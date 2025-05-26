package org.bea.backend.service;

import org.bea.backend.repository.IngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class IngredientServiceTest {

    private IngredientService ingredientService;

    @BeforeEach
    void setUp() {
        IngredientRepository mockIngredientRepository = Mockito.mock(IngredientRepository.class);
        ingredientService = new IngredientService(mockIngredientRepository);
    }

    @Test
    public void getIngredients_shouldReturn_EmptyList_whenMongoCollectionIsEmpty(){
        assertEquals(Collections.emptyList(), ingredientService.getIngredients());
    }
}