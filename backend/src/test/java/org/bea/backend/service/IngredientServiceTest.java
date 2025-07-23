package org.bea.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.bea.backend.FakeTestData.IngredientCreateFakeData;
import org.springframework.dao.DuplicateKeyException;
import org.bea.backend.exception.IdNotFoundException;
import org.bea.backend.exception.OpenAiException;
import org.bea.backend.exception.ProductVariationNotFoundException;
import org.bea.backend.mapper.NutrientMapper;
import org.bea.backend.model.Ingredient;
import org.bea.backend.model.IngredientDto;
import org.bea.backend.model.IngredientProfile;
import org.bea.backend.openai.IngredientOpenAiDto;
import org.bea.backend.model.Nutrients;
import org.bea.backend.openai.NutrientOpenAiService;
import org.bea.backend.repository.IngredientRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockRestServiceServer
class IngredientServiceTest {

    // OpenAi
    private NutrientOpenAiService mockNutrientOpenAiService;

    private IngredientRepository mockIngredientRepository;
    private ServiceId mockServiceId;
    private NutrientService mockNutrientService;
    NutrientMapper mockNutrientMapper;
    ObjectMapper mockObjectMapper;
    private IngredientService ingredientService;

    IngredientProfile ingredientProfile;
    Nutrients expectedNutrients;
    Ingredient expectedIngredient;

    Ingredient milkOrig;
    Ingredient milkFindByName;
    Ingredient milkDouble;
    Ingredient milk;
    IngredientDto milkDto;
    // OpenAi
    IngredientOpenAiDto ingredientOpenAiDto;
    String correctResponse;
    String responseWrongIngredientUnit;
    String responseWrongIngredientQuantity;
    String responseWrongNutrientsEnergyKcal;
    String responseNutrientsWithInvalidNutrient;
    String responseWithoutNutrientsNode;
    String responseWithEmptyNutrientsNode;
    String responseWithEmptyNutrientInNutrientsNode;
    String responseWithoutNutrientEnergyKcal;
    String responseWithInvalidIngredient;

    @BeforeEach
    void setUp() {

        mockIngredientRepository = Mockito.mock(IngredientRepository.class);
        mockNutrientService = Mockito.mock(NutrientService.class);
        mockServiceId = Mockito.mock(ServiceId.class);
        mockNutrientMapper = Mockito.mock(NutrientMapper.class);
        mockNutrientOpenAiService = Mockito.mock(NutrientOpenAiService.class);
        mockObjectMapper = Mockito.mock(ObjectMapper.class);
        ingredientService = new IngredientService(
                mockServiceId,
                mockIngredientRepository,
                mockNutrientService,
                mockNutrientMapper,
                mockNutrientOpenAiService,
                mockObjectMapper
        );

        ingredientProfile = new IngredientProfile(
                IngredientCreateFakeData.ingredientCreate,
                IngredientCreateFakeData.nutrientsArray
        );
        expectedNutrients = IngredientCreateFakeData.nutrients;
        expectedIngredient = new Ingredient(
                "ingredientId",
                IngredientCreateFakeData.ingredientCreate.product(),
                IngredientCreateFakeData.ingredientCreate.variation(),
                "slug",
                IngredientCreateFakeData.ingredientCreate.quantity(),
                IngredientCreateFakeData.ingredientCreate.unit(),
                IngredientCreateFakeData.ingredientCreate.prices(),
                expectedNutrients.id()
        );

        milkOrig = new Ingredient("milk", "milch", "fat", "slug", 90.0, "g", 1.09, "egal");
        milkFindByName = new Ingredient("milk", "milch", "milch fat", "slug", 90.0, "g", 1.09, "egal");
        milkDouble = new Ingredient("milkDouble", "milk", "low fat", "slug", 90.0, "g", 1.09, "egal");
        milk = new Ingredient("milk", "milk", "low fat", "slug", 100.0, "ml", 1.29, "egal");
        milkDto = new IngredientDto("milk", "low fat", 100.0, "ml", 1.29, "egal");
        // OpenAi
        ingredientOpenAiDto = new IngredientOpenAiDto("rindehack", "");
        correctResponse = IngredientCreateFakeData.CORRECT_RESPONSE;
        responseWrongIngredientUnit = IngredientCreateFakeData.WRONG_INGREDIENT_UNIT;
        responseWrongIngredientQuantity = IngredientCreateFakeData.WRONG_INGREDIENT_QUANTITY;
        responseWrongNutrientsEnergyKcal = IngredientCreateFakeData.WRONG_NUTRIENTS_ENERGY_KCAL;
        responseNutrientsWithInvalidNutrient = IngredientCreateFakeData.RESPONSE_NUTRIENTS_WITH_INVALID_NUTRIENT;
        responseWithoutNutrientsNode = IngredientCreateFakeData.RESPONSE_WITHOUT_NUTRIENTS_NODE;
        responseWithEmptyNutrientsNode = IngredientCreateFakeData.RESPONSE_WITH_EMPTY_NUTRIENTS_NODE;
        responseWithEmptyNutrientInNutrientsNode = IngredientCreateFakeData.RESPONSE_WITH_EMPTY_NUTRIENT_IN_NUTRIENTS_NODE;
        responseWithoutNutrientEnergyKcal = IngredientCreateFakeData.RESPONSE_WITHOUT_NUTRIENT_ENERGY_KCAL;
        responseWithInvalidIngredient = IngredientCreateFakeData.RESPONSE_WITH_INVALID_INGREDIENT;
    }

    @Test
    void getIngredients_shouldReturn_EmptyList_whenMongoCollectionIsEmpty(){
        assertEquals(Collections.emptyList(), ingredientService.getIngredients());
        Mockito.verify(mockIngredientRepository, Mockito.times(1)).findAll();
    }
    @Test
    void getIngredients_shouldReturn_IngredientsList_whenAtLeastOneIngredientIsPresent() {
        // given
        List<Ingredient> expected = Collections.singletonList(milk);
        // when
        Mockito.when(mockIngredientRepository.findAll()).thenReturn(expected);
        // then
        assertEquals(expected, ingredientService.getIngredients());
        Mockito.verify(mockIngredientRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getIngredientById_shouldThrowIdNotFoundException_whenIdIsNotFound() {
        assertThrows(IdNotFoundException.class, () -> ingredientService.getIngredientById(milk.id()));
    }
    @Test
    void getIngredientById_shouldReturn_IngredientMilkForMilkId() {
        // when
        Mockito.when(mockIngredientRepository
                .findById(milk.id())
        ).thenReturn(Optional.of(milk));
        // then
        assertEquals(milk, ingredientService.getIngredientById(milk.id()));
        Mockito.verify(mockIngredientRepository, Mockito.times(1)).findById(milk.id());
    }
    @Test
    void getIngredientByName_shouldReturnSetOfIngredients_whenNameIsInProductnameOrVariationnameOfStoredIngredientsInDB() {
        // given
        Set<Ingredient> expected = new LinkedHashSet<>();
        expected.add(milkOrig);
        expected.add(milkFindByName);
        // when
        Mockito.when(mockIngredientRepository
                .findIngredientsByProductContainsIgnoreCase(milkOrig.product())
        ).thenReturn(List.of(milkOrig));
        Mockito.when(mockIngredientRepository
                .findIngredientsByVariationContainsIgnoreCase(milkOrig.product())
        ).thenReturn(List.of(milkFindByName));
        // then
        assertEquals(expected, ingredientService.getIngredientByName(milkOrig.product()));
        // verify
        Mockito.verify(mockIngredientRepository, Mockito.times(1))
                .findIngredientsByProductContainsIgnoreCase(milkOrig.product());
        Mockito.verify(mockIngredientRepository, Mockito.times(1))
                .findIngredientsByVariationContainsIgnoreCase(milkOrig.product());
    }

    @Test
    void addIngredient_shouldReturn_createdIngredient() {
        // when
        Mockito.when(mockServiceId
                .generateId()
        ).thenReturn(expectedNutrients.id(), expectedIngredient.id());
        Mockito.when(mockServiceId.generateSlug(expectedIngredient.product() + "-" + expectedIngredient.variation()))
                        .thenReturn(expectedIngredient.slug());
        Mockito.when(mockNutrientMapper
                .createNutrients(expectedNutrients.id(), ingredientProfile.nutrientsArray())
        ).thenReturn(expectedNutrients);
        Mockito.when(mockNutrientService
                .addNutrients(expectedNutrients)
        ).thenReturn(expectedNutrients);
        // then
        assertEquals(expectedIngredient, ingredientService.addIngredient(ingredientProfile));
        //verify
        Mockito.verify(mockServiceId, Mockito.times(2)).generateId();
        Mockito.verify(mockServiceId, Mockito.times(1))
                .generateSlug(expectedIngredient.product() + "-" + expectedIngredient.variation());
        Mockito.verify(mockIngredientRepository, Mockito.times(1)).save(expectedIngredient);
        Mockito.verify(mockNutrientService, Mockito.times(1)).addNutrients(expectedNutrients);
    }
    // addIngredientByOpenAi
    @Test
    void addIngredientByOpenAi_shouldAddCorrectIngredientAndTheirNutrients() throws JsonProcessingException {
        // given
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode contentNode = objectMapper.readTree(correctResponse);
        ObjectNode nutrientsNode = (ObjectNode) contentNode.get("nutrientsDto");
        ObjectNode ingredientNode = (ObjectNode) contentNode.get("ingredientDto");
        // when
        Mockito.when(mockNutrientOpenAiService
                .getNutrients(ingredientOpenAiDto.product(), ingredientOpenAiDto.variation())
        ).thenReturn(correctResponse);
        Mockito.when(mockObjectMapper.readTree(correctResponse))
                        .thenReturn(contentNode);
        Mockito.when(mockObjectMapper.treeToValue(nutrientsNode, Nutrients.class))
                .thenReturn(expectedNutrients);
        Mockito.when(mockObjectMapper.treeToValue(ingredientNode, Ingredient.class))
                .thenReturn(expectedIngredient);
        Mockito.when(mockServiceId
                .generateId()
        ).thenReturn(expectedNutrients.id(), expectedIngredient.id());
        Mockito.when(mockNutrientService
                .addNutrients(expectedNutrients)
        ).thenReturn(expectedNutrients);
        Mockito.when(mockIngredientRepository
                .getIngredientByProductAndVariationContainsIgnoreCase(ingredientOpenAiDto.product(),ingredientOpenAiDto.variation())
        ).thenReturn(Optional.empty());
        Mockito.when(mockIngredientRepository
                .getIngredientByProductAndVariationContainsIgnoreCase(expectedIngredient.product(),expectedIngredient.variation())
        ).thenReturn(Optional.empty());
        // then
        assertEquals(expectedIngredient, ingredientService.addIngredientByOpenAi(ingredientOpenAiDto));
        Mockito.verify(mockNutrientOpenAiService, Mockito.times(1))
                .getNutrients(
                        ingredientOpenAiDto.product(),
                        ingredientOpenAiDto.variation()
                );
        Mockito.verify(mockIngredientRepository, Mockito.times(1))
                .save(expectedIngredient);
        Mockito.verify(mockIngredientRepository, Mockito.times(1))
                .getIngredientByProductAndVariationContainsIgnoreCase(
                    ingredientOpenAiDto.product(),
                    ingredientOpenAiDto.variation()
                );
        Mockito.verify(mockIngredientRepository, Mockito.times(1))
                .getIngredientByProductAndVariationContainsIgnoreCase(
                        expectedIngredient.product(),
                        expectedIngredient.variation()
                );
        Mockito.verify(mockNutrientService, Mockito.times(1)).addNutrients(expectedNutrients);
        Mockito.verify(mockObjectMapper, Mockito.times(1)).readTree(correctResponse);
        Mockito.verify(mockObjectMapper, Mockito.times(1)).treeToValue(nutrientsNode, Nutrients.class);
        Mockito.verify(mockObjectMapper, Mockito.times(1)).treeToValue(ingredientNode, Ingredient.class);
    }

    @Test
    void addIngredientByOpenAi_shouldOpenAiException_whenNutrientsNotFound(){
        // when
        Mockito.when(mockNutrientOpenAiService
                .getNutrients(ingredientOpenAiDto.product(), ingredientOpenAiDto.variation())
        ).thenReturn("Es konnten keine Nährstoffe gefunden werden. Änderne die Anfrage und versuche es erneut.");
        // then
        assertThrows(OpenAiException.class, () -> ingredientService.addIngredientByOpenAi(ingredientOpenAiDto));
        // verify
        Mockito.verify(mockNutrientOpenAiService, Mockito.times(1))
                .getNutrients(
                        ingredientOpenAiDto.product(),
                        ingredientOpenAiDto.variation()
                );
    }

    @Test
    void addIngredientByOpenAi_shouldThrowOpenAiException_whenJsonIsWrong(){
        // when
        Mockito.when(mockNutrientOpenAiService
                        .getNutrients(ingredientOpenAiDto.product(),ingredientOpenAiDto.variation())
        ).thenReturn("""
                        {{ "product": "Rindfleisch",
                        "variation": "Rinderhack 20% Fett",
                        "quantity": 100,
                        "unit": "g",
                        "prices": 7.99}}
        """);
        assertThrows(OpenAiException.class, () -> ingredientService.addIngredientByOpenAi(ingredientOpenAiDto));
        Mockito.verify(mockNutrientOpenAiService, Mockito.times(1))
                .getNutrients(
                        ingredientOpenAiDto.product(),
                        ingredientOpenAiDto.variation()
                );
    }

    @Test
    void addIngredientByOpenAi_shouldThrowOpenAiException_whenNoIngredientDtoInJson(){
        // when
        Mockito.when(mockNutrientOpenAiService
                .getNutrients(ingredientOpenAiDto.product(), ingredientOpenAiDto.variation())
        ).thenReturn("{}");
        OpenAiException ex = assertThrows(OpenAiException.class, () -> ingredientService.addIngredientByOpenAi(ingredientOpenAiDto));
        assertTrue(ex.getMessage().contains("ist leer"));
        Mockito.verify(mockNutrientOpenAiService, Mockito.times(1))
                .getNutrients(
                        ingredientOpenAiDto.product(),
                        ingredientOpenAiDto.variation()
                );
    }
    @Test
    void addIngredientByOpenAi_shouldThrowOpenAiException_whenNoNutrientsDtoInJson(){
        // when
        Mockito.when(mockNutrientOpenAiService
                .getNutrients(ingredientOpenAiDto.product(), ingredientOpenAiDto.variation())
        ).thenReturn(responseWithoutNutrientsNode);
        OpenAiException ex = assertThrows(OpenAiException.class, () -> ingredientService.addIngredientByOpenAi(ingredientOpenAiDto));
        assertTrue(ex.getMessage().contains("Antwort von OpenAI für Nährstoffe von "));
        Mockito.verify(mockNutrientOpenAiService, Mockito.times(1))
                .getNutrients(ingredientOpenAiDto.product(), ingredientOpenAiDto.variation());
    }

    @Test
    void addIngredientByOpenAi_shouldThrowOpenAiException_whenIngredientFieldQuantityIsInvalid() throws JsonProcessingException {
        // given
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode contentNode = objectMapper.readTree(responseWrongIngredientQuantity);
        // when
        Mockito.when(mockNutrientOpenAiService.getNutrients(ingredientOpenAiDto.product(), ingredientOpenAiDto.variation())
        ).thenReturn(responseWrongIngredientQuantity);
        Mockito.when(mockObjectMapper.readTree(responseWrongIngredientQuantity))
                .thenReturn(contentNode);
        // then
        OpenAiException ex = assertThrows(OpenAiException.class, () -> ingredientService.addIngredientByOpenAi(ingredientOpenAiDto));
        assertTrue(ex.getMessage().contains("Antwort von OpenAI für Zutat-Menge ist unbrauchbar. Änderne die Anfrage und versuche es erneut."));
        Mockito.verify(mockNutrientOpenAiService, Mockito.times(1))
                .getNutrients(
                        ingredientOpenAiDto.product(),
                        ingredientOpenAiDto.variation()
                );
        Mockito.verify(mockObjectMapper, Mockito.times(1)).readTree(responseWrongIngredientQuantity);
    }
    @Test
    void addIngredientByOpenAi_shouldThrowOpenAiException_whenIngredientFieldUnitIsInvalid() throws JsonProcessingException {
        // given
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode contentNode = objectMapper.readTree(responseWrongIngredientUnit);
        // when
        Mockito.when(mockNutrientOpenAiService.getNutrients(ingredientOpenAiDto.product(), ingredientOpenAiDto.variation())
        ).thenReturn(responseWrongIngredientUnit);
        Mockito.when(mockObjectMapper.readTree(responseWrongIngredientUnit))
                .thenReturn(contentNode);
        // then
        OpenAiException ex = assertThrows(OpenAiException.class, () -> ingredientService.addIngredientByOpenAi(ingredientOpenAiDto));
        assertTrue(ex.getMessage().contains("Antwort von OpenAI für Zutat-Einheit ist unbrauchbar. Änderne die Anfrage und versuche es erneut."));
        Mockito.verify(mockNutrientOpenAiService, Mockito.times(1))
                .getNutrients(
                        ingredientOpenAiDto.product(),
                        ingredientOpenAiDto.variation()
                );
        Mockito.verify(mockObjectMapper, Mockito.times(1)).readTree(responseWrongIngredientUnit);
    }

    @Test
    void addIngredientByOpenAi_shouldThrowOpenAiException_whenResponseContainsInvalidIngredient() throws JsonProcessingException {
        // given
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode contentNode = objectMapper.readTree(responseWithInvalidIngredient);
        ObjectNode nutrientsNode = (ObjectNode) contentNode.get("nutrientsDto");
        ObjectNode ingredientNode = (ObjectNode) contentNode.get("ingredientDto");
        // when
        Mockito.when(mockNutrientOpenAiService.getNutrients(ingredientOpenAiDto.product(), ingredientOpenAiDto.variation())
        ).thenReturn(responseWithInvalidIngredient);
        Mockito.when(mockObjectMapper.readTree(responseWithInvalidIngredient))
                .thenReturn(contentNode);
        Mockito.when(mockObjectMapper.treeToValue(nutrientsNode, Nutrients.class))
                .thenReturn(expectedNutrients);
        Mockito.when(mockObjectMapper.treeToValue(ingredientNode, Ingredient.class))
                .thenThrow(JsonProcessingException.class);
        // then
        OpenAiException ex = assertThrows(OpenAiException.class, () -> ingredientService.addIngredientByOpenAi(ingredientOpenAiDto));
        System.out.println(ex.getMessage());
        assertTrue(ex.getMessage().contains("Antwort von OpenAI für Zutat ist unbrauchbar. Änderne die Anfrage und versuche es erneut."));
        Mockito.verify(mockNutrientOpenAiService, Mockito.times(1))
                .getNutrients(
                        ingredientOpenAiDto.product(),
                        ingredientOpenAiDto.variation()
                );
        Mockito.verify(mockObjectMapper, Mockito.times(1)).readTree(responseWithInvalidIngredient);
        Mockito.verify(mockObjectMapper, Mockito.times(1)).treeToValue(nutrientsNode, Nutrients.class);
    }

    @Test
    void addIngredientByOpenAi_shouldThrowOpenAiException_whenNutrientsDtoInResponseIsEmpty() throws JsonProcessingException {
        // given
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode contentNode = objectMapper.readTree(responseWithEmptyNutrientsNode);
        // when
        Mockito.when(mockNutrientOpenAiService.getNutrients(ingredientOpenAiDto.product(), ingredientOpenAiDto.variation())
        ).thenReturn(responseWithEmptyNutrientsNode);
        Mockito.when(mockObjectMapper.readTree(responseWithEmptyNutrientsNode))
                .thenReturn(contentNode);
        OpenAiException ex = assertThrows(OpenAiException.class, () -> ingredientService.addIngredientByOpenAi(ingredientOpenAiDto));
        assertTrue(ex.getMessage().contains("Antwort von OpenAI für Nährstoffe ist leer oder unbrauchbar. Änderne die Anfrage und versuche es erneut."));
        Mockito.verify(mockNutrientOpenAiService, Mockito.times(1))
                .getNutrients(
                        ingredientOpenAiDto.product(),
                        ingredientOpenAiDto.variation()
                );
        Mockito.verify(mockObjectMapper, Mockito.times(1)).readTree(responseWithEmptyNutrientsNode);
    }

    @Test
    void addIngredientByOpenAi_shouldThrowOpenAiException_whenNutrient_InNutrientsDtoOfResponseIsEmpty() throws JsonProcessingException {
        // given
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode contentNode = objectMapper.readTree(responseWithEmptyNutrientInNutrientsNode);
        // when
        Mockito.when(mockNutrientOpenAiService.getNutrients(ingredientOpenAiDto.product(), ingredientOpenAiDto.variation())
        ).thenReturn(responseWithEmptyNutrientInNutrientsNode);
        Mockito.when(mockObjectMapper.readTree(responseWithEmptyNutrientInNutrientsNode))
                .thenReturn(contentNode);
        OpenAiException ex = assertThrows(OpenAiException.class, () -> ingredientService.addIngredientByOpenAi(ingredientOpenAiDto));
        assertTrue(ex.getMessage().contains("Antwort von OpenAI für Nährstoffe ist leer oder unbrauchbar. Änderne die Anfrage und versuche es erneut."));
        Mockito.verify(mockNutrientOpenAiService, Mockito.times(1))
                .getNutrients(
                        ingredientOpenAiDto.product(),
                        ingredientOpenAiDto.variation()
                );
        Mockito.verify(mockObjectMapper, Mockito.times(1)).readTree(responseWithEmptyNutrientInNutrientsNode);
    }
    @Test
    void addIngredientByOpenAi_shouldThrowOpenAiException_whenNutrient_energyKcal_InNutrientsDtoOfResponseIsNull() throws JsonProcessingException {
        // given
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode contentNode = objectMapper.readTree(responseWithoutNutrientEnergyKcal);
        // when
        Mockito.when(mockNutrientOpenAiService.getNutrients(ingredientOpenAiDto.product(), ingredientOpenAiDto.variation())
        ).thenReturn(responseWithoutNutrientEnergyKcal);
        Mockito.when(mockObjectMapper.readTree(responseWithoutNutrientEnergyKcal))
                .thenReturn(contentNode);
        OpenAiException ex = assertThrows(OpenAiException.class, () -> ingredientService.addIngredientByOpenAi(ingredientOpenAiDto));
        assertTrue(ex.getMessage().contains("Antwort von OpenAI für Nährstoffe ist leer oder unbrauchbar. Änderne die Anfrage und versuche es erneut."));
        Mockito.verify(mockNutrientOpenAiService, Mockito.times(1))
                .getNutrients(
                        ingredientOpenAiDto.product(),
                        ingredientOpenAiDto.variation()
                );
        Mockito.verify(mockObjectMapper, Mockito.times(1)).readTree(responseWithoutNutrientEnergyKcal);
    }

    @Test
    void addIngredientByOpenAi_shouldThrowOpenAiException_whenNutrientsDtoInResponseContainsInvalidNutrient() throws JsonProcessingException {
        // given
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode contentNode = objectMapper.readTree(responseNutrientsWithInvalidNutrient);
        ObjectNode nutrientsNode = (ObjectNode) contentNode.get("nutrientsDto");
        // when
        Mockito.when(mockNutrientOpenAiService.getNutrients(ingredientOpenAiDto.product(), ingredientOpenAiDto.variation())
        ).thenReturn(responseNutrientsWithInvalidNutrient);
        Mockito.when(mockObjectMapper.readTree(responseNutrientsWithInvalidNutrient))
                .thenReturn(contentNode);
        Mockito.when(mockObjectMapper.treeToValue(nutrientsNode, Nutrients.class))
                .thenThrow(JsonProcessingException.class);
        // then
        OpenAiException ex = assertThrows(OpenAiException.class, () -> ingredientService.addIngredientByOpenAi(ingredientOpenAiDto));
        assertTrue(ex.getMessage().contains("Antwort von OpenAI für Nährstoffe ist unbrauchbar. Änderne die Anfrage und versuche es erneut."));
        Mockito.verify(mockNutrientOpenAiService, Mockito.times(1))
                .getNutrients(
                        ingredientOpenAiDto.product(),
                        ingredientOpenAiDto.variation()
                );
        Mockito.verify(mockObjectMapper, Mockito.times(1)).readTree(responseNutrientsWithInvalidNutrient);
    }

    @Test
    void addIngredientByOpenAi_shouldThrowDuplicateKeyException_whenProductVariationFoundByOpenAiExists() throws JsonProcessingException {
        // given
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode contentNode = objectMapper.readTree(correctResponse);
        // when
        Mockito.when(mockNutrientOpenAiService.getNutrients(
                ingredientOpenAiDto.product(),
                ingredientOpenAiDto.variation()
        )).thenReturn(correctResponse);
        Mockito.when(mockObjectMapper.readTree(correctResponse))
                .thenReturn(contentNode);
        Mockito.when(mockIngredientRepository.getIngredientByProductAndVariationContainsIgnoreCase(
                ingredientOpenAiDto.product(),
                ingredientOpenAiDto.variation()
        )).thenReturn(Optional.empty());
        Mockito.when(mockIngredientRepository.getIngredientByProductAndVariationContainsIgnoreCase(
                expectedIngredient.product(),
                expectedIngredient.variation()
        )).thenReturn(Optional.of(expectedIngredient));
        // then
        assertThrows(DuplicateKeyException.class,() -> ingredientService.addIngredientByOpenAi(ingredientOpenAiDto));
        Mockito.verify(mockNutrientOpenAiService, Mockito.times(1))
                .getNutrients(
                        ingredientOpenAiDto.product(),
                        ingredientOpenAiDto.variation()
                );
        Mockito.verify(mockIngredientRepository, Mockito.times(1))
                .getIngredientByProductAndVariationContainsIgnoreCase(
                        ingredientOpenAiDto.product(),
                        ingredientOpenAiDto.variation()
                );
        Mockito.verify(mockIngredientRepository, Mockito.times(1))
                .getIngredientByProductAndVariationContainsIgnoreCase(
                        expectedIngredient.product(),
                        expectedIngredient.variation()
                );
        Mockito.verify(mockObjectMapper, Mockito.times(1)).readTree(correctResponse);
    }
    @Test
    void addIngredientByOpenAi_shouldThrowDuplicateKeyException_whenProductVariationExists() {
        Mockito.when(mockIngredientRepository.getIngredientByProductAndVariationContainsIgnoreCase(
                ingredientOpenAiDto.product(),
                ingredientOpenAiDto.variation()
        )).thenReturn(Optional.of(milk));
        // then
        assertThrows(DuplicateKeyException.class,() -> ingredientService.addIngredientByOpenAi(ingredientOpenAiDto));
    }

    @Test
    void updateIngredient_shouldReturn_updatedIngredient() {
        // when
        Mockito.when(mockIngredientRepository.findById(milkOrig.id())).thenReturn(Optional.of(milkOrig));
        // then
        assertEquals(milk, ingredientService.updateIngredient(milkOrig.id(), milkDto));
        // verify
        Mockito.verify(mockIngredientRepository, Mockito.times(1)).findById(milkOrig.id());
        Mockito.verify(mockIngredientRepository, Mockito.times(1)).save(milk);
    }
    @Test
    void updateIngredient_shouldThrowDuplicateKeyException_whenProductVariationExists(){
        // when
        Mockito.when(mockIngredientRepository
                .getIngredientByProductAndVariationContainsIgnoreCase(milkDto.product(),milkDto.variation())
        ).thenReturn(Optional.of(milkDouble));
        // then
        assertThrows(DuplicateKeyException.class,() -> ingredientService.updateIngredient(milkOrig.id(), milkDto));
        // verify
        Mockito.verify(mockIngredientRepository, Mockito.times(1))
                .getIngredientByProductAndVariationContainsIgnoreCase(milkDto.product(),milkDto.variation());
    }
    @Test
    void updateIngredient_should_updatedIngredient_whenProductVariationExists_AndMatchesWithTheGivenId() {
        // when
        Mockito.when(mockIngredientRepository
                .getIngredientByProductAndVariationContainsIgnoreCase(milkDto.product(),milkDto.variation())
        ).thenReturn(Optional.of(milk));
        // then
        assertEquals(milk, ingredientService.updateIngredient(milkOrig.id(), milkDto));
        // verify
        Mockito.verify(mockIngredientRepository, Mockito.times(1))
                .getIngredientByProductAndVariationContainsIgnoreCase(milkDto.product(),milkDto.variation());
    }

    @Test
    void getNutrientsDaily_shouldThrowProductVariationNotFound_whenProductVariationIsNotFound() {
        assertThrows(ProductVariationNotFoundException.class, ()->ingredientService.getNutrientsDaily());
    }
    @Test
    void getNutrientsDaily_shouldReturn_NutrientsDaily() {
        // given
        Ingredient ingredient = new Ingredient("ingredientId","Nährstoffe", "Täglicher Bedarf", "slug", 0.0, "g", 0.0,"nutrientId");
        Nutrients expected = Instancio.of(Nutrients.class)
                        .set(field(Nutrients::id), "nutrientId")
                        .create();
        // when
        Mockito.when(mockNutrientService
                .getNutrientsById(ingredient.nutrientsId())
        ).thenReturn(expected);
        Mockito.when(mockIngredientRepository
                .getIngredientByProductAndVariationContainsIgnoreCase(
                    ingredient.product(),
                    ingredient.variation())
        ).thenReturn(Optional.of(ingredient));
        // then
        assertEquals(expected, ingredientService.getNutrientsDaily());
        Mockito.verify(mockNutrientService, Mockito.times(1))
                .getNutrientsById(ingredient.nutrientsId());
        Mockito.verify(mockIngredientRepository, Mockito.times(1))
                .getIngredientByProductAndVariationContainsIgnoreCase(ingredient.product(), ingredient.variation());
    }
}