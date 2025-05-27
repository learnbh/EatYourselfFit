package org.bea.backend.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("Ingredients")
public record Ingredient(
    String id,
    String product,
    String variation,
    Double quantity,
    String unit,
    Double prices,
    String nutrientsId
) {}
