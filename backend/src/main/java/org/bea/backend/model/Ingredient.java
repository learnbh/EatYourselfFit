package org.bea.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Ingredients")
@CompoundIndex(def = "{'product' : 1, 'variation' : 1}", unique = true)
public record Ingredient(
    @Id
    String id,

    String product,
    String variation,

    Double quantity,
    String unit,
    Double prices,

    String nutrientsId
) {}
