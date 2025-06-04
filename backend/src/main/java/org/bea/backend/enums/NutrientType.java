package org.bea.backend.enums;

public enum NutrientType {
    MACRONUTRIENT("Makronährstoff"),
    VITAMIN("Vitamin"),
    MAJORELEMENT("Mengenelement"),
    TRACEELEMENT("Spurenlement"),
    CARBOHYTRATE("Kohlenhydrat"),
    ESSENTIALAMINOACID("Aminosäure essentiell"),
    AMINOACID("Aminosäure"),
    FATTYACID("Fettsäure"),
    FIBER("Balaststoff"),
    OTHER("Sostiges");

    NutrientType(String typeInGerman) {}
}
