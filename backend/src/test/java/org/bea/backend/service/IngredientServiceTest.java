package org.bea.backend.service;

import org.bea.backend.model.Ingredient;
import org.bea.backend.repository.IngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IngredientServiceTest {

    IngredientRepository mockIngredientRepository;
    private IngredientService ingredientService;

    Ingredient milk;

    @BeforeEach
    void setUp() {
        mockIngredientRepository = Mockito.mock(IngredientRepository.class);
        ingredientService = new IngredientService(mockIngredientRepository);

        milk = new Ingredient("milk", "milk", "low fat", 100.0, "ml", 1.29, "egal");
    }

    @Test
    public void getIngredients_shouldReturn_EmptyList_whenMongoCollectionIsEmpty(){
        assertEquals(Collections.emptyList(), ingredientService.getIngredients());
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

}