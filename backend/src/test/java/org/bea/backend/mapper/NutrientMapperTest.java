package org.bea.backend.mapper;

import org.bea.backend.FakeTestData.IngredientCreateFakeData;
import org.bea.backend.model.Nutrient;
import org.bea.backend.model.Nutrients;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.bea.backend.enums.NutrientType.MACRONUTRIENT;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;

class NutrientMapperTest {

    @Test
    void updateNutrients_shouldMapNutrientarrayIntoNutrients_ReturnUpdatedNutrients(){
        // given
        NutrientMapper nutrientMapper = new NutrientMapper();
        Nutrients nutrients = Instancio.of(Nutrients.class)
                .set(field(Nutrients::id), "nutrientId")
                .set(field(Nutrients::energyKcal),  new Nutrient("Energie", MACRONUTRIENT,3.0, "kcal"))
                .create();
        Nutrient change = new Nutrient("Energie", MACRONUTRIENT,3.14, "kcal");
        Nutrient[] changeArr = {change};
        // when
        Nutrients actual = nutrientMapper.updateNutrients(nutrients, changeArr);
        // then
        assertEquals(nutrients.withEnergyKcal(change), actual);
    }

    @Test
    void createNutrients_shouldMapNutrientarrayIntoNutrients_ReturnCreatedNutrients(){
        // given
        NutrientMapper nutrientMapper = new NutrientMapper();
        Nutrients nutrients = IngredientCreateFakeData.nutrients;
        Nutrient[] nutrientsArray = IngredientCreateFakeData.nutrientsArray;
        // when
        Nutrients actual = nutrientMapper.createNutrients(nutrients.id(), nutrientsArray);
        // then
        assertEquals(nutrients, actual);
    }
}