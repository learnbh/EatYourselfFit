package org.bea.backend.model;

public record IngredientDto(
        String product,
        String variation,
        Double quantity,
        String unit,
        Double prices,
        String nutrientsId
) {
}
