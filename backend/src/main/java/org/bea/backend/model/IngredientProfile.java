package org.bea.backend.model;

import jakarta.validation.Valid;

import java.util.Arrays;
import java.util.Objects;

public record IngredientProfile(
    @Valid
    IngredientCreate ingredientCreate,
    Nutrient[] nutrientsArray
) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IngredientProfile(IngredientCreate create, Nutrient[] array))) return false;
        return Objects.equals(ingredientCreate, create) &&
                Arrays.equals(nutrientsArray, array);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredientCreate, Arrays.hashCode(nutrientsArray));
    }

    @Override
    public String toString() {
        return "IngredientProfile{" +
                "ingredientCreate=" + ingredientCreate +
                ", nutrientsArray=" + Arrays.toString(nutrientsArray) +
                '}';
    }
}
