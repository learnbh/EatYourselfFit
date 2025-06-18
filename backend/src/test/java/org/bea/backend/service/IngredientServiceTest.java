package org.bea.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.bea.backend.exception.IdNotFoundException;
import org.bea.backend.exception.OpenAiNotFoundIngredientException;
import org.bea.backend.exception.ProductVariationNotFound;
import org.bea.backend.model.Ingredient;
import org.bea.backend.model.IngredientDto;
import org.bea.backend.openai.IngredientOpenAiDto;
import org.bea.backend.model.Nutrients;
import org.bea.backend.openai.NutrientOpenAiService;
import org.bea.backend.openai.OpenAiConfig;
import org.bea.backend.repository.IngredientRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockRestServiceServer
class IngredientServiceTest {

    // OpenAi
    private NutrientOpenAiService mockNutrientOpenAiService;

    private IngredientRepository mockIngredientRepository;
    private NutrientService mockNutrientService;
    private ServiceId mockServiceId;
    private IngredientService ingredientService;

    Ingredient milk;
    IngredientDto milkDto;
    IngredientOpenAiDto ingredientOpenAiDto;

    @BeforeEach
    void setUp() {

        mockIngredientRepository = Mockito.mock(IngredientRepository.class);
        mockNutrientService = Mockito.mock(NutrientService.class);
        mockServiceId = Mockito.mock(ServiceId.class);
        mockNutrientOpenAiService = Mockito.mock(NutrientOpenAiService.class);
        ingredientService = new IngredientService(mockIngredientRepository, mockNutrientService, mockServiceId, mockNutrientOpenAiService);

        milk = new Ingredient("milk", "milk", "low fat", 100.0, "ml", 1.29, "egal");
        milkDto = new IngredientDto("milk", "low fat", 100.0, "ml", 1.29, "egal");
        ingredientOpenAiDto = new IngredientOpenAiDto("rindehack", "");

    }

    @Test
    public void getIngredients_shouldReturn_EmptyList_whenMongoCollectionIsEmpty(){
        assertEquals(Collections.emptyList(), ingredientService.getIngredients());
        Mockito.verify(mockIngredientRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void getIngredients_shouldReturn_IngredientsList_whenAtLeastOneIngredientIsPresent() {
        List<Ingredient> expected = Collections.singletonList(milk);
        // when
        Mockito.when(mockIngredientRepository.findAll()).thenReturn(expected);
        // then
        assertEquals(expected, ingredientService.getIngredients());
        Mockito.verify(mockIngredientRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void addIngredient_shouldReturn_createdIngredient() {
        // when
        Mockito.when(mockServiceId.generateId()).thenReturn(milk.id());
        Mockito.when(mockIngredientRepository.save(milk)).thenReturn(milk);
        // then
        assertEquals(milk, ingredientService.addIngredient(milkDto));
        Mockito.verify(mockIngredientRepository, Mockito.times(1)).save(milk);
    }

    @Test
    public void addIngredientByOpenAi_shouldAddCorrectIngredientAndTheirNutrients() throws JsonProcessingException {
        // given
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode contentNode = objectMapper.readTree(OpenAiConfig.ingredientResponseTest);
        ObjectNode nutrientsNode = (ObjectNode) contentNode.get("nutrientsDto");
        nutrientsNode.put("id", "nutrientId");
        Nutrients nutrients = objectMapper.treeToValue(nutrientsNode, Nutrients.class);
        ObjectNode ingredientNode = (ObjectNode) contentNode.get("ingredientDto");
        ingredientNode.put("id", "ingredientId");
        ingredientNode.put("nutrientsId", "nutrientId");
        Ingredient ingredient = objectMapper.treeToValue(ingredientNode, Ingredient.class);
        // when
        Mockito.when(mockNutrientOpenAiService
                        .getNutrients(ingredientOpenAiDto.product(),
                                        ingredientOpenAiDto.variation()))
                .thenReturn(OpenAiConfig.ingredientResponseTest);
        Mockito.when(mockServiceId.generateId()).thenReturn("nutrientId", "ingredientId");
        Mockito.when(mockNutrientService.addNutrients(nutrients)).thenReturn(nutrients);
        Mockito.when(mockIngredientRepository.save(ingredient)).thenReturn(ingredient);
        // then
        assertEquals(ingredient, ingredientService.addIngredientByOpenAi(ingredientOpenAiDto));
        Mockito.verify(mockIngredientRepository, Mockito.times(1)).save(ingredient);
        Mockito.verify(mockNutrientService, Mockito.times(1)).addNutrients(nutrients);
    }

    @Test
    public void addIngredientByOpenAi_shouldOpenAiNotFoundIngredientException_whenJsonIsWrong(){
        // when
        Mockito.when(mockNutrientOpenAiService
                        .getNutrients(ingredientOpenAiDto.product(),
                                ingredientOpenAiDto.variation()))
                .thenReturn("""
                        {{ "product": "Rindfleisch",
                        "variation": "Rinderhack 20% Fett",
                        "quantity": 100,
                        "unit": "g",
                        "prices": 7.99}}
            """);
        assertThrows(OpenAiNotFoundIngredientException.class, () -> ingredientService.addIngredientByOpenAi(ingredientOpenAiDto));
    }
    @Test
    public void addIngredientByOpenAi_shouldOpenAiNotFoundIngredientException_whenNutrientsNotFound(){
        // when
        Mockito.when(mockNutrientOpenAiService
                        .getNutrients(ingredientOpenAiDto.product(),
                                ingredientOpenAiDto.variation()))
                .thenReturn("Es konnten keine Nährstoffe gefunden werden. Änderne die Anfrage und versuche es erneut.");
        assertThrows(OpenAiNotFoundIngredientException.class, () -> ingredientService.addIngredientByOpenAi(ingredientOpenAiDto));
    }

    @Test
    void getIngredientById_shouldThrowIdNotFoundException_whenIdIsNotFound() {
        assertThrows(IdNotFoundException.class, () -> ingredientService.getIngredientById(milk.id()));
    }
    @Test
    void getIngredientById_shouldReturn_IngredientMilkForMilkId() {
        // when
        Mockito.when(mockIngredientRepository.findById(milk.id()))
                .thenReturn(Optional.of(milk));
        // then
        assertEquals(milk, ingredientService.getIngredientById(milk.id()));
        Mockito.verify(mockIngredientRepository, Mockito.times(1)).findById(milk.id());
    }

    @Test
    void getNutrientsDaily_shouldThrowProductVariationNotFound_whenProductVariationIsNotFound() {
        assertThrows(ProductVariationNotFound.class, ()->ingredientService.getNutrientsDaily());
    }
    @Test
    void getNutrientsDaily_shouldReturn_NutrientsDaily() {
        // given
        Ingredient ingredient = new Ingredient("ingredientId","Nährstoffe", "Täglicher Bedarf", 0.0, "g", 0.0,"nutrientId");
        Nutrients expected = Instancio.of(Nutrients.class)
                        .set(field(Nutrients::id), "nutrientId")
                        .create();
        // when
        Mockito.when(mockNutrientService.getNutrientsById(ingredient.nutrientsId())).thenReturn(expected);
        Mockito.when(mockIngredientRepository.getIngredientByProductAndVariation(ingredient.product(), ingredient.variation()))
                        .thenReturn(Optional.of(ingredient));
        // then
        assertEquals(expected, ingredientService.getNutrientsDaily());
        Mockito.verify(mockNutrientService, Mockito.times(1))
                .getNutrientsById(ingredient.nutrientsId());
        Mockito.verify(mockIngredientRepository, Mockito.times(1))
                .getIngredientByProductAndVariation(ingredient.product(), ingredient.variation());
    }
}