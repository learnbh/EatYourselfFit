package org.bea.backend.enums;

public enum NutrientType {
    MACRONUTRIENT("Makronährstoff"),
    VITAMIN("Vitamine"),
    MAJORELEMENT("Mengenelemente"),
    TRACEELEMENT("Spurenlemente"),
    CARBOHYTRATE("Kohlenhydrate"),
    ESSENTIALAMINOACID("Aminosäure essentiell"),
    AMINOACID("Aminosäure"),
    FATTYACID("Fettsäure"),
    FIBER("Balaststoff"),
    OTHER("Sostiges");

    NutrientType(String typeInGerman) {}
}
