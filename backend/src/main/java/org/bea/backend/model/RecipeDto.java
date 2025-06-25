package org.bea.backend.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record RecipeDto(
        @NotNull(message = "The recipe title cannot be null")
        @Size(min = 1, message = "The recipe title must have at least 1 character")
        String title,
        List<RecipeIngredient> recipeIngredients
) {}
