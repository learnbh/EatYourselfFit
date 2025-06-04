package org.bea.backend.model;

import org.bea.backend.enums.NutrientType;

public record Nutrient(
        String name,
        NutrientType type,
        Double quantity,
        String unit) {}
