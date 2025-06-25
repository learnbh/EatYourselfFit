package org.bea.backend.service;

import org.bea.backend.exception.IdNotFoundException;
import org.bea.backend.model.Recipe;
import org.bea.backend.model.RecipeDto;
import org.bea.backend.model.RecipeIngredient;
import org.bea.backend.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RecipeServiceTest {

    private RecipeRepository mockRecipeRepository;
    private ServiceId mockServiceId;

    private RecipeService recipeService;

    private Recipe recipeActual;
    private Recipe recipeExpected;
    private Recipe recipeExpectedNoIngredient;
    private RecipeDto recipeDto;

    @BeforeEach
    void setUp() {
        mockRecipeRepository = Mockito.mock(RecipeRepository.class);
        mockServiceId = Mockito.mock(ServiceId.class);

        recipeService = new RecipeService(mockRecipeRepository, mockServiceId);

        RecipeIngredient pasta = new RecipeIngredient("ingredient1", 1.0);
        RecipeIngredient tomato = new RecipeIngredient("ingredient2", 2.0);

        recipeActual = new Recipe("recipeExpected", "Spaghetti", List.of(pasta));
        recipeExpected = new Recipe("recipeExpected", "Spaghetti Napoli", List.of(pasta, tomato));
        recipeExpectedNoIngredient = new Recipe("recipeExpected", "Mystery Dish", Collections.emptyList());

        recipeDto = new RecipeDto(recipeExpected.title(), recipeExpected.recipeIngredients());
    }

    @Test
    void getAllRecipes_shouldReturnEmptyListIfNoRecipesIsFound () {
        // when
        Mockito.when(mockRecipeRepository.findAll()).thenReturn(Collections.emptyList());
        // then
        assertEquals(Collections.emptyList(), recipeService.getAllRecipes());
        // verify
        Mockito.verify(mockRecipeRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getAllRecipes_shouldReturnAllRecipesEvenIfListOfIngredientIsEmpty () {
        // when
        Mockito.when(mockRecipeRepository.findAll()).thenReturn(List.of(recipeExpected, recipeExpectedNoIngredient));
        // then
        assertEquals(List.of(recipeExpected, recipeExpectedNoIngredient), recipeService.getAllRecipes(), "The service should return all recipes including ones with empty ingredient lists");
        // verify
        Mockito.verify(mockRecipeRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getAllRecipes_shouldReturnAllRecipesFoundInDB() {
        // when
        Mockito.when(mockRecipeRepository.findAll()).thenReturn(List.of(recipeExpected));
        // then
        assertEquals(List.of(recipeExpected), recipeService.getAllRecipes());
        // verify
        Mockito.verify(mockRecipeRepository, Mockito.times(1)).findAll();
    }

    @Test
    void findRecipeById_shouldUseMethod_findById_OfRecipeRepository() {
        // when
        recipeService.findRecipeById(recipeExpected.id());
        // verify
        Mockito.verify(mockRecipeRepository, Mockito.times(1)).findById(recipeExpected.id());
    }

    @Test
    void getRecipeById_shouldThrowIdNotFound_IfRecipeIdDoesNotExist() {
        assertThrows(IdNotFoundException.class, ()->recipeService.getRecipeById("wrongId"));
    }

    @Test
    void getRecipeById_shouldReturnARecipeIfIdIsValid() {
        // when
        Mockito.when(mockRecipeRepository.findById(recipeExpected.id())).thenReturn(Optional.of(recipeExpected));
        // then
        assertEquals(recipeExpected, recipeService.getRecipeById(recipeExpected.id()));
        // verify
        Mockito.verify(mockRecipeRepository, Mockito.times(1)).findById(recipeExpected.id());
    }

    @Test
    void addRecipe_ShouldAddRecipeToDB() {
        // when
        Mockito.when(mockServiceId.generateId()).thenReturn(recipeExpected.id());
        // then
        assertEquals(recipeExpected, recipeService.addRecipe(recipeDto));
        // verify
        Mockito.verify(mockServiceId, Mockito.times(1)).generateId();
        Mockito.verify(mockRecipeRepository, Mockito.times(1)).save(recipeExpected);
    }

    @Test
    void updateRecipe_shouldThrowIdNotFound_IfRecipeIdDoesNotExist() {
        assertThrows(IdNotFoundException.class, ()->recipeService.updateRecipe(recipeExpected.id(),recipeDto));
        Mockito.verify(mockRecipeRepository, Mockito.times(1)).findById(recipeExpected.id());
    }

    @Test
    void updateRecipe_shouldReturn_UpdatedRecipeIfIdIsValid() {
        // when
        Mockito.when(mockRecipeRepository.findById(recipeActual.id())).thenReturn(Optional.of(recipeActual));
        // then
        assertEquals(recipeExpected, recipeService.updateRecipe(recipeExpected.id(),recipeDto));
        // verify
        Mockito.verify(mockRecipeRepository, Mockito.times(1)).findById(recipeExpected.id());
        Mockito.verify(mockRecipeRepository, Mockito.times(1)).save(recipeExpected);
    }
}