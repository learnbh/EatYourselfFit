package org.bea.backend.mapper;

import org.bea.backend.enums.*;
import org.bea.backend.model.Nutrients;
import org.bea.backend.model.Nutrient;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
public class NutrientMapper {

    public Nutrients createNutrients(String id, Nutrient[] nutrientsArray) {

        Map<String, Nutrient> nutrientMap = mapNutrientArrayToMap(nutrientsArray);

        return new Nutrients (
                id,
                nutrientMap.get("energyKcal"),
                nutrientMap.get("energyKJ"),
                nutrientMap.get(Macronutrient.FAT.getInGerman()),
                nutrientMap.get(Macronutrient.PROTEIN.getInGerman()),
                nutrientMap.get(Macronutrient.CARBOHYDRATE.getInGerman()),
                nutrientMap.get(Macronutrient.FIBER.getInGerman()),
                nutrientMap.get(Macronutrient.WATER.getInGerman()),
                nutrientMap.get(Vitamin.A.getInGerman()),
                nutrientMap.get(Vitamin.B1_THIAMIN.getInGerman()),
                nutrientMap.get(Vitamin.B2_RIBOFLAVIN.getInGerman()),
                nutrientMap.get(Vitamin.B3.getInGerman()),
                nutrientMap.get(Vitamin.B5_PANTONTHETICACID.getInGerman()),
                nutrientMap.get(Vitamin.B6_PYRIDOXIN.getInGerman()),
                nutrientMap.get(Vitamin.B7_BIOTIN.getInGerman()),
                nutrientMap.get(Vitamin.B9_FOLICACID_TOTAL.getInGerman()),
                nutrientMap.get(Vitamin.B12_COBALAMIN.getInGerman()),
                nutrientMap.get(Vitamin.C_ASCORBINACID.getInGerman()),
                nutrientMap.get(Vitamin.D_CALCIFEROL.getInGerman()),
                nutrientMap.get(Vitamin.E.getInGerman()),
                nutrientMap.get(Vitamin.K.getInGerman()),
                nutrientMap.get(MajorElement.SALT.getInGerman()),
                nutrientMap.get(MajorElement.PRAL.getInGerman()),
                nutrientMap.get(MajorElement.SODIUM.getInGerman()),
                nutrientMap.get(MajorElement.POTASSIUM.getInGerman()),
                nutrientMap.get(MajorElement.CALCIUM.getInGerman()),
                nutrientMap.get(MajorElement.MAGNESIUM.getInGerman()),
                nutrientMap.get(MajorElement.PHOSPHORUS.getInGerman()),
                nutrientMap.get(MajorElement.SULFUR.getInGerman()),
                nutrientMap.get(MajorElement.CHLORIDE.getInGerman()),
                nutrientMap.get(TraceElement.IRON.getInGerman()),
                nutrientMap.get(TraceElement.ZINC.getInGerman()),
                nutrientMap.get(TraceElement.COPPER.getInGerman()),
                nutrientMap.get(TraceElement.MANGANESE.getInGerman()),
                nutrientMap.get(TraceElement.FLUORIDE.getInGerman()),
                nutrientMap.get(TraceElement.IODIDE.getInGerman()),
                nutrientMap.get(AminoacidEssential.ISOLEUCIN.getInGerman()),
                nutrientMap.get(AminoacidEssential.LEUCIN.getInGerman()),
                nutrientMap.get(AminoacidEssential.LYSIN.getInGerman()),
                nutrientMap.get(AminoacidEssential.METHIONIN.getInGerman()),
                nutrientMap.get(AminoacidEssential.CYSTEIN.getInGerman()),
                nutrientMap.get(AminoacidEssential.PHENYLALANIN.getInGerman()),
                nutrientMap.get(AminoacidEssential.TYROSIN.getInGerman()),
                nutrientMap.get(AminoacidEssential.THREONIN.getInGerman()),
                nutrientMap.get(AminoacidEssential.TRYPTOPHAN.getInGerman()),
                nutrientMap.get(AminoacidEssential.VALIN.getInGerman()),
                nutrientMap.get(AminoacidEssential.ARGININ.getInGerman()),
                nutrientMap.get(AminoacidEssential.HISTIDIN.getInGerman()),
                nutrientMap.get(Aminoacid.ALANIN.getInGerman()),
                nutrientMap.get(Aminoacid.ASPARAGINACID.getInGerman()),
                nutrientMap.get(Aminoacid.GLUTAMINACID.getInGerman()),
                nutrientMap.get(Aminoacid.GLYCIN.getInGerman()),
                nutrientMap.get(Aminoacid.PROLIN.getInGerman()),
                nutrientMap.get(Aminoacid.SERIN.getInGerman()));
    }

    public Nutrients updateNutrients(Nutrients currentNutrients, Nutrient[] updatedNutrients){

        Map<String, Nutrient> updatedMap = mapNutrientArrayToMap(updatedNutrients);
        // Durchlaufe alle Nutrients und aktualisiere die Menge (quantity)
        currentNutrients = currentNutrients.withEnergyKcal(updateNutrientQuantity(currentNutrients.energyKcal(), updatedMap.get("energyKcal")));
        currentNutrients = currentNutrients.withEnergyKJ(updateNutrientQuantity(currentNutrients.energyKJ(), updatedMap.get("energyKJ")));
        currentNutrients = currentNutrients.withFat(updateNutrientQuantity(currentNutrients.fat(), updatedMap.get(Macronutrient.FAT.getInGerman())));
        currentNutrients = currentNutrients.withProtein(updateNutrientQuantity(currentNutrients.protein(), updatedMap.get(Macronutrient.PROTEIN.getInGerman())));
        currentNutrients = currentNutrients.withCarbohydrate(updateNutrientQuantity(currentNutrients.carbohydrate(), updatedMap.get(Macronutrient.CARBOHYDRATE.getInGerman())));
        currentNutrients = currentNutrients.withFiber(updateNutrientQuantity(currentNutrients.fiber(), updatedMap.get(Macronutrient.FIBER.getInGerman())));
        currentNutrients = currentNutrients.withWater(updateNutrientQuantity(currentNutrients.water(), updatedMap.get(Macronutrient.WATER.getInGerman())));
        currentNutrients = currentNutrients.withVitaminA(updateNutrientQuantity(currentNutrients.vitaminA(), updatedMap.get(Vitamin.A.getInGerman())));
        currentNutrients = currentNutrients.withVitaminB1(updateNutrientQuantity(currentNutrients.vitaminB1(), updatedMap.get(Vitamin.B1_THIAMIN.getInGerman())));
        currentNutrients = currentNutrients.withVitaminB2(updateNutrientQuantity(currentNutrients.vitaminB2(), updatedMap.get(Vitamin.B2_RIBOFLAVIN.getInGerman())));
        currentNutrients = currentNutrients.withVitaminB3(updateNutrientQuantity(currentNutrients.vitaminB3(), updatedMap.get(Vitamin.B3.getInGerman())));
        currentNutrients = currentNutrients.withVitaminB5(updateNutrientQuantity(currentNutrients.vitaminB5(), updatedMap.get(Vitamin.B5_PANTONTHETICACID.getInGerman())));
        currentNutrients = currentNutrients.withVitaminB6(updateNutrientQuantity(currentNutrients.vitaminB6(), updatedMap.get(Vitamin.B6_PYRIDOXIN.getInGerman())));
        currentNutrients = currentNutrients.withVitaminB7(updateNutrientQuantity(currentNutrients.vitaminB7(), updatedMap.get(Vitamin.B7_BIOTIN.getInGerman())));
        currentNutrients = currentNutrients.withVitaminB9(updateNutrientQuantity(currentNutrients.vitaminB9(), updatedMap.get(Vitamin.B9_FOLICACID_TOTAL.getInGerman())));
        currentNutrients = currentNutrients.withVitaminB12(updateNutrientQuantity(currentNutrients.vitaminB12(), updatedMap.get(Vitamin.B12_COBALAMIN.getInGerman())));
        currentNutrients = currentNutrients.withVitaminC(updateNutrientQuantity(currentNutrients.vitaminC(), updatedMap.get(Vitamin.C_ASCORBINACID.getInGerman())));
        currentNutrients = currentNutrients.withVitaminD(updateNutrientQuantity(currentNutrients.vitaminD(), updatedMap.get(Vitamin.D_CALCIFEROL.getInGerman())));
        currentNutrients = currentNutrients.withVitaminE(updateNutrientQuantity(currentNutrients.vitaminE(), updatedMap.get(Vitamin.E.getInGerman())));
        currentNutrients = currentNutrients.withVitaminK(updateNutrientQuantity(currentNutrients.vitaminK(), updatedMap.get(Vitamin.K.getInGerman())));
        currentNutrients = currentNutrients.withSalt(updateNutrientQuantity(currentNutrients.salt(), updatedMap.get(MajorElement.SALT.getInGerman())));
        currentNutrients = currentNutrients.withPral(updateNutrientQuantity(currentNutrients.pral(), updatedMap.get(MajorElement.PRAL.getInGerman())));
        currentNutrients = currentNutrients.withSodium(updateNutrientQuantity(currentNutrients.sodium(), updatedMap.get(MajorElement.SODIUM.getInGerman())));
        currentNutrients = currentNutrients.withPotassium(updateNutrientQuantity(currentNutrients.potassium(), updatedMap.get(MajorElement.POTASSIUM.getInGerman())));
        currentNutrients = currentNutrients.withCalcium(updateNutrientQuantity(currentNutrients.calcium(), updatedMap.get(MajorElement.CALCIUM.getInGerman())));
        currentNutrients = currentNutrients.withMagnesium(updateNutrientQuantity(currentNutrients.magnesium(), updatedMap.get(MajorElement.MAGNESIUM.getInGerman())));
        currentNutrients = currentNutrients.withPhosphorus(updateNutrientQuantity(currentNutrients.phosphorus(), updatedMap.get(MajorElement.PHOSPHORUS.getInGerman())));
        currentNutrients = currentNutrients.withSulfur(updateNutrientQuantity(currentNutrients.sulfur(), updatedMap.get(MajorElement.SULFUR.getInGerman())));
        currentNutrients = currentNutrients.withChloride(updateNutrientQuantity(currentNutrients.chloride(), updatedMap.get(MajorElement.CHLORIDE.getInGerman())));
        currentNutrients = currentNutrients.withIron(updateNutrientQuantity(currentNutrients.iron(), updatedMap.get(TraceElement.IRON.getInGerman())));
        currentNutrients = currentNutrients.withZinc(updateNutrientQuantity(currentNutrients.zinc(), updatedMap.get(TraceElement.ZINC.getInGerman())));
        currentNutrients = currentNutrients.withCopper(updateNutrientQuantity(currentNutrients.copper(), updatedMap.get(TraceElement.COPPER.getInGerman())));
        currentNutrients = currentNutrients.withManganese(updateNutrientQuantity(currentNutrients.manganese(), updatedMap.get(TraceElement.MANGANESE.getInGerman())));
        currentNutrients = currentNutrients.withFluoride(updateNutrientQuantity(currentNutrients.fluoride(), updatedMap.get(TraceElement.FLUORIDE.getInGerman())));
        currentNutrients = currentNutrients.withIoide(updateNutrientQuantity(currentNutrients.ioide(), updatedMap.get(TraceElement.IODIDE.getInGerman())));
        currentNutrients = currentNutrients.withIsoleucin(updateNutrientQuantity(currentNutrients.isoleucin(), updatedMap.get(AminoacidEssential.ISOLEUCIN.getInGerman())));
        currentNutrients = currentNutrients.withLeucin(updateNutrientQuantity(currentNutrients.leucin(), updatedMap.get(AminoacidEssential.LEUCIN.getInGerman())));
        currentNutrients = currentNutrients.withLysin(updateNutrientQuantity(currentNutrients.lysin(), updatedMap.get(AminoacidEssential.LYSIN.getInGerman())));
        currentNutrients = currentNutrients.withMethionin(updateNutrientQuantity(currentNutrients.methionin(), updatedMap.get(AminoacidEssential.METHIONIN.getInGerman())));
        currentNutrients = currentNutrients.withCystein(updateNutrientQuantity(currentNutrients.cystein(), updatedMap.get(AminoacidEssential.CYSTEIN.getInGerman())));
        currentNutrients = currentNutrients.withPhenylalanin(updateNutrientQuantity(currentNutrients.phenylalanin(), updatedMap.get(AminoacidEssential.PHENYLALANIN.getInGerman())));
        currentNutrients = currentNutrients.withTyrosin(updateNutrientQuantity(currentNutrients.tyrosin(), updatedMap.get(AminoacidEssential.TYROSIN.getInGerman())));
        currentNutrients = currentNutrients.withThreonin(updateNutrientQuantity(currentNutrients.threonin(), updatedMap.get(AminoacidEssential.THREONIN.getInGerman())));
        currentNutrients = currentNutrients.withTryptophan(updateNutrientQuantity(currentNutrients.tryptophan(), updatedMap.get(AminoacidEssential.TRYPTOPHAN.getInGerman())));
        currentNutrients = currentNutrients.withValin(updateNutrientQuantity(currentNutrients.valin(), updatedMap.get(AminoacidEssential.VALIN.getInGerman())));
        currentNutrients = currentNutrients.withArginin(updateNutrientQuantity(currentNutrients.arginin(), updatedMap.get(AminoacidEssential.ARGININ.getInGerman())));
        currentNutrients = currentNutrients.withHistidin(updateNutrientQuantity(currentNutrients.histidin(), updatedMap.get(AminoacidEssential.HISTIDIN.getInGerman())));
        currentNutrients = currentNutrients.withAlanin(updateNutrientQuantity(currentNutrients.alanin(), updatedMap.get(Aminoacid.ALANIN.getInGerman())));
        currentNutrients = currentNutrients.withAsparaginacid(updateNutrientQuantity(currentNutrients.asparaginacid(), updatedMap.get(Aminoacid.ASPARAGINACID.getInGerman())));
        currentNutrients = currentNutrients.withGlutaminacid(updateNutrientQuantity(currentNutrients.glutaminacid(), updatedMap.get(Aminoacid.GLUTAMINACID.getInGerman())));
        currentNutrients = currentNutrients.withGlycin(updateNutrientQuantity(currentNutrients.glycin(), updatedMap.get(Aminoacid.GLYCIN.getInGerman())));
        currentNutrients = currentNutrients.withProlin(updateNutrientQuantity(currentNutrients.prolin(), updatedMap.get(Aminoacid.PROLIN.getInGerman())));
        currentNutrients = currentNutrients.withSerin(updateNutrientQuantity(currentNutrients.serin(), updatedMap.get(Aminoacid.SERIN.getInGerman())));

        return currentNutrients;
    }

    private static Nutrient updateNutrientQuantity(Nutrient currentNutrient, Nutrient updatedNutrient) {
        if (updatedNutrient != null) {
            return currentNutrient.withQuantity(updatedNutrient.quantity());
        }
        return currentNutrient;
    }

    public Map<String, Nutrient> mapNutrientArrayToMap(Nutrient[] nutrientArray) {
        Map<String, Nutrient> nutrientMap = new HashMap<>();

        for (Nutrient nutrient : nutrientArray) {
            addNutrientToMap(nutrientMap, nutrient);
        }
        return nutrientMap;
    }

    private static void addNutrientToMap(Map<String, Nutrient> nutrientMap, Nutrient nutrient) {
        switch (nutrient.unit()) {
            case "kcal":
                nutrientMap.put("energyKcal", nutrient);
                break;
            case "kJ":
                nutrientMap.put("energyKJ", nutrient);
                break;
            default:
                nutrientMap.put(nutrient.name(), nutrient);
        }
    }
}