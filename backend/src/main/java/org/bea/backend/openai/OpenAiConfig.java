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
                    - prüfe und korrigiere die Felder ingredientDto.produkt und ingredientDto.variation auf Rechtschreibung, achte dabei auf Groß- und Kleinschreibung
                    - vervollständige eine unvollständige oder nicht standardisierte Variation (z.B. "1,5" statt "1,5% Fett") zu einer sinnvollen, handelsüblichen Bezeichnung. Verwende dabei die korrekte Formatierung und Beschreibung.
                    - Wenn ein Produktname angegeben wird, dass eigentlich eine spezifische Variation eines übergeordneten Produkts ist (z.B. "Edamer" → "Käse"), dann korrigiere dies automatisch. Verwende als "product" die korrekte Oberkategorie (z.B. "Käse") und trage die ursprünglich angegebene Produktspezifikation ins Feld "variation" ein, z.B.:
                     "Edamer" → product: "Käse", variation: "Edamer"
                     "Vollkornbrot" → product: "Brot", variation: "Vollkornbrot"
                      Diese Regel gilt auch, wenn keine Variation separat angegeben wurde.
                    - Bitte antworte ausschließlich im JSON-Format und verzichte auf zusätzliche Formatierungen oder Backticks
                    - Wenn Du keine sinnvollen Daten findest/die Anfrage keinen Sinn macht (z.B dhfgu, oder essen) gib statt JSON den String "Es konnten keine Nährstoffe gefunden werden. Änderne die Anfrage und versuche es erneut."
                    - Gib den String auch zurück, wenn du als Variante: Standardvariante/ als Produkt: Lebensmittel o.ä. angeben möchtest.
                """;
}
