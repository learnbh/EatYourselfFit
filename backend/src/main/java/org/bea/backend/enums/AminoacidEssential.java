package org.bea.backend.enums;

public enum AminoacidEssential {

    ISOLEUCIN("Isoleucin", "mg"),
    LEUCIN("Leucin", "mg"),
    LYSIN("Lysin", "mg"),
    METHIONIN("Methionin","mg"),
    CYSTEIN("Cystein", "mg"),
    PHENYLALANIN("Phenylalanin", "mg"),
    TYROSIN("Tyrosin","mg"),
    THREONIN("Threonin", "mg"),
    TRYPTOPHAN("Tryptophan", "mg"),
    VALIN("Valin", "mg"),
    ARGININ("Arginin","mg"),
    HISTIDIN("Histidin", "mg");

    private final String inGerman;
    private final String unit;

    AminoacidEssential(String inGerman, String unit){
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
