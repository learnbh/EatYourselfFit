package org.bea.backend.model;

import jakarta.validation.Valid;

public record IngredientProfile(
    @Valid
    IngredientCreate ingredientCreate,
    Nutrient[] nutrientsArray
) {}
