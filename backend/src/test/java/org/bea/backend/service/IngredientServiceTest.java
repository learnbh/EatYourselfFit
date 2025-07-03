package org.bea.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.dao.DuplicateKeyException;
import org.bea.backend.FakeTestData.IngredientCreateFakeData;
import org.bea.backend.exception.IdNotFoundException;
import org.bea.backend.exception.OpenAiNotFoundIngredientException;
import org.bea.backend.exception.ProductVariationNotFoundException;
import org.bea.backend.mapper.NutrientMapper;
import org.bea.backend.model.Ingredient;
import org.bea.backend.model.IngredientDto;
import org.bea.backend.model.IngredientProfile;
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
    private IngredientService ingredientService;

    IngredientProfile ingredientProfile;
    Nutrients expectedNutrients;
    Ingredient expectedIngredient;

    Ingredient milkOrig;
    Ingredient milkFindByName;
    Ingredient milkDouble;
    Ingredient milk;
    IngredientDto milkDto;
    IngredientOpenAiDto ingredientOpenAiDto;

    @BeforeEach
    void setUp() {

        mockIngredientRepository = Mockito.mock(IngredientRepository.class);
        mockNutrientService = Mockito.mock(NutrientService.class);
        mockServiceId = Mockito.mock(ServiceId.class);
        mockNutrientMapper = Mockito.mock(NutrientMapper.class);
        mockNutrientOpenAiService = Mockito.mock(NutrientOpenAiService.class);
        ingredientService = new IngredientService(
                mockServiceId,
                mockIngredientRepository,
                mockNutrientService,
                mockNutrientMapper,
                mockNutrientOpenAiService
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
    public void addIngredient_shouldReturn_createdIngredient() {
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
                .getNutrients(ingredientOpenAiDto.product(), ingredientOpenAiDto.variation())
        ).thenReturn(OpenAiConfig.ingredientResponseTest);
        Mockito.when(mockServiceId
                .generateId()
        ).thenReturn("nutrientId", "ingredientId");
        Mockito.when(mockNutrientService
                .addNutrients(nutrients)
        ).thenReturn(nutrients);
        Mockito.when(mockIngredientRepository
                .getIngredientByProductAndVariationContainsIgnoreCase(ingredientOpenAiDto.product(),ingredientOpenAiDto.variation())
        ).thenReturn(Optional.empty());
        Mockito.when(mockIngredientRepository
                .getIngredientByProductAndVariationContainsIgnoreCase(ingredient.product(),ingredient.variation())
        ).thenReturn(Optional.empty());
        // then
        assertEquals(ingredient, ingredientService.addIngredientByOpenAi(ingredientOpenAiDto));
        Mockito.verify(mockNutrientOpenAiService, Mockito.times(1))
                .getNutrients(
                        ingredientOpenAiDto.product(),
                        ingredientOpenAiDto.variation()
                );
        Mockito.verify(mockIngredientRepository, Mockito.times(1))
                .save(ingredient);
        Mockito.verify(mockIngredientRepository, Mockito.times(1))
                .getIngredientByProductAndVariationContainsIgnoreCase(
                    ingredientOpenAiDto.product(),
                    ingredientOpenAiDto.variation()
                );
        Mockito.verify(mockIngredientRepository, Mockito.times(1))
                .getIngredientByProductAndVariationContainsIgnoreCase(
                        ingredient.product(),
                        ingredient.variation()
                );
        Mockito.verify(mockNutrientService, Mockito.times(1)).addNutrients(nutrients);
    }
    @Test
    public void addIngredientByOpenAi_shouldThrowOpenAiNotFoundIngredientException_whenJsonIsWrong(){
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
        assertThrows(OpenAiNotFoundIngredientException.class, () -> ingredientService.addIngredientByOpenAi(ingredientOpenAiDto));
        Mockito.verify(mockNutrientOpenAiService, Mockito.times(1))
                .getNutrients(
                        ingredientOpenAiDto.product(),
                        ingredientOpenAiDto.variation()
                );
    }
    @Test
    public void addIngredientByOpenAi_shouldThrowOpenAiNotFoundIngredientException_whenNoIngredientOrNutrientsDtoInJson(){
        // when
        Mockito.when(mockNutrientOpenAiService
                .getNutrients(ingredientOpenAiDto.product(), ingredientOpenAiDto.variation())
        ).thenReturn("{}");
        OpenAiNotFoundIngredientException ex = assertThrows(OpenAiNotFoundIngredientException.class, () -> ingredientService.addIngredientByOpenAi(ingredientOpenAiDto));
        assertTrue(ex.getMessage().contains("ist leer"));
        Mockito.verify(mockNutrientOpenAiService, Mockito.times(1))
                .getNutrients(
                        ingredientOpenAiDto.product(),
                        ingredientOpenAiDto.variation()
                );
    }
    @Test
    public void addIngredientByOpenAi_shouldThrowOpenAiNotFoundIngredientException_whenNoNutrientsDtoInJson(){
        // when
        Mockito.when(mockNutrientOpenAiService
                .getNutrients(ingredientOpenAiDto.product(), ingredientOpenAiDto.variation())
        ).thenReturn("""
            {
                "ingredientDto": {
                    "product": "Banane",
                    "variation": "Frisch",
                    "quantity": 100,
                    "unit": "g"
                }
            }
        """);
        OpenAiNotFoundIngredientException ex = assertThrows(OpenAiNotFoundIngredientException.class, () -> ingredientService.addIngredientByOpenAi(ingredientOpenAiDto));
        assertTrue(ex.getMessage().contains("Antwort von OpenAI für Nutrients ist leer"));
        Mockito.verify(mockNutrientOpenAiService, Mockito.times(1))
                .getNutrients(ingredientOpenAiDto.product(), ingredientOpenAiDto.variation());
    }
    @Test
    public void addIngredientByOpenAi_shouldThrowOpenAiNotFoundIngredientException_whenIngredientFieldsAreInvalid(){
        // when
        Mockito.when(mockNutrientOpenAiService.getNutrients(ingredientOpenAiDto.product(), ingredientOpenAiDto.variation())
        ).thenReturn("""
            {
                "ingredientDto": {
                     "product": "Apfel",
                     "variation": "Getrocknet",
                     "quantity": 50,
                     "unit": "ml"
                },
                "nutrientsDto": {
                    "energyKcal": 52
                }
            }
        """);
        OpenAiNotFoundIngredientException ex = assertThrows(OpenAiNotFoundIngredientException.class, () -> ingredientService.addIngredientByOpenAi(ingredientOpenAiDto));
        assertTrue(ex.getMessage().contains("Antwort von OpenAI für Ingredient ist unbrauchbar"));
        Mockito.verify(mockNutrientOpenAiService, Mockito.times(1))
                .getNutrients(
                        ingredientOpenAiDto.product(),
                        ingredientOpenAiDto.variation()
                );
    }
    @Test
    public void addIngredientByOpenAi_shouldOpenAiNotFoundIngredientException_whenNutrientsNotFound(){
        // when
        Mockito.when(mockNutrientOpenAiService
                .getNutrients(ingredientOpenAiDto.product(), ingredientOpenAiDto.variation())
        ).thenReturn("Es konnten keine Nährstoffe gefunden werden. Änderne die Anfrage und versuche es erneut.");
        // then
        assertThrows(OpenAiNotFoundIngredientException.class, () -> ingredientService.addIngredientByOpenAi(ingredientOpenAiDto));
        // verify
        Mockito.verify(mockNutrientOpenAiService, Mockito.times(1))
                .getNutrients(
                        ingredientOpenAiDto.product(),
                        ingredientOpenAiDto.variation()
                );
    }
    @Test
    public void addIngredientByOpenAi_shouldThrowDuplicateKeyException_whenProductVariationFoundByOpenAiExists() throws JsonProcessingException {
        // given
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode contentNode = objectMapper.readTree(OpenAiConfig.ingredientResponseTest);
        ObjectNode nutrientsNode = (ObjectNode) contentNode.get("nutrientsDto");
        nutrientsNode.put("id", "nutrientId");
        ObjectNode ingredientNode = (ObjectNode) contentNode.get("ingredientDto");
        ingredientNode.put("id", "ingredientId");
        ingredientNode.put("nutrientsId", "nutrientId");
        Ingredient ingredient = objectMapper.treeToValue(ingredientNode, Ingredient.class);
        // when
        Mockito.when(mockNutrientOpenAiService.getNutrients(
                ingredientOpenAiDto.product(),
                ingredientOpenAiDto.variation()
        )).thenReturn(OpenAiConfig.ingredientResponseTest);
        Mockito.when(mockIngredientRepository.getIngredientByProductAndVariationContainsIgnoreCase(
                ingredientOpenAiDto.product(),
                ingredientOpenAiDto.variation()
        )).thenReturn(Optional.empty());
        Mockito.when(mockIngredientRepository.getIngredientByProductAndVariationContainsIgnoreCase(
                ingredient.product(),
                ingredient.variation()
        )).thenReturn(Optional.of(ingredient));
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
                        ingredient.product(),
                        ingredient.variation()
                );
    }
    @Test
    public void addIngredientByOpenAi_shouldThrowDuplicateKeyException_whenProductVariationExists() {
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
    public void updateIngredient_shouldThrowDuplicateKeyException_whenProductVariationExists(){
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