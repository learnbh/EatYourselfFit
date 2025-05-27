package org.bea.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bea.backend.model.Ingredient;
import org.bea.backend.model.IngredientDto;
import org.bea.backend.repository.IngredientRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
    public void addIngredient_shouldReturn_createdIngredient() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/eyf/ingredients")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(milkDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpectAll(
                    MockMvcResultMatchers.jsonPath("$.id").exists(),
                    MockMvcResultMatchers.jsonPath("$.product").value(milk.product()),
                    MockMvcResultMatchers.jsonPath("$.quantity").value(milk.quantity()),
                    MockMvcResultMatchers.jsonPath("$.unit").value(milk.unit()),
                    MockMvcResultMatchers.jsonPath("$.prices").value(milk.prices()),
                    MockMvcResultMatchers.jsonPath("$.nutrientsId").value(milk.nutrientsId()
                )
        );
    }
}
