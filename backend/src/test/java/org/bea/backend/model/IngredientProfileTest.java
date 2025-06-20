package org.bea.backend.model;

import org.bea.backend.FakeTestData.IngredientCreateFakeData;
import org.bea.backend.enums.NutrientType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IngredientProfileTest {
    @Test
    void ingredientProfileEquality_shouldWorkWithArrayContent() {
        Nutrient[] nutrients1 = { new Nutrient("Fett", NutrientType.MACRONUTRIENT, 50.0, "g"), new Nutrient("Eiweiß", NutrientType.MACRONUTRIENT, 60.0, "g") };
        Nutrient[] nutrients2 = { new Nutrient("Fett",NutrientType.MACRONUTRIENT, 50.0, "g"), new Nutrient("Eiweiß", NutrientType.MACRONUTRIENT, 60.0, "g") };

        IngredientCreate ingredient = IngredientCreateFakeData.ingredientCreate;

        IngredientProfile profile1 = new IngredientProfile(ingredient, nutrients1);
        IngredientProfile profile2 = new IngredientProfile(ingredient, nutrients2);

        assertEquals(profile1, profile2);
        assertEquals(profile1.hashCode(), profile2.hashCode());
    }

}