package org.bea.backend.model;

import lombok.With;
import org.bea.backend.enums.NutrientType;

public record Nutrient(
        String name,
        NutrientType type,
        @With Double quantity,
        String unit) {}
