package org.bea.backend.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Ingredients")
@CompoundIndex(def = "{'product' : 1, 'variation' : 1}", unique = true)
public record Ingredient(
    @Id
    String id,

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
    Double prices,

    String nutrientsId
) {}
