package org.bea.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bea.backend.model.Ingredient;
import org.bea.backend.model.IngredientDto;
import org.bea.backend.repository.IngredientRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class IngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private IngredientRepository mockIngredientRepository;

    ObjectMapper mapper = new ObjectMapper();

    Ingredient milk = new Ingredient("milk", "milk", "low fat", 100.0, "ml", 1.29, "egal");
    IngredientDto milkDto = new IngredientDto("milk", "low fat", 100.0, "ml", 1.29, "egal");
    IngredientDto milkDtoDuplicate = new IngredientDto("milk", "low fat", 100.0, "ml", 1.09, "good");
    IngredientDto milkDto2 = new IngredientDto("milk", "fat", 100.0, "ml", 1.59, "bad");
    IngredientDto invalidDtoMaxSize = new IngredientDto("", "low fat", 0.0, "", 1.29, "egal");
    IngredientDto invalidDtoNull = new IngredientDto(null, "low fat", null, null, 1.29, "egal");

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
    public void addIngredient_shouldReturnMethodArgumentNotValidException_forSizeAndMax_withInvalidIngredientDto() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/eyf/ingredients")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(invalidDtoNull)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpectAll(
                    MockMvcResultMatchers.jsonPath("$.messages.product")
                        .value("Product cannot be null"),
                    MockMvcResultMatchers.jsonPath("$.messages.quantity")
                        .value("Quantity cannot be null"),
                    MockMvcResultMatchers.jsonPath("$.messages.unit")
                        .value("Unit cannot be null"),
                    MockMvcResultMatchers.jsonPath("$.timestamp").exists(),
                    MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.BAD_REQUEST.name())
                );
    }
    @Test
    public void addIngredient_shouldReturnMethodArgumentNotValidException_forNull_withInvalidIngredientDto() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/eyf/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(invalidDtoMaxSize)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpectAll(
                        MockMvcResultMatchers.jsonPath("$.messages.product")
                                .value("Product must have at least 1 character"),
                        MockMvcResultMatchers.jsonPath("$.messages.quantity")
                                .value("Quantity must be at least 0.01"),
                        MockMvcResultMatchers.jsonPath("$.messages.unit")
                                .value("Unit must have at least 1 character"),
                        MockMvcResultMatchers.jsonPath("$.timestamp").exists(),
                        MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.BAD_REQUEST.name())
                );
    }
    @Test
    public void addIngredient_shouldReturn_createdIngredient_withValidIngredientDto() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/eyf/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(milkDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpectAll(
                        MockMvcResultMatchers.jsonPath("$.id").exists(),
                        MockMvcResultMatchers.jsonPath("$.product").value(milk.product()),
                        MockMvcResultMatchers.jsonPath("$.variation").value(milk.variation()),
                        MockMvcResultMatchers.jsonPath("$.quantity").value(milk.quantity()),
                        MockMvcResultMatchers.jsonPath("$.unit").value(milk.unit()),
                        MockMvcResultMatchers.jsonPath("$.prices").value(milk.prices()),
                        MockMvcResultMatchers.jsonPath("$.nutrientsId").value(milk.nutrientsId())
                );
    }
    @Test
    void addIngredient_shouldThrowException_whenDuplicate_ProductVariationCombination_IsInserted() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/eyf/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(milkDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.post("/eyf/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(milkDtoDuplicate)))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpectAll(
                        MockMvcResultMatchers
                                .jsonPath("$.error")
                                .value("Error: A ingredient with this product-variation combination already exists")
                );
    }
    @Test
    void addIngredient_shouldAllowDifferent_ProductVariationCombination() throws Exception {
        mockIngredientRepository.save(milk);
        mockMvc.perform(MockMvcRequestBuilders.post("/eyf/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(milkDto2)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpectAll(
                        MockMvcResultMatchers.jsonPath("$.id").exists(),
                        MockMvcResultMatchers.jsonPath("$.product").value(milkDto2.product()),
                        MockMvcResultMatchers.jsonPath("$.variation").value(milkDto2.variation()),
                        MockMvcResultMatchers.jsonPath("$.quantity").value(milkDto2.quantity()),
                        MockMvcResultMatchers.jsonPath("$.unit").value(milkDto2.unit()),
                        MockMvcResultMatchers.jsonPath("$.prices").value(milkDto2.prices()),
                        MockMvcResultMatchers.jsonPath("$.nutrientsId").value(milkDto2.nutrientsId())
                );
    }
}