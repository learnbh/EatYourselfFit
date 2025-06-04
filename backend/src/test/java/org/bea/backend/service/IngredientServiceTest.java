package org.bea.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.bea.backend.model.Ingredient;
import org.bea.backend.model.IngredientDto;
import org.bea.backend.openai.IngredientOpenAiDto;
import org.bea.backend.model.Nutrients;
import org.bea.backend.openai.NutrientOpenAiService;
import org.bea.backend.openai.OpenAiConfig;
import org.bea.backend.repository.IngredientRepository;
import org.bea.backend.repository.NutrientsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockRestServiceServer
class IngredientServiceTest {

    // OpenAi
    private NutrientOpenAiService mockNutrientOpenAiService;

    private IngredientRepository mockIngredientRepository;
    private NutrientsRepository mockNutrientsRepository;
    private ServiceId mockServiceId;
    private IngredientService ingredientService;

    Ingredient milk;
    IngredientDto milkDto;
    IngredientOpenAiDto ingredientOpenAiDto;

    @BeforeEach
    void setUp() {

        mockIngredientRepository = Mockito.mock(IngredientRepository.class);
        mockNutrientsRepository = Mockito.mock(NutrientsRepository.class);
        mockServiceId = Mockito.mock(ServiceId.class);
        mockNutrientOpenAiService = Mockito.mock(NutrientOpenAiService.class);
        ingredientService = new IngredientService(mockIngredientRepository, mockNutrientsRepository, mockServiceId, mockNutrientOpenAiService);

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
        // given
        mockIngredientRepository.save(milk);
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
        Mockito.when(mockNutrientsRepository.save(nutrients)).thenReturn(nutrients);
        Mockito.when(mockIngredientRepository.save(ingredient)).thenReturn(ingredient);
        // then
        assertEquals(ingredient, ingredientService.addIngredientByOpenAi(ingredientOpenAiDto));
        Mockito.verify(mockIngredientRepository, Mockito.times(1)).save(ingredient);
        Mockito.verify(mockNutrientsRepository, Mockito.times(1)).save(nutrients);
    }

    @Test
    public void addIngredientByOpenAi_shouldThrowResponseStatusException_whenJsonIsWrong(){
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
        assertThrows(ResponseStatusException.class, () -> ingredientService.addIngredientByOpenAi(ingredientOpenAiDto));
    }
}