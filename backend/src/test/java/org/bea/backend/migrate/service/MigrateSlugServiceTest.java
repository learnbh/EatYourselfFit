package org.bea.backend.migrate.service;

import org.bea.backend.repository.IngredientRepository;
import org.bea.backend.repository.RecipeRepository;
import org.bea.backend.service.ServiceId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


class MigrateSlugServiceTest {

    private RecipeRepository mockRecipeRepository;
    private IngredientRepository mockIngredientRepository;
    private ServiceId mockServiceId;

    private MigrateSlugService migrateSlugService;

    @BeforeEach
    void setUp() {
        mockRecipeRepository = Mockito.mock(RecipeRepository.class);
        mockIngredientRepository = Mockito.mock(IngredientRepository.class);
        mockServiceId = Mockito.mock(ServiceId.class);

        migrateSlugService = new MigrateSlugService(mockRecipeRepository, mockIngredientRepository, mockServiceId);
    }

    @Test
    void migrateRecipeAddSlug_shoudReturnFalse () {
        // when
        Mockito.when(mockRecipeRepository.existsById(Mockito.anyString())).thenReturn(true);

    }

    @Test
    void migrateIngredientAddSlug() {
    }
}