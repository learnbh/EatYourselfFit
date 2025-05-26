package org.bea.backend.model;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Ingredients")
@CompoundIndex(def = "{'product' : 1, 'variation' : 1}", unique = true)
public record Ingredient(
    String id,
    @NotNull String product,
    String variation,
    @NotNull Double quantity,
    @NotNull String unit,
    double prices,
    String nutrientsId
) {
    public Ingredient(String id, String product, String variation, Double quantity, String unit, double prices, String nutrientsId) {
        this.id = id;
        this.product = product;
        this.variation = (variation == null) ? "" : variation;
        this.quantity = quantity;
        this.unit = unit;
        this.prices = prices;
        this.nutrientsId = nutrientsId;
    }
}
