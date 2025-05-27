package org.bea.backend.service;

import org.bea.backend.model.Ingredient;
import org.bea.backend.model.IngredientDto;
import org.bea.backend.repository.IngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IngredientServiceTest {

    private IngredientRepository mockIngredientRepository;
    private ServiceId mockServiceId;
    private IngredientService ingredientService;

    Ingredient milk;
    IngredientDto milkDto;

    @BeforeEach
    void setUp() {
        mockIngredientRepository = Mockito.mock(IngredientRepository.class);
        mockServiceId = Mockito.mock(ServiceId.class);
        ingredientService = new IngredientService(mockIngredientRepository, mockServiceId);

        milk = new Ingredient("milk", "milk", "low fat", 100.0, "ml", 1.29, "egal");
        milkDto = new IngredientDto("milk", "low fat", 100.0, "ml", 1.29, "egal");
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

    @Test
    public void addIngredient_shouldReturn_createdIngredient() {
        // given
        // when
        Mockito.when(mockServiceId.generateId()).thenReturn(milk.id());
        Mockito.when(mockIngredientRepository.save(milk)).thenReturn(milk);
        // then
        assertEquals(milk, ingredientService.addIngredient(milkDto));
        Mockito.verify(mockIngredientRepository, Mockito.times(1)).save(milk);
    }
}