package org.bea.backend.enums;

public enum TraceElement {
    IRON("Eisen", "μg"),
    ZINC("Zink", "μg"),
    COPPER("Kupfer", "μg"),
    MANGANESE("Mangan", "μg"),
    FLUORIDE("Fluorid", "μg"),
    IODIDE("Jod", "μg");

    private final String inGerman;
    private final String unit;

    TraceElement(String inGerman, String unit){
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
