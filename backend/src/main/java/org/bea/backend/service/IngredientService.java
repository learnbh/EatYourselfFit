package org.bea.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.bea.backend.exception.IdNotFoundException;
import org.bea.backend.exception.OpenAiNotFoundIngredientException;
import org.bea.backend.exception.ProductVariationNotFoundException;
import org.bea.backend.mapper.NutrientMapper;
import org.bea.backend.model.*;
import org.bea.backend.openai.IngredientOpenAiDto;
import org.bea.backend.openai.NutrientOpenAiService;
import org.bea.backend.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final NutrientService nutrientService;
    private final NutrientMapper nutrientMapper;

    private final ServiceId serviceId;
    private final NutrientOpenAiService nutrientOpenAiService;

    public IngredientService(
            ServiceId serviceId,
            IngredientRepository ingredientRepository,
            NutrientService nutrientService,
            NutrientMapper nutrientMapper,
            NutrientOpenAiService nutrientOpenAiService) {
        this.ingredientRepository = ingredientRepository;
        this.nutrientService = nutrientService;
        this.serviceId = serviceId;
        this.nutrientOpenAiService = nutrientOpenAiService;
        this.nutrientMapper = nutrientMapper;
    }

    public List<Ingredient> getIngredients() {
        return ingredientRepository.findAll();
    }

    public String addIngredient(IngredientProfile ingredientProfile) {
        String product = ingredientProfile.ingredientCreate().product();
        String variation = ingredientProfile.ingredientCreate().variation();

            String nutrientId = serviceId.generateId();
            Nutrients newNutrients = nutrientMapper.createNutrients(nutrientId, ingredientProfile.nutrientsArray());

            String ingredientId = serviceId.generateId();
            Ingredient newIngredient = new Ingredient(
                    ingredientId,
                    product,
                    variation,
                    ingredientProfile.ingredientCreate().quantity(),
                    ingredientProfile.ingredientCreate().unit(),
                    ingredientProfile.ingredientCreate().prices(),
                    nutrientId
            );
            ingredientRepository.save(newIngredient);
            nutrientService.addNutrients(newNutrients);

            return ingredientId;
    }

    public Ingredient addIngredientByOpenAi(IngredientOpenAiDto ingredientOpenAiDto) throws JsonProcessingException {
        String product = ingredientOpenAiDto.product();
        String variation = ingredientOpenAiDto.variation();

        if (ingredientRepository.getIngredientByProductAndVariation(product, variation).isEmpty()) {
            ObjectMapper objectMapper = new ObjectMapper();
            String contentString = nutrientOpenAiService.getNutrients(product, variation);

                if (Objects.equals(contentString, "Es konnten keine Nährstoffe gefunden werden. Änderne die Anfrage und versuche es erneut.")) {
                    throw new OpenAiNotFoundIngredientException(contentString);
                }
                // is ingedientNode valid
                if (!(contentString.contains("ingredientDto") || contentString.contains("nutrientsDto"))) {
                    throw new OpenAiNotFoundIngredientException("Antwort von OpenAI für Ingredient "+product+" ist leer. Änderne die Anfrage und versuche es erneut.");
                }
                JsonNode contentNode = objectMapper.readTree(contentString);
                ObjectNode ingredientNode = (ObjectNode) contentNode.get("ingredientDto");
                if (!( ingredientNode.get("product").toString().equals(product)
                    || !(ingredientNode.get("variation").toString().equals(variation))
                    || !(ingredientNode.get("quantity").asInt() == 100)
                    || !(ingredientNode.get("unit").toString().equals("g")))
                ){
                    throw new OpenAiNotFoundIngredientException("Antwort von OpenAI für Ingredient ist unbrauchbar. Änderne die Anfrage und versuche es erneut.");
                }
                // add Ingredient and Nutrient
                if (ingredientRepository.getIngredientByProductAndVariation(ingredientNode.get("product").asText(), ingredientNode.get("variation").asText()).isEmpty()){
                    // Nutrients:
                    ObjectNode nutrientsNode = (ObjectNode) contentNode.get("nutrientsDto");
                    if (nutrientsNode == null || nutrientsNode.get("energyKcal") == null) {
                        throw new OpenAiNotFoundIngredientException("Antwort von OpenAI für Nutrients ist leer oder unbrauchbar. Änderne die Anfrage und versuche es erneut.");
                    }

                    String nutrientsId = serviceId.generateId();
                    nutrientsNode.put("id", nutrientsId);
                    Nutrients nutrients = objectMapper.treeToValue(nutrientsNode, Nutrients.class);
                    nutrientService.addNutrients(nutrients);

                    // Ingredient:
                    ingredientNode.put("id", serviceId.generateId());
                    ingredientNode.put("nutrientsId", nutrientsId);
                    Ingredient ingredient = objectMapper.readValue(objectMapper.writeValueAsString(ingredientNode), Ingredient.class);
                    ingredientRepository.save(ingredient);
                    return ingredient;
                }
        }
        return null;
    }

    public Set<Ingredient> getIngredientByName(String name) {
        List<Ingredient> products = ingredientRepository.findIngredientsByProductContainsIgnoreCase(name);
        List<Ingredient> variation = ingredientRepository.findIngredientsByVariationContainsIgnoreCase(name);
        Set<Ingredient> ingredients = new LinkedHashSet<>(products);
        ingredients.addAll(variation);
        return ingredients;
    }

    public Ingredient getIngredientById(String id) {
        return ingredientRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Zutat wurde nicht gefunden."));
    }

    public Nutrients getNutrientsDaily() {
        Ingredient ingredient = ingredientRepository
                .getIngredientByProductAndVariation("Nährstoffe", "Täglicher Bedarf")
                .orElseThrow(() -> new ProductVariationNotFoundException("Produkt, Variation: Nährstoffe, Täglicher Bedarf existiert nicht."));
        return nutrientService.getNutrientsById(ingredient.nutrientsId());
    }

    public Ingredient updateIngredient(String id, IngredientDto ingredientDto) {
        Ingredient ingredient = getIngredientById(id);
        Ingredient ingredientUpgedated = new Ingredient(
            ingredient.id(),
            ingredientDto.product(),
            ingredientDto.variation(),
            ingredientDto.quantity(),
            ingredientDto.unit(),
            ingredientDto.prices(),
            ingredient.nutrientsId()
        );
        ingredientRepository.save(ingredientUpgedated);
        return ingredientUpgedated;
    }

}
