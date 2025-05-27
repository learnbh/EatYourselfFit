package org.bea.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bea.backend.model.Ingredient;
import org.bea.backend.repository.IngredientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
        mockMvc.perform(MockMvcRequestBuilders.get("/eyf/ingredients"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(mapper.writeValueAsString(expected)));
    }
}
