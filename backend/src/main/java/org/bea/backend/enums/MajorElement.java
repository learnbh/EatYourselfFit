package org.bea.backend.enums;

public enum MajorElement {
    SALT("Salz", "mg"),
    PRAL("PRAL-Wert", "mEg"),
    SODIUM("Natrium", "mg"),
    POTASSIUM("Kalium", "mg"),
    CALCIUM("Kalzium", "mg"),
    MAGNESIUM("Magnesium", "mg"),
    PHOSPHORUS("Phosphor", "mg"),
    SULFUR("Schwefel", "mg"),
    CHLORIDE("Chlorid", "mg");

    private final String inGerman;
    private final String unit;

    MajorElement(String inGerman, String unit){
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
