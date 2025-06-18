package org.bea.backend.enums;

public enum Aminoacid {

    ALANIN("Alanin", "mg"),
    ASPARAGINACID("Asparaginsäure","mg"),
    GLUTAMINACID("Glutaminsäure","mg"),
    GLYCIN("Glycin", "mg"),
    PROLIN("Prolin", "mg"),
    SERIN("Serin","mg");

    private final String inGerman;
    private final String unit;

    Aminoacid(String inGerman, String unit){
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
