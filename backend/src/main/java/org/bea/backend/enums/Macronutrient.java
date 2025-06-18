package org.bea.backend.enums;

public enum Macronutrient {

    ENERGY_KCAL("Energie", "kcal"),
    ENERGY_KJ("Energie", "kJ"),
    FAT("Fett", "g"),
    PROTEIN("Eiweiß","g"),
    CARBOHYDRATE("Kohlenhydrate", "g"),
    FIBER("Ballaststoffe", "g"),
    WATER("Wasser","g");

    private final String inGerman;
    private final String unit;

    Macronutrient(String inGerman, String unit){
        this.inGerman = inGerman;
        this.unit = unit;
    }

    public String getInGerman() {
        return inGerman;
    }
    public String getUnit() {
        return unit;
    }
}
