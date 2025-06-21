package org.bea.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bea.backend.FakeTestData.IngredientCreateFakeData;
import org.bea.backend.model.*;
import org.bea.backend.openai.IngredientOpenAiDto;
import org.bea.backend.openai.OpenAiConfig;
import org.bea.backend.repository.IngredientRepository;

import org.bea.backend.repository.NutrientsRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.instancio.Select.field;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureMockRestServiceServer
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class IngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private IngredientRepository mockIngredientRepository;
    @Autowired
    private NutrientsRepository mockNutrientsRepository;

    // OpenAi
    @Value("${openai.api.url.base}")
    private String baseUrl;
    @Value("${openai.api.key}")
    private String openAiApiKey;
    @Autowired
    private MockRestServiceServer mockRestServer;

    ObjectMapper mapper = new ObjectMapper();

    Ingredient milkOrig = new Ingredient("milk", "milch", "fat", 90.0, "g", 1.09, "egal");
    Ingredient milkFindByName = new Ingredient("milkFindByName", "milk", "Vollmilch", 90.0, "g", 1.09, "egal");
    Ingredient milk = new Ingredient("milk", "milk", "low fat", 100.0, "ml", 1.29, "egal");
    IngredientDto milkDto = new IngredientDto("milk", "low fat", 100.0, "ml", 1.29, "egal");
    IngredientDto milkDtoDuplicate = new IngredientDto("milk", "low fat", 100.0, "ml", 1.09, "good");
    IngredientDto milkDto2 = new IngredientDto("milk", "fat", 100.0, "ml", 1.59, "bad");
    IngredientDto invalidDtoMaxSize = new IngredientDto("", "low fat", 0.0, "", 1.29, "egal");
    IngredientDto invalidDtoNull = new IngredientDto(null, "low fat", null, null, 1.29, "egal");

    IngredientProfile ingredientProfile = new IngredientProfile(
            IngredientCreateFakeData.ingredientCreate,
            IngredientCreateFakeData.nutrientsArray
    );
    IngredientProfile ingredientProfileDuplicate = new IngredientProfile(
            new IngredientCreate(
                    milkDtoDuplicate.product(),
                    milkDtoDuplicate.variation(),
                    milkDtoDuplicate.quantity(),
                    milkDtoDuplicate.unit(),
                    milkDtoDuplicate.prices()),
            IngredientCreateFakeData.nutrientsArray
    );
    IngredientProfile ingredientProfileNull = new IngredientProfile(
            new IngredientCreate(
                    invalidDtoNull.product(),
                    invalidDtoNull.variation(),
                    invalidDtoNull.quantity(),
                    invalidDtoNull.unit(),
                    invalidDtoNull.prices()),
            IngredientCreateFakeData.nutrientsArray
    );
    IngredientProfile ingredientProfileMaxSize = new IngredientProfile(
            new IngredientCreate(
                    invalidDtoMaxSize.product(),
                    invalidDtoMaxSize.variation(),
                    invalidDtoMaxSize.quantity(),
                    invalidDtoMaxSize.unit(),
                    invalidDtoMaxSize.prices()),
            IngredientCreateFakeData.nutrientsArray
    );
    IngredientProfile ingredientProfileProductVariationCombination = new IngredientProfile(
            new IngredientCreate(
                    milkDto2.product(),
                    milkDto2.variation(),
                    milkDto2.quantity(),
                    milkDto2.unit(),
                    milkDto2.prices()),
            IngredientCreateFakeData.nutrientsArray
    );

    IngredientOpenAiDto ingredientOpenAiDto = new IngredientOpenAiDto("rindehack", "");
    String openAiResponse = """
                {
                  "choices": [
                    {
                      "message": {
                        "role": "assistant",
                        "content": %s}}]}""";

    @Test
    void getIngredients_shouldHandleUnknownError() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/eyf/trigger-error"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.INTERNAL_SERVER_ERROR.name()));
    }

    @Test
    public void getIngredients_shouldReturn_EmptyList_whenDbIsEmpty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/eyf/ingredients"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
    @Test
    public void getIngredients_shouldReturn_IngredientsList_whenDbIsNotEmpty() throws Exception {
        // Given
        mockIngredientRepository.save(milk);
        List<Ingredient> expected = Collections.singletonList(milk);
        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/eyf/ingredients"))
        // then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(mapper.writeValueAsString(expected)));
    }

    @Test
    void getIngredientById_shouldThrowIdNotFoundException_whenIdIsNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/eyf/ingredients/ingredient/detail/"+milk.id()))
                .andExpect(MockMvcResultMatchers
                        .status().isNotFound())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.error").value("Error: Zutat wurde nicht gefunden."));
    }
    @Test
    void getIngredientById_shouldReturn_IngredientMilkForMilkId() throws Exception {
        mockIngredientRepository.save(milk);
        mockMvc.perform(MockMvcRequestBuilders.get("/eyf/ingredients/ingredient/detail/"+milk.id()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(mapper.writeValueAsString(milk)));
    }

    @Test
    void getIngredientByName_shouldReturnSetOfIngredients_whenNameIsInProductnameOrVariationnameOfStoredIngredientsInDB() throws Exception {
        // given
        Set<Ingredient> expected = new LinkedHashSet<>();
        expected.add(milkOrig);
        expected.add(milkFindByName);

        mockIngredientRepository.save(milkOrig);
        mockIngredientRepository.save(milkFindByName);
        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/eyf/ingredients/name/"+milkOrig.product()))
        // then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(mapper.writeValueAsString(expected)));
    }

    @Test
    public void addIngredient_shouldThrowMethodArgumentNotValidException_forSizeAndMax_withInvalidIngredientCreate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/eyf/ingredients")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(ingredientProfileMaxSize)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpectAll(
                        MockMvcResultMatchers.jsonPath("$.messages['ingredientCreate.product']")
                                .value("Product must have at least 1 character"),
                        MockMvcResultMatchers.jsonPath("$.messages['ingredientCreate.quantity']")
                                .value("Quantity must be at least 0.01"),
                        MockMvcResultMatchers.jsonPath("$.messages['ingredientCreate.unit']")
                                .value("Unit must have at least 1 character"),
                        MockMvcResultMatchers.jsonPath("$.timestamp").exists(),
                        MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.BAD_REQUEST.name())
                );
    }
    @Test
    public void addIngredient_shouldRThrowMethodArgumentNotValidException_forNull_withInvalidIngredientCreate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/eyf/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ingredientProfileNull)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpectAll(
                        MockMvcResultMatchers.jsonPath("$.messages['ingredientCreate.product']")
                                .value("Product cannot be null"),
                        MockMvcResultMatchers.jsonPath("$.messages['ingredientCreate.quantity']")
                                .value("Quantity cannot be null"),
                        MockMvcResultMatchers.jsonPath("$.messages['ingredientCreate.unit']")
                                .value("Unit cannot be null"),
                        MockMvcResultMatchers.jsonPath("$.timestamp").exists(),
                        MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.BAD_REQUEST.name())
                );
    }
    @Test
    void addIngredient_shouldThrowException_whenDuplicate_ProductVariationCombination_IsInserted() throws Exception {
        mockIngredientRepository.save(milk);
        mockMvc.perform(MockMvcRequestBuilders.post("/eyf/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ingredientProfileDuplicate)))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpectAll(
                        MockMvcResultMatchers
                                .jsonPath("$.error")
                                .value("Error: Eine Zutat mit dieser Produkt-Variation existiert bereits.")
                );
    }
    @Test
    void addIngredient_shouldAllowDifferent_ProductVariationCombination() throws Exception {
        mockIngredientRepository.save(milk);
        mockMvc.perform(MockMvcRequestBuilders.post("/eyf/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ingredientProfileProductVariationCombination)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(not(emptyOrNullString())));
    }

    @Test
    public void addIngredient_shouldReturn_createdIngredient() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/eyf/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ingredientProfile)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(not(emptyOrNullString())));
    }

    @Test
    void addIngredientByOpenAi_shouldAddCorrectIngredientAndTheirNutrients() throws Exception{
        // given
        String response = String.format(openAiResponse, mapper.writeValueAsString(OpenAiConfig.ingredientResponseTest));

        IngredientDto ingredientDto = mapper
                .readTree(OpenAiConfig.ingredientResponseTest)
                .path("ingredientDto")
                .traverse(mapper)
                .readValueAs(IngredientDto.class);

        mockRestServer.expect(requestTo(baseUrl+"/v1/chat/completions"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(MockRestRequestMatchers.header(HttpHeaders.AUTHORIZATION, "Bearer "+openAiApiKey))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));

        mockMvc.perform(MockMvcRequestBuilders.post("/eyf/ingredients/openai/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(ingredientOpenAiDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpectAll(
                        MockMvcResultMatchers.jsonPath("$.id").exists(),
                        MockMvcResultMatchers.jsonPath("$.product").value(ingredientDto.product()),
                        MockMvcResultMatchers.jsonPath("$.variation").value(ingredientDto.variation()),
                        MockMvcResultMatchers.jsonPath("$.quantity").value(ingredientDto.quantity()),
                        MockMvcResultMatchers.jsonPath("$.unit").value(ingredientDto.unit()),
                        MockMvcResultMatchers.jsonPath("$.prices").value(ingredientDto.prices()),
                        MockMvcResultMatchers.jsonPath("$.nutrientsId").exists()
                );
        mockRestServer.verify();
    }
    @Test
    void addIngredientByOpenAi_shouldThrowResponseStatusException_whenJsonIsWrong() throws Exception{
        String response = String.format(openAiResponse, mapper.writeValueAsString(OpenAiConfig.responseWithoutIngredientNode));

        System.out.println(response);
        mockRestServer.expect(requestTo(baseUrl+"/v1/chat/completions"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(MockRestRequestMatchers.header(HttpHeaders.AUTHORIZATION, "Bearer "+openAiApiKey))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));

        mockMvc.perform(MockMvcRequestBuilders.post("/eyf/ingredients/openai/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(ingredientOpenAiDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpectAll(
                        MockMvcResultMatchers
                                .jsonPath("$.error")
                                .value("Error: Antwort von OpenAI für Ingredient rindehack ist leer. Änderne die Anfrage und versuche es erneut.")
                );
        mockRestServer.verify();
    }
    @Test
    void addIngredientByOpenAi_shouldOpenAiNotFoundIngredientException_whenNutrientsNotFound() throws Exception{
        String response = String.format(openAiResponse, mapper.writeValueAsString(OpenAiConfig.responseWithoutIngredientNode));

        System.out.println(response);
        mockRestServer.expect(requestTo(baseUrl+"/v1/chat/completions"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(MockRestRequestMatchers.header(HttpHeaders.AUTHORIZATION, "Bearer "+openAiApiKey))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));

        mockMvc.perform(MockMvcRequestBuilders.post("/eyf/ingredients/openai/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ingredientOpenAiDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpectAll(
                        MockMvcResultMatchers
                                .jsonPath("$.error")
                                .value("Error: Antwort von OpenAI für Ingredient rindehack ist leer. Änderne die Anfrage und versuche es erneut.")
                );
        mockRestServer.verify();
    }

    @Test
    void updateIngredient_shouldReturn_updatedIngredient() throws Exception{
        // given
        mockIngredientRepository.save(milkOrig);
        // when
        mockMvc.perform(MockMvcRequestBuilders.put("/eyf/ingredients/"+milkOrig.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(milkDto)))
        // then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpectAll(
                        MockMvcResultMatchers.jsonPath("$.id").value(milkOrig.id()),
                        MockMvcResultMatchers.jsonPath("$.product").value(milk.product()),
                        MockMvcResultMatchers.jsonPath("$.variation").value(milk.variation()),
                        MockMvcResultMatchers.jsonPath("$.quantity").value(milk.quantity()),
                        MockMvcResultMatchers.jsonPath("$.unit").value(milk.unit()),
                        MockMvcResultMatchers.jsonPath("$.prices").value(milk.prices()),
                        MockMvcResultMatchers.jsonPath("$.nutrientsId").value(milkOrig.nutrientsId())
                );
    }

    @Test
    void getNutrientsDaily_shouldThrowProductVariationNotFound_whenProductVariationIsNotFound() throws Exception  {
        mockMvc.perform(MockMvcRequestBuilders.get("/eyf/ingredients/daily/nutrients"))
                .andExpect(MockMvcResultMatchers
                        .status().isNotFound())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.error").value("Error: Produkt, Variation: Nährstoffe, Täglicher Bedarf existiert nicht."));
    }
    @Test
    void getNutrientsDaily_shouldReturn_NutrientsDaily() throws Exception {
        // given
        Ingredient ingredient = new Ingredient("ingredientId","Nährstoffe", "Täglicher Bedarf", 0.0, "g", 0.0,"nutrientId");
        Nutrients expected = Instancio.of(Nutrients.class)
                .set(field(Nutrients::id), "nutrientId")
                .create();
        mockIngredientRepository.save(ingredient);
        mockNutrientsRepository.save(expected);
        mockMvc.perform(MockMvcRequestBuilders.get("/eyf/ingredients/daily/nutrients"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(mapper.writeValueAsString(expected)));
    }
}