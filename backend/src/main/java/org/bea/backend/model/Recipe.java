package org.bea.backend.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("Recipe")
public record Recipe(
        @Id
        String id,
        @NotNull(message = "The recipe title cannot be null")
        @Size(min = 1, message = "The recipe title must have at least 1 character")
        @Indexed(unique = true)
        String title,
        @NotNull(message = "slug cannot be null")
        @Size(min = 10, message = "slug must have at least 1 character")
        String slug,
        List<RecipeIngredient> recipeIngredients
) {}
