
package org.bea.backend.model;

import lombok.Builder;
import lombok.With;
@Builder
@With
public record Nutrients(
    String id,
    // Macronutrients
    Nutrient energyKcal,
    Nutrient energyKJ,
    Nutrient fat,
    Nutrient protein,
    Nutrient carbohydrate,
    Nutrient fiber,
    Nutrient water,
    // Vitamins
    Nutrient vitaminA,
    Nutrient vitaminB1,
    Nutrient vitaminB2,
    Nutrient vitaminB3,
    Nutrient vitaminB5,
    Nutrient vitaminB6,
    Nutrient vitaminB7,
    Nutrient vitaminB9,
    Nutrient vitaminB12,
    Nutrient vitaminC,
    Nutrient vitaminD,
    Nutrient vitaminE,
    Nutrient vitaminK,
    // Major elements
    Nutrient salt,
    Nutrient pral,
    Nutrient sodium,
    Nutrient potassium,
    Nutrient calcium,
    Nutrient magnesium,
    Nutrient phosphorus,
    Nutrient sulfur,
    Nutrient chloride,
    // Trac elements
    Nutrient iron,
    Nutrient zinc,
    Nutrient copper,
    Nutrient manganese,
    Nutrient fluoride,
    Nutrient ioide,
    // Amino acid essential
    Nutrient isoleucin,
    Nutrient leucin,
    Nutrient lysin,
    Nutrient methionin,
    Nutrient cystein,
    Nutrient phenylalanin,
    Nutrient tyrosin,
    Nutrient threonin,
    Nutrient tryptophan,
    Nutrient valin,
    Nutrient arginin,
    Nutrient histidin,
    // Amino acid non essential
    Nutrient alanin,
    Nutrient asparaginacid,
    Nutrient glutaminacid,
    Nutrient glycin,
    Nutrient prolin,
    Nutrient serin
    ) {}
