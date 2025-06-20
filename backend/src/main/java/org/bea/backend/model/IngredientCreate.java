package org.bea.backend.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record IngredientCreate(
        @NotNull(message = "Product cannot be null")
        @Size(min = 1, message = "Product must have at least 1 character")
        String product,
        String variation,
        @NotNull(message = "Quantity cannot be null")
        @DecimalMin(value = "0.01", message = "Quantity must be at least 0.01")
        Double quantity,
        @NotNull(message = "Unit cannot be null")
        @Size(min = 1, message = "Unit must have at least 1 character")
        String unit,
        Double prices
        ){}
