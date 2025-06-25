package org.bea.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bea.backend.model.Recipe;
import org.bea.backend.model.RecipeDto;
import org.bea.backend.model.RecipeIngredient;
import org.bea.backend.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
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
class RecipeControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    RecipeRepository mockRecipeRepository;

    private Recipe recipeActual;
    private Recipe recipeExpected;
    private Recipe recipeDublicateTitle;
    private Recipe recipeExpectedNoIngredient;
    private RecipeDto recipeDto;

    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        RecipeIngredient pasta = new RecipeIngredient("pasta", 1.0);
        RecipeIngredient tomato = new RecipeIngredient("tomato", 2.0);

        recipeActual = new Recipe("recipeExpected", "Spaghetti", List.of(pasta));
        recipeDublicateTitle = new Recipe("recipeDublicateTitle", "Spaghetti Napoli", List.of(pasta));
        recipeExpected = new Recipe("recipeExpected", "Spaghetti Napoli", List.of(pasta, tomato));
        recipeExpectedNoIngredient = new Recipe("recipeExpectedNoIngredient", "Mystery Dish", Collections.emptyList());

        recipeDto = new RecipeDto(recipeExpected.title(), recipeExpected.recipeIngredients());
    }

    @Test
    void getAllRecipes_shouldReturnEmptyListIfNoRecipesIsFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/eyf/recipe"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(mapper.writeValueAsString(Collections.emptyList())));
    }

    @Test
    void getAllRecipes_shouldReturnAllRecipesEvenIfListOfIngredientIsEmpty() throws Exception {
        mockRecipeRepository.save(recipeExpectedNoIngredient);
        mockMvc.perform(MockMvcRequestBuilders.get("/eyf/recipe"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(mapper.writeValueAsString(List.of(recipeExpectedNoIngredient))));
    }

    @Test
    void getAllRecipes_shouldReturnAllRecipesFoundInDB() throws Exception {
        mockRecipeRepository.save(recipeExpected);
        mockRecipeRepository.save(recipeExpectedNoIngredient);
        mockMvc.perform(MockMvcRequestBuilders.get("/eyf/recipe"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(mapper.writeValueAsString(List.of(recipeExpected, recipeExpectedNoIngredient))));
    }

    @Test
    void getRecipeById_shouldThrowIdNotFound_IfRecipeIdDoesNotExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/eyf/recipe/"+recipeExpected.id()))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.error")
                        .value("Error: Recipe with Id: " + recipeExpected.id() + " not found"));
    }

    @Test
    void getRecipeById_shouldReturnARecipeIfIdIsValid() throws Exception {
        mockRecipeRepository.save(recipeExpected);
        mockMvc.perform(MockMvcRequestBuilders.get("/eyf/recipe/"+recipeExpected.id()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(mapper.writeValueAsString(recipeExpected)));
    }

    @Test
    void addRecipe_ShouldThrowDuplicateKeyException_IfRecipeWithTitleExists() throws Exception {
        mockRecipeRepository.save(recipeDublicateTitle);
        mockMvc.perform(MockMvcRequestBuilders.post("/eyf/recipe")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(recipeDto))
        )
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers
                .jsonPath("$.error")
                .value("Error: Eine Zutat mit dieser Produkt-Variation existiert bereits."));
    }

    @Test
    void addRecipe_ShouldAddRecipeToDB() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/eyf/recipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(recipeDto))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpectAll(
                        MockMvcResultMatchers.jsonPath("$.id").exists(),
                        MockMvcResultMatchers.jsonPath("$.title")
                                .value(recipeExpected.title()),
                        MockMvcResultMatchers.jsonPath("$.recipeIngredients[0].ingredientId")
                                .value("pasta"),
                        MockMvcResultMatchers.jsonPath("$.recipeIngredients[0].quantity")
                                .value(1.0),
                        MockMvcResultMatchers.jsonPath("$.recipeIngredients[1].ingredientId")
                                .value("tomato"),
                        MockMvcResultMatchers.jsonPath("$.recipeIngredients[1].quantity")
                                .value(2.0)
                );
    }

    @Test
    void updateRecipe_shouldThrowIdNotFound_IfRecipeIdDoesNotExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/eyf/recipe/"+recipeActual.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(recipeDto))
        )
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.error")
                        .value("Error: Recipe with Id: " + recipeActual.id() + " not found"));
    }

    @Test
    void updateRecipe_shouldReturn_UpdatedRecipeIfIdIsValid() throws Exception {
        mockRecipeRepository.save(recipeActual);
        mockMvc.perform(MockMvcRequestBuilders.put("/eyf/recipe/"+recipeActual.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(recipeDto))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpectAll(
                        MockMvcResultMatchers.jsonPath("$.id").exists(),
                        MockMvcResultMatchers.jsonPath("$.title")
                                .value(recipeExpected.title()),
                        MockMvcResultMatchers.jsonPath("$.recipeIngredients[0].ingredientId")
                                .value("pasta"),
                        MockMvcResultMatchers.jsonPath("$.recipeIngredients[0].quantity")
                                .value(1.0),
                        MockMvcResultMatchers.jsonPath("$.recipeIngredients[1].ingredientId")
                                .value("tomato"),
                        MockMvcResultMatchers.jsonPath("$.recipeIngredients[1].quantity")
                                .value(2.0)
                );
    }
}