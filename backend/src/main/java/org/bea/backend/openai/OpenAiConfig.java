package org.bea.backend.openai;

public class OpenAiConfig {

    public static final String ingredientRequest = """
                    Du bist ein Ernährungsexperte. Gib ein vollständiges JSON-Objekt für ein Produkt zurück.
                    Das JSON-Objekt enthält:
                                                ingredientDto = {
                                                String product,
                                                String variation,
                                                Double quantity, //immer 100
                                                String unit, // immer g
                                                Double  prices
                                            },
                                            nutrientsDto = {
                                                // NutrientType = MACRONUTRIENT
                                                Nutrient energyKcal,
                                                Nutrient energyKJ,
                                                Nutrient fat,
                                                Nutrient protein,
                                                Nutrient carbohydrate,
                                                Nutrient fiber,
                                                Nutrient water,
                                                // NutrientType = VITAMIN
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
                                                // NutrientType = MAJORELEMENT
                                                Nutrient salt,
                                                Nutrient pral,
                                                Nutrient sodium,
                                                Nutrient potassium,
                                                Nutrient calcium,
                                                Nutrient magnesium,
                                                Nutrient phosphorus,
                                                Nutrient sulfur,
                                                Nutrient chloride,
                                                // NutrientType = TRACEELEMENT
                                                Nutrient iron,
                                                Nutrient zinc,
                                                Nutrient copper,
                                                Nutrient manganese,
                                                Nutrient fluoride,
                                                Nutrient ioide,
                                                // NutrientType = ESSENTIALAMINOACID
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
                                                // NutrientType = AMINOACID
                                                Nutrient alanin,
                                                Nutrient asparaginacid,
                                                Nutrient glutaminacid,
                                                Nutrient glycin,
                                                Nutrient prolin,
                                                Nutrient serin
                                            }
                    Regeln:
                    - nutrient: { "name": string, "type": string, "quantity": number, "unit": string }
                    - Alle Felder sind Pflichtfelder. Verwende Defaultwerte bei Unbekanntem: String: "", Number: 0.
                    - Alle Werte beziehen sich auf 100g des Produkts. Einheiten z.B. "kcal", "g", "mg", "kJ".
                    - ingredientDto.prices: Wähle hier einen handelsüblichen Durchschnittspreis in Euro (z.B. Endverbraucherpreis für Supermarkt Denns's Biomarkt) als realistischen Schätzwert in Euro. Verwende keine Null oder Platzhalter.
                    - prüfe, ob nur die Felder ingredientDto.produkt und ingredientDto.variation auf Rechtschreibung und korrigiere, achte dabei auf Groß- und Kleinschreibung
                    - Wenn eine Variation angegeben wird, aber unvollständig oder nicht standardisiert ist (z.B. "1,5" statt "1,5% Fett"), dann vervollständige sie zu einer sinnvollen, handelsüblichen Bezeichnung. Verwende dabei die korrekte Formatierung und Beschreibung.
                    - Wenn ein Produktname angegeben wird, der in Wirklichkeit eine spezifische Variation eines übergeordneten Produkts ist (z.B. "Edamer" → "Käse"), dann korrigiere dies automatisch. Verwende als "product" die korrekte Oberkategorie (z.B. "Käse") und trage die ursprünglich angegebene Produktspezifikation ins Feld "variation" ein, z.B.:
                     "Edamer" → product: "Käse", variation: "Edamer"
                     "Mozzarella" → product: "Käse", variation: "Mozzarella"
                     "Vollkornbrot" → product: "Brot", variation: "Vollkornbrot"
                      Diese Regel gilt auch, wenn keine Variation separat angegeben wurde.
                    - Bitte antworte ausschließlich im JSON-Format und verzichte auf zusätzliche Formatierungen oder Backticks
                """;

       public static final String ingredientResponseTest = """
        {"ingredientDto": {
                "product": "Rindfleisch",
                        "variation": "Rinderhack 20% Fett",
                        "quantity": 100,
                        "unit": "g",
                        "prices": 7.99
            },
            "nutrientsDto": {
                "energyKcal": {
                "name": "Energie",
                        "type": "MACRONUTRIENT",
                        "quantity": 250,
                        "unit": "kcal"
                },
                "energyKJ": {
                "name": "Energie",
                        "type": "MACRONUTRIENT",
                        "quantity": 1046,
                        "unit": "kJ"
                },
                "fat": {
                "name": "Fett",
                        "type": "MACRONUTRIENT",
                        "quantity": 20,
                        "unit": "g"
                },
                "protein": {
                "name": "Eiweiß",
                        "type": "MACRONUTRIENT",
                        "quantity": 26,
                        "unit": "g"
                },
                "carbohydrate": {
                "name": "Kohlenhydrate",
                        "type": "MACRONUTRIENT",
                        "quantity": 0,
                        "unit": "g"
                },
                "fiber": {
                "name": "Ballaststoffe",
                        "type": "MACRONUTRIENT",
                        "quantity": 0,
                        "unit": "g"
                },
                "water": {
                "name": "Wasser",
                        "type": "MACRONUTRIENT",
                        "quantity": 54,
                        "unit": "g"
                },
                "vitaminA": {
                "name": "Vitamin A",
                        "type": "VITAMIN",
                        "quantity": 0,
                        "unit": "µg"
                },
                "vitaminB1": {
                "name": "Vitamin B1",
                        "type": "VITAMIN",
                        "quantity": 0.07,
                        "unit": "mg"
                },
                "vitaminB2": {
                "name": "Vitamin B2",
                        "type": "VITAMIN",
                        "quantity": 0.2,
                        "unit": "mg"
                },
                "vitaminB3": {
                "name": "Vitamin B3",
                        "type": "VITAMIN",
                        "quantity": 5.5,
                        "unit": "mg"
                },
                "vitaminB5": {
                "name": "Vitamin B5",
                        "type": "VITAMIN",
                        "quantity": 0.7,
                        "unit": "mg"
                },
                "vitaminB6": {
                "name": "Vitamin B6",
                        "type": "VITAMIN",
                        "quantity": 0.5,
                        "unit": "mg"
                },
                "vitaminB7": {
                "name": "Vitamin B7",
                        "type": "VITAMIN",
                        "quantity": 0,
                        "unit": "µg"
                },
                "vitaminB9": {
                "name": "Vitamin B9",
                        "type": "VITAMIN",
                        "quantity": 6,
                        "unit": "µg"
                },
                "vitaminB12": {
                "name": "Vitamin B12",
                        "type": "VITAMIN",
                        "quantity": 2.5,
                        "unit": "µg"
                },
                "vitaminC": {
                "name": "Vitamin C",
                        "type": "VITAMIN",
                        "quantity": 0,
                        "unit": "mg"
                },
                "vitaminD": {
                "name": "Vitamin D",
                        "type": "VITAMIN",
                        "quantity": 0,
                        "unit": "µg"
                },
                "vitaminE": {
                "name": "Vitamin E",
                        "type": "VITAMIN",
                        "quantity": 0.2,
                        "unit": "mg"
                },
                "vitaminK": {
                "name": "Vitamin K",
                        "type": "VITAMIN",
                        "quantity": 0,
                        "unit": "µg"
                },
                "salt": {
                "name": "Salz",
                        "type": "MAJORELEMENT",
                        "quantity": 0.1,
                        "unit": "g"
                },
                "pral": {
                "name": "PRAL",
                        "type": "MAJORELEMENT",
                        "quantity": 0,
                        "unit": "mg"
                },
                "sodium": {
                "name": "Natrium",
                        "type": "MAJORELEMENT",
                        "quantity": 0.04,
                        "unit": "g"
                },
                "potassium": {
                "name": "Kalium",
                        "type": "MAJORELEMENT",
                        "quantity": 330,
                        "unit": "mg"
                },
                "calcium": {
                "name": "Kalzium",
                        "type": "MAJORELEMENT",
                        "quantity": 10,
                        "unit": "mg"
                },
                "magnesium": {
                "name": "Magnesium",
                        "type": "MAJORELEMENT",
                        "quantity": 20,
                        "unit": "mg"
                },
                "phosphorus": {
                "name": "Phosphor",
                        "type": "MAJORELEMENT",
                        "quantity": 180,
                        "unit": "mg"
                },
                "sulfur": {
                "name": "Schwefel",
                        "type": "MAJORELEMENT",
                        "quantity": 0,
                        "unit": "mg"
                },
                "chloride": {
                "name": "Chlorid",
                        "type": "MAJORELEMENT",
                        "quantity": 0.1,
                        "unit": "g"
                },
                "iron": {
                "name": "Eisen",
                        "type": "TRACEELEMENT",
                        "quantity": 2.5,
                        "unit": "mg"
                },
                "zinc": {
                "name": "Zink",
                        "type": "TRACEELEMENT",
                        "quantity": 5,
                        "unit": "mg"
                },
                "copper": {
                "name": "Kupfer",
                        "type": "TRACEELEMENT",
                        "quantity": 0.1,
                        "unit": "mg"
                },
                "manganese": {
                "name": "Mangan",
                        "type": "TRACEELEMENT",
                        "quantity": 0.01,
                        "unit": "mg"
                },
                "fluoride": {
                "name": "Fluorid",
                        "type": "TRACEELEMENT",
                        "quantity": 0,
                        "unit": "mg"
                },
                "ioide": {
                "name": "Jod",
                        "type": "TRACEELEMENT",
                        "quantity": 0,
                        "unit": "µg"
                },
                "isoleucin": {
                "name": "Isoleucin",
                        "type": "ESSENTIALAMINOACID",
                        "quantity": 1.5,
                        "unit": "g"
                },
                "leucin": {
                "name": "Leucin",
                        "type": "ESSENTIALAMINOACID",
                        "quantity": 2.5,
                        "unit": "g"
                },
                "lysin": {
                "name": "Lysin",
                        "type": "ESSENTIALAMINOACID",
                        "quantity": 2.2,
                        "unit": "g"
                },
                "methionin": {
                "name": "Methionin",
                        "type": "ESSENTIALAMINOACID",
                        "quantity": 0.9,
                        "unit": "g"
                },
                "cystein": {
                "name": "Cystein",
                        "type": "ESSENTIALAMINOACID",
                        "quantity": 0.4,
                        "unit": "g"
                },
                "phenylalanin": {
                "name": "Phenylalanin",
                        "type": "ESSENTIALAMINOACID",
                        "quantity": 1.0,
                        "unit": "g"
                },
                "tyrosin": {
                "name": "Tyrosin",
                        "type": "ESSENTIALAMINOACID",
                        "quantity": 0.5,
                        "unit": "g"
                },
                "threonin": {
                "name": "Threonin",
                        "type": "ESSENTIALAMINOACID",
                        "quantity": 1.0,
                        "unit": "g"
                },
                "tryptophan": {
                "name": "Tryptophan",
                        "type": "ESSENTIALAMINOACID",
                        "quantity": 0.3,
                        "unit": "g"
                },
                "valin": {
                "name": "Valin",
                        "type": "ESSENTIALAMINOACID",
                        "quantity": 1.5,
                        "unit": "g"
                },
                "arginin": {
                "name": "Arginin",
                        "type": "ESSENTIALAMINOACID",
                        "quantity": 1.0,
                        "unit": "g"
                },
                "histidin": {
                "name": "Histidin",
                        "type": "ESSENTIALAMINOACID",
                        "quantity": 0.5,
                        "unit": "g"
                },
                "alanin": {
                "name": "Alanin",
                        "type": "AMINOACID",
                        "quantity": 1.5,
                        "unit": "g"
                },
                "asparaginacid": {
                "name": "Asparagin",
                        "type": "AMINOACID",
                        "quantity": 1.0,
                        "unit": "g"
                },
                "glutaminacid": {
                "name": "Glutamin",
                        "type": "AMINOACID",
                        "quantity": 3.0,
                        "unit": "g"
                },
                "glycin": {
                "name": "Glycin",
                        "type": "AMINOACID",
                        "quantity": 1.0,
                        "unit": "g"
                },
                "prolin": {
                "name": "Prolin",
                        "type": "AMINOACID",
                        "quantity": 1.0,
                        "unit": "g"
                },
                "serin": {
                "name": "Serin",
                        "type": "AMINOACID",
                        "quantity": 0.8,
                        "unit": "g"
                }
            }
        }
        """;

       public static final String responseWithoutIngredientNode = """
                        { "product": "Rindfleisch",
                        "variation": "Rinderhack 20% Fett",
                        "quantity": 100,
                        "unit": "g",
                        "prices": 7.99}
            """;
}
