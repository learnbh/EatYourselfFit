package org.bea.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.bea.backend.model.*;
import org.bea.backend.openAI.IngredientOpenAiDto;
import org.bea.backend.openAI.NutrientOpenAiService;
import org.bea.backend.repository.IngredientRepository;
import org.bea.backend.repository.NutrientsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final NutrientsRepository nutrientsRepository;

    private final ServiceId serviceId;
    private final NutrientOpenAiService nutrientOpenAiService;

    public IngredientService(IngredientRepository ingredientRepository,
                             NutrientsRepository nutrientsRepository,
                             ServiceId serviceId,
                             NutrientOpenAiService nutrientOpenAiService) {
        this.ingredientRepository = ingredientRepository;
        this.nutrientsRepository = nutrientsRepository;
        this.serviceId = serviceId;
        this.nutrientOpenAiService = nutrientOpenAiService;
    }

    public List<Ingredient> getIngredients() {
        return ingredientRepository.findAll();
    }

    public Ingredient addIngredient(IngredientDto ingredientDto) {
        Ingredient newIngredient = new Ingredient(
                serviceId.generateId(),
                ingredientDto.product(),
                ingredientDto.variation(),
                ingredientDto.quantity(),
                ingredientDto.unit(),
                ingredientDto.prices(),
                ingredientDto.nutrientsId()
        );
        ingredientRepository.save(newIngredient);
        return newIngredient;
    }

    public Ingredient addIngredientByOpenAi(IngredientOpenAiDto ingredientOpenAiDto) throws JsonProcessingException {
        String product = ingredientOpenAiDto.product();
        String variation = ingredientOpenAiDto.variation();


        if (ingredientRepository.getIngredientByProductAndVariation(product, variation).isEmpty()) {
            ObjectMapper objectMapper = new ObjectMapper();
            String contentString = nutrientOpenAiService.getNutrients(product, variation);

                // is ingedientNode valid
                if (!(contentString.contains("ingredientDto") || contentString.contains("nutrientsDto"))) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Antwort von OpenAI für Ingredient "+product+" ist leer.");
                }
                JsonNode contentNode = objectMapper.readTree(contentString);
                ObjectNode ingredientNode = (ObjectNode) contentNode.get("ingredientDto");
                if (!( ingredientNode.get("product").toString().equals(product)
                    || !(ingredientNode.get("variation").toString().equals(variation))
                    || !(ingredientNode.get("quantity").asInt() == 100)
                    || !(ingredientNode.get("unit").toString().equals("g")))
                ){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Antwort von OpenAI für Ingredient ist unbrauchbar.");
                }

                // add Ingredient and Nutrient
                if (ingredientRepository.getIngredientByProductAndVariation(ingredientNode.get("product").asText(), ingredientNode.get("variation").asText()).isEmpty()){
                    // Nutrients:
                    ObjectNode nutrientsNode = (ObjectNode) contentNode.get("nutrientsDto");
                    if (nutrientsNode == null || nutrientsNode.get("energyKcal") == null) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                "Antwort von OpenAI für Nutrients ist leer oder unbrauchbar.");
                    }

                    String nutrientsId = serviceId.generateId();
                    nutrientsNode.put("id", nutrientsId);
                    Nutrients nutrients = objectMapper.treeToValue(nutrientsNode, Nutrients.class);
                    nutrientsRepository.save(nutrients);

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
}
