package org.bea.backend.enums;

public enum Vitamin {
    A("Vitamin A", "µg"),
    B1_THIAMIN("Vitamin B1", "µg"),
    B2_RIBOFLAVIN("Vitamin B2", "µg"),
    B3("Vitamin B3", "µg"),
    B3_NIACIN("Vitamin B3 Niacin, Nicotinsäure", "µg"),
    B3_NIACINB3_NIACIN("Vitamin B3 Niacinäquivalent", "µg"),
    B5_PANTONTHETICACID("Vitamin B5", "µg"),
    B6_PYRIDOXIN("Vitamin B6", "µg"),
    B7_BIOTIN("Vitamin B7", "µg"),
    B9_FOLICACID_TOTAL("Vitamin B9", "µg"),
    B12_COBALAMIN("Vitamin B12", "µg"),
    C_ASCORBINACID("Vitamin C", "µg"),
    D_CALCIFEROL("Vitamin D", "µg"),
    E("Vitamin E", "µg"),
    K("Vitamin K", "µg");

    private final String inGerman;
    private final String unit;

    Vitamin(String inGerman, String unit){
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
