package org.bea.backend.FakeTestData;

import org.bea.backend.model.IngredientCreate;
import org.bea.backend.model.Nutrient;
import org.bea.backend.model.Nutrients;

import static org.bea.backend.enums.NutrientType.MACRONUTRIENT;

public class IngredientCreateFakeData {
    public static final IngredientCreate ingredientCreate = new IngredientCreate(
            "Rindfleisch",
            "Rinderhack 20% Fett",
            100.0,
            "g",
            7.99
    );
    public static final String WRONG_INGREDIENT_UNIT = """
            {
                "ingredientDto": {
                     "product": "Apfel",
                     "variation": "Getrocknet",
                     "quantity": 100,
                     "unit": "ml"
                },
                "nutrientsDto": {
                    "energyKcal": 52
                }
            }
        """;
    public static final String WRONG_INGREDIENT_QUANTITY = """
            {
                "ingredientDto": {
                     "product": "Apfel",
                     "variation": "Getrocknet",
                     "quantity": 500,
                     "unit": "ml"
                },
                "nutrientsDto": {
                    "energyKcal": 52
                }
            }
        """;
    public static final String WRONG_NUTRIENTS_ENERGY_KCAL = """
            {
                "ingredientDto": {
                     "product": "Apfel",
                     "variation": "Getrocknet",
                     "quantity": 100,
                     "unit": "g"
                },
                "nutrientsDto": {
                    "energyKcal": 52
                }
            }
        """;
    public static final Nutrients nutrients = new Nutrients(
            "nutrientId",
            new Nutrient("Energie",MACRONUTRIENT,250.0,"kcal"),
            new Nutrient("Energie",MACRONUTRIENT,1046.0,"kJ"),
            new Nutrient("Fett",MACRONUTRIENT,20.0,"g"),
            new Nutrient("Eiweiß",MACRONUTRIENT,26.0,"g"),
            new Nutrient("Kohlenhydrate",MACRONUTRIENT,0.0,"g"),
            new Nutrient("Ballaststoffe",MACRONUTRIENT,0.0,"g"),
            new Nutrient("Wasser",MACRONUTRIENT,54.0,"g"),
            new Nutrient("Vitamin A",MACRONUTRIENT,0.0,"µg"),
            new Nutrient("Vitamin B1",MACRONUTRIENT,0.07,"µg"),
            new Nutrient("Vitamin B2",MACRONUTRIENT,250.0,"µg"),
            new Nutrient("Vitamin B3",MACRONUTRIENT,250.0,"µg"),
            new Nutrient("Vitamin B5",MACRONUTRIENT,250.0,"µg"),
            new Nutrient("Vitamin B6",MACRONUTRIENT,250.0,"µg"),
            new Nutrient("Vitamin B7",MACRONUTRIENT,250.0,"µg"),
            new Nutrient("Vitamin B9",MACRONUTRIENT,250.0,"µg"),
            new Nutrient("Vitamin B12",MACRONUTRIENT,250.0,"µg"),
            new Nutrient("Vitamin C",MACRONUTRIENT,250.0,"µg"),
            new Nutrient("Vitamin D",MACRONUTRIENT,250.0,"µg"),
            new Nutrient("Vitamin E",MACRONUTRIENT,250.0,"µg"),
            new Nutrient("Vitamin K",MACRONUTRIENT,250.0,"µg"),
            new Nutrient("Salz",MACRONUTRIENT,250.0,"g"),
            new Nutrient("PRAL",MACRONUTRIENT,250.0,"mg"),
            new Nutrient("Natrium",MACRONUTRIENT,250.0,"g"),
            new Nutrient("Kalium",MACRONUTRIENT,250.0,"g"),
            new Nutrient("Kalzium",MACRONUTRIENT,250.0,"mg"),
            new Nutrient("Magnesium",MACRONUTRIENT,250.0,"mg"),
            new Nutrient("Phosphor",MACRONUTRIENT,250.0,"mg"),
            new Nutrient("Schwefel",MACRONUTRIENT,250.0,"g"),
            new Nutrient("Chlorid",MACRONUTRIENT,250.0,"g"),
            new Nutrient("Eisen",MACRONUTRIENT,250.0,"mg"),
            new Nutrient("Zink",MACRONUTRIENT,250.0,"mg"),
            new Nutrient("Kupfer",MACRONUTRIENT,250.0,"mg"),
            new Nutrient("Mangan",MACRONUTRIENT,250.0,"mg"),
            new Nutrient("Fluorid",MACRONUTRIENT,250.0,"mg"),
            new Nutrient("Jod",MACRONUTRIENT,250.0,"µg"),
            new Nutrient("Isoleucin",MACRONUTRIENT,250.0,"g"),
            new Nutrient("Leucin",MACRONUTRIENT,250.0,"g"),
            new Nutrient("Lysin",MACRONUTRIENT,250.0,"mg"),
            new Nutrient("Methionin",MACRONUTRIENT,250.0,"mg"),
            new Nutrient("Cystein",MACRONUTRIENT,250.0,"mg"),
            new Nutrient("Phenylalanin",MACRONUTRIENT,250.0,"mg"),
            new Nutrient("Tyrosin",MACRONUTRIENT,250.0,"mg"),
            new Nutrient("Threonin",MACRONUTRIENT,250.0,"mg"),
            new Nutrient("Tryptophan",MACRONUTRIENT,250.0,"mg"),
            new Nutrient("Valin",MACRONUTRIENT,250.0,"mg"),
            new Nutrient("Arginin",MACRONUTRIENT,250.0,"mg"),
            new Nutrient("Histidin",MACRONUTRIENT,250.0,"mg"),
            new Nutrient("Alanin",MACRONUTRIENT,250.0,"mg"),
            new Nutrient("Asparaginsäure",MACRONUTRIENT,250.0,"mg"),
            new Nutrient("Glutaminsäure",MACRONUTRIENT,250.0,"mg"),
            new Nutrient("Glycin",MACRONUTRIENT,250.0,"mg"),
            new Nutrient("Prolin",MACRONUTRIENT,250.0,"mg"),
            new Nutrient("Serin",MACRONUTRIENT,250.0,"mg")
    );
    public static final Nutrient[] nutrientsArray = {
            nutrients.energyKcal(),
            nutrients.energyKJ(),
            nutrients.fat(),
            nutrients.protein(),
            nutrients.carbohydrate(),
            nutrients.fiber(),
            nutrients.water(),
            nutrients.vitaminA(),
            nutrients.vitaminB1(),
            nutrients.vitaminB2(),
            nutrients.vitaminB3(),
            nutrients.vitaminB5(),
            nutrients.vitaminB6(),
            nutrients.vitaminB7(),
            nutrients.vitaminB9(),
            nutrients.vitaminB12(),
            nutrients.vitaminC(),
            nutrients.vitaminD(),
            nutrients.vitaminE(),
            nutrients.vitaminK(),
            nutrients.salt(),
            nutrients.pral(),
            nutrients.sodium(),
            nutrients.potassium(),
            nutrients.calcium(),
            nutrients.magnesium(),
            nutrients.phosphorus(),
            nutrients.sulfur(),
            nutrients.chloride(),
            nutrients.iron(),
            nutrients.zinc(),
            nutrients.copper(),
            nutrients.manganese(),
            nutrients.fluoride(),
            nutrients.ioide(),
            nutrients.isoleucin(),
            nutrients.leucin(),
            nutrients.lysin(),
            nutrients.methionin(),
            nutrients.cystein(),
            nutrients.phenylalanin(),
            nutrients.tyrosin(),
            nutrients.threonin(),
            nutrients.tryptophan(),
            nutrients.valin(),
            nutrients.arginin(),
            nutrients.histidin(),
            nutrients.alanin(),
            nutrients.asparaginacid(),
            nutrients.glutaminacid(),
            nutrients.glycin(),
            nutrients.prolin(),
            nutrients.serin()
    };

    public static final String CORRECT_RESPONSE = """
        {
            "ingredientDto": {
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
                        "quantity": 250,
                        "unit": "mg"
                },
                "vitaminB3": {
                "name": "Vitamin B3",
                        "type": "VITAMIN",
                        "quantity": 250,
                        "unit": "mg"
                },
                "vitaminB5": {
                "name": "Vitamin B5",
                        "type": "VITAMIN",
                        "quantity": 250,
                        "unit": "mg"
                },
                "vitaminB6": {
                "name": "Vitamin B6",
                        "type": "VITAMIN",
                        "quantity": 250,
                        "unit": "mg"
                },
                "vitaminB7": {
                "name": "Vitamin B7",
                        "type": "VITAMIN",
                        "quantity": 250,
                        "unit": "µg"
                },
                "vitaminB9": {
                "name": "Vitamin B9",
                        "type": "VITAMIN",
                        "quantity": 250,
                        "unit": "µg"
                },
                "vitaminB12": {
                "name": "Vitamin B12",
                        "type": "VITAMIN",
                        "quantity": 250,
                        "unit": "µg"
                },
                "vitaminC": {
                "name": "Vitamin C",
                        "type": "VITAMIN",
                        "quantity": 250,
                        "unit": "mg"
                },
                "vitaminD": {
                "name": "Vitamin D",
                        "type": "VITAMIN",
                        "quantity": 250,
                        "unit": "µg"
                },
                "vitaminE": {
                "name": "Vitamin E",
                        "type": "VITAMIN",
                        "quantity": 250,
                        "unit": "mg"
                },
                "vitaminK": {
                "name": "Vitamin K",
                        "type": "VITAMIN",
                        "quantity": 250,
                        "unit": "µg"
                },
                "salt": {
                "name": "Salz",
                        "type": "MAJORELEMENT",
                        "quantity": 250,
                        "unit": "g"
                },
                "pral": {
                "name": "PRAL",
                        "type": "MAJORELEMENT",
                        "quantity": 250,
                        "unit": "mg"
                },
                "sodium": {
                "name": "Natrium",
                        "type": "MAJORELEMENT",
                        "quantity": 250,
                        "unit": "g"
                },
                "potassium": {
                "name": "Kalium",
                        "type": "MAJORELEMENT",
                        "quantity": 250,
                        "unit": "mg"
                },
                "calcium": {
                "name": "Kalzium",
                        "type": "MAJORELEMENT",
                        "quantity": 250,
                        "unit": "mg"
                },
                "magnesium": {
                "name": "Magnesium",
                        "type": "MAJORELEMENT",
                        "quantity": 250,
                        "unit": "mg"
                },
                "phosphorus": {
                "name": "Phosphor",
                        "type": "MAJORELEMENT",
                        "quantity": 250,
                        "unit": "mg"
                },
                "sulfur": {
                "name": "Schwefel",
                        "type": "MAJORELEMENT",
                        "quantity": 250,
                        "unit": "mg"
                },
                "chloride": {
                "name": "Chlorid",
                        "type": "MAJORELEMENT",
                        "quantity": 250,
                        "unit": "g"
                },
                "iron": {
                "name": "Eisen",
                        "type": "TRACEELEMENT",
                        "quantity": 250,
                        "unit": "mg"
                },
                "zinc": {
                "name": "Zink",
                        "type": "TRACEELEMENT",
                        "quantity": 250,
                        "unit": "mg"
                },
                "copper": {
                "name": "Kupfer",
                        "type": "TRACEELEMENT",
                        "quantity": 250,
                        "unit": "mg"
                },
                "manganese": {
                "name": "Mangan",
                        "type": "TRACEELEMENT",
                        "quantity": 250,
                        "unit": "mg"
                },
                "fluoride": {
                "name": "Fluorid",
                        "type": "TRACEELEMENT",
                        "quantity": 250,
                        "unit": "mg"
                },
                "ioide": {
                "name": "Jod",
                        "type": "TRACEELEMENT",
                        "quantity": 250,
                        "unit": "µg"
                },
                "isoleucin": {
                "name": "Isoleucin",
                        "type": "ESSENTIALAMINOACID",
                        "quantity": 250,
                        "unit": "g"
                },
                "leucin": {
                "name": "Leucin",
                        "type": "ESSENTIALAMINOACID",
                        "quantity": 250,
                        "unit": "g"
                },
                "lysin": {
                "name": "Lysin",
                        "type": "ESSENTIALAMINOACID",
                        "quantity": 250,
                        "unit": "g"
                },
                "methionin": {
                "name": "Methionin",
                        "type": "ESSENTIALAMINOACID",
                        "quantity": 250,
                        "unit": "g"
                },
                "cystein": {
                "name": "Cystein",
                        "type": "ESSENTIALAMINOACID",
                        "quantity": 250,
                        "unit": "g"
                },
                "phenylalanin": {
                "name": "Phenylalanin",
                        "type": "ESSENTIALAMINOACID",
                        "quantity": 250,
                        "unit": "g"
                },
                "tyrosin": {
                "name": "Tyrosin",
                        "type": "ESSENTIALAMINOACID",
                        "quantity": 250,
                        "unit": "g"
                },
                "threonin": {
                "name": "Threonin",
                        "type": "ESSENTIALAMINOACID",
                        "quantity": 250,
                        "unit": "g"
                },
                "tryptophan": {
                "name": "Tryptophan",
                        "type": "ESSENTIALAMINOACID",
                        "quantity": 250,
                        "unit": "g"
                },
                "valin": {
                "name": "Valin",
                        "type": "ESSENTIALAMINOACID",
                        "quantity": 250,
                        "unit": "g"
                },
                "arginin": {
                "name": "Arginin",
                        "type": "ESSENTIALAMINOACID",
                        "quantity": 250,
                        "unit": "g"
                },
                "histidin": {
                "name": "Histidin",
                        "type": "ESSENTIALAMINOACID",
                        "quantity": 250,
                        "unit": "g"
                },
                "alanin": {
                "name": "Alanin",
                        "type": "AMINOACID",
                        "quantity": 250,
                        "unit": "g"
                },
                "asparaginacid": {
                "name": "Asparagin",
                        "type": "AMINOACID",
                        "quantity": 250,
                        "unit": "g"
                },
                "glutaminacid": {
                "name": "Glutamin",
                        "type": "AMINOACID",
                        "quantity": 250,
                        "unit": "g"
                },
                "glycin": {
                "name": "Glycin",
                        "type": "AMINOACID",
                        "quantity": 250,
                        "unit": "g"
                },
                "prolin": {
                "name": "Prolin",
                        "type": "AMINOACID",
                        "quantity": 250,
                        "unit": "g"
                },
                "serin": {
                "name": "Serin",
                        "type": "AMINOACID",
                        "quantity": 250,
                        "unit": "g"
                }
            }
        }
        """;

    public static final String RESPONSE_NUTRIENTS_WITH_INVALID_NUTRIENT = """
        {
            "ingredientDto": {
                "product": "Rindfleisch",
                        "variation": "Rinderhack 20% Fett",
                        "quantity": 100,
                        "unit": "g",
                        "price": 7.99
            },
            "nutrientsDto": {
                "energyKcal": {
                    "type": "MACRONUTRIENT",
                    "quantity": 250,
                    "unit": "kcal"
                },
                "energyKJ": "Energie",
                "fat": "Fett",
                "protein": "Eiweiß",
                "carbohydrate": "Kohlenhydrate",
                "fiber": "Ballaststoffe",
                "water": "Wasser",
                "vitaminA": "Vitamin A",
                "vitaminB1": "Vitamin B1",
                "vitaminB2": "Vitamin B2",
                "vitaminB3": "Vitamin B3",
                "vitaminB5": "Vitamin B5",
                "vitaminB6": "Vitamin B6",
                "vitaminB7": "Vitamin B7",
                "vitaminB9": "Vitamin B9",
                "vitaminB12": "Vitamin B12",
                "vitaminC": "Vitamin C",
                "vitaminD": "Vitamin D",
                "vitaminE": "Vitamin E",
                "vitaminK": "Vitamin K",
                "salt": "Salz",
                "pral": "PRAL",
                "sodium": "Natrium",
                "potassium": "Kalium",
                "calcium": "Kalzium",
                "magnesium": "Magnesium",
                "phosphorus": "Phosphor",
                "sulfur": "Schwefel",
                "chloride": "Chlorid",
                "iron": "Eisen",
                "zinc": "Zink",
                "copper": "Kupfer",
                "manganese": "Mangan",
                "fluoride": "Fluorid",
                "ioide": "Jod",
                "isoleucin": "Isoleucin",
                "leucin": "Leucin",
                "lysin": "Lysin",
                "methionin": "Methionin",
                "cystein": "Cystein",
                "phenylalanin": "Phenylalanin",
                "tyrosin": "Tyrosin",
                "threonin": "Threonin",
                "tryptophan": "Tryptophan",
                "valin": "Valin",
                "arginin": "Arginin",
                "histidin": "Histidin",
                "alanin": "Alanin",
                "asparaginacid": "Asparagin",
                "glutaminacid": "Glutamin",
                "glycin": "Glycin",
                "prolin": "Prolin",
                "serin": "Serin"
            }
        }
        """;

    public static final String RESPONSE_WITHOUT_NUTRIENTS_NODE = """
        {
            "ingredientDto": {
                "product": "Rindfleisch",
                        "variation": "Rinderhack 20% Fett",
                        "quantity": 100,
                        "unit": "g",
                        "price": 7.99
            }
        }
        """;
    public static final String RESPONSE_WITH_EMPTY_NUTRIENTS_NODE = """
            {
                "ingredientDto": {
                     "product": "Apfel",
                     "variation": "Getrocknet",
                     "quantity": 100,
                     "unit": "g"
                },
                "nutrientsDto": {}
            }
        """;
    public static final String RESPONSE_WITH_EMPTY_NUTRIENT_IN_NUTRIENTS_NODE = """
            {
                "ingredientDto": {
                     "product": "Apfel",
                     "variation": "Getrocknet",
                     "quantity": 100,
                     "unit": "g"
                },
                "nutrientsDto": {
                    "energyKcal": {
                    }
                }
            }
        """;
    public static final String RESPONSE_WITHOUT_NUTRIENT_ENERGY_KCAL = """
            {
                "ingredientDto": {
                     "product": "Apfel",
                     "variation": "Getrocknet",
                     "quantity": 100,
                     "unit": "g"
                },
                "nutrientsDto": {
                    "prolin": {
                    "name": "Prolin",
                            "type": "AMINOACID",
                            "quantity": 250,
                            "unit": "g"
                    },
                    "serin": {
                    "name": "Serin",
                            "type": "AMINOACID",
                            "quantity": 250,
                            "unit": "g"
                    }
                }
            }
        """;

    public static final String RESPONSE_WITHOUT_INGREDIENT_NODE = """
                        {
                            "product": "Rindfleisch",
                            "variation": "Rinderhack 20% Fett",
                            "quantity": 100,
                            "unit": "g",
                            "prices": 7.99
                        }
            """;
    public static final String RESPONSE_WITH_INVALID_INGREDIENT = """
        {
            "ingredientDto": {
                "product": "Rindfleisch",
                "variation": "Rinderhack 20% Fett",
                "quantity": 100,
                "unit": "g",
                "prices": "7.99"
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
                        "quantity": 250,
                        "unit": "mg"
                },
                "vitaminB3": {
                "name": "Vitamin B3",
                        "type": "VITAMIN",
                        "quantity": 250,
                        "unit": "mg"
                },
                "vitaminB5": {
                "name": "Vitamin B5",
                        "type": "VITAMIN",
                        "quantity": 250,
                        "unit": "mg"
                },
                "vitaminB6": {
                "name": "Vitamin B6",
                        "type": "VITAMIN",
                        "quantity": 250,
                        "unit": "mg"
                },
                "vitaminB7": {
                "name": "Vitamin B7",
                        "type": "VITAMIN",
                        "quantity": 250,
                        "unit": "µg"
                },
                "vitaminB9": {
                "name": "Vitamin B9",
                        "type": "VITAMIN",
                        "quantity": 250,
                        "unit": "µg"
                },
                "vitaminB12": {
                "name": "Vitamin B12",
                        "type": "VITAMIN",
                        "quantity": 250,
                        "unit": "µg"
                },
                "vitaminC": {
                "name": "Vitamin C",
                        "type": "VITAMIN",
                        "quantity": 250,
                        "unit": "mg"
                },
                "vitaminD": {
                "name": "Vitamin D",
                        "type": "VITAMIN",
                        "quantity": 250,
                        "unit": "µg"
                },
                "vitaminE": {
                "name": "Vitamin E",
                        "type": "VITAMIN",
                        "quantity": 250,
                        "unit": "mg"
                },
                "vitaminK": {
                "name": "Vitamin K",
                        "type": "VITAMIN",
                        "quantity": 250,
                        "unit": "µg"
                },
                "salt": {
                "name": "Salz",
                        "type": "MAJORELEMENT",
                        "quantity": 250,
                        "unit": "g"
                },
                "pral": {
                "name": "PRAL",
                        "type": "MAJORELEMENT",
                        "quantity": 250,
                        "unit": "mg"
                },
                "sodium": {
                "name": "Natrium",
                        "type": "MAJORELEMENT",
                        "quantity": 250,
                        "unit": "g"
                },
                "potassium": {
                "name": "Kalium",
                        "type": "MAJORELEMENT",
                        "quantity": 250,
                        "unit": "mg"
                },
                "calcium": {
                "name": "Kalzium",
                        "type": "MAJORELEMENT",
                        "quantity": 250,
                        "unit": "mg"
                },
                "magnesium": {
                "name": "Magnesium",
                        "type": "MAJORELEMENT",
                        "quantity": 250,
                        "unit": "mg"
                },
                "phosphorus": {
                "name": "Phosphor",
                        "type": "MAJORELEMENT",
                        "quantity": 250,
                        "unit": "mg"
                },
                "sulfur": {
                "name": "Schwefel",
                        "type": "MAJORELEMENT",
                        "quantity": 250,
                        "unit": "mg"
                },
                "chloride": {
                "name": "Chlorid",
                        "type": "MAJORELEMENT",
                        "quantity": 250,
                        "unit": "g"
                },
                "iron": {
                "name": "Eisen",
                        "type": "TRACEELEMENT",
                        "quantity": 250,
                        "unit": "mg"
                },
                "zinc": {
                "name": "Zink",
                        "type": "TRACEELEMENT",
                        "quantity": 250,
                        "unit": "mg"
                },
                "copper": {
                "name": "Kupfer",
                        "type": "TRACEELEMENT",
                        "quantity": 250,
                        "unit": "mg"
                },
                "manganese": {
                "name": "Mangan",
                        "type": "TRACEELEMENT",
                        "quantity": 250,
                        "unit": "mg"
                },
                "fluoride": {
                "name": "Fluorid",
                        "type": "TRACEELEMENT",
                        "quantity": 250,
                        "unit": "mg"
                },
                "ioide": {
                "name": "Jod",
                        "type": "TRACEELEMENT",
                        "quantity": 250,
                        "unit": "µg"
                },
                "isoleucin": {
                "name": "Isoleucin",
                        "type": "ESSENTIALAMINOACID",
                        "quantity": 250,
                        "unit": "g"
                },
                "leucin": {
                "name": "Leucin",
                        "type": "ESSENTIALAMINOACID",
                        "quantity": 250,
                        "unit": "g"
                },
                "lysin": {
                "name": "Lysin",
                        "type": "ESSENTIALAMINOACID",
                        "quantity": 250,
                        "unit": "g"
                },
                "methionin": {
                "name": "Methionin",
                        "type": "ESSENTIALAMINOACID",
                        "quantity": 250,
                        "unit": "g"
                },
                "cystein": {
                "name": "Cystein",
                        "type": "ESSENTIALAMINOACID",
                        "quantity": 250,
                        "unit": "g"
                },
                "phenylalanin": {
                "name": "Phenylalanin",
                        "type": "ESSENTIALAMINOACID",
                        "quantity": 250,
                        "unit": "g"
                },
                "tyrosin": {
                "name": "Tyrosin",
                        "type": "ESSENTIALAMINOACID",
                        "quantity": 250,
                        "unit": "g"
                },
                "threonin": {
                "name": "Threonin",
                        "type": "ESSENTIALAMINOACID",
                        "quantity": 250,
                        "unit": "g"
                },
                "tryptophan": {
                "name": "Tryptophan",
                        "type": "ESSENTIALAMINOACID",
                        "quantity": 250,
                        "unit": "g"
                },
                "valin": {
                "name": "Valin",
                        "type": "ESSENTIALAMINOACID",
                        "quantity": 250,
                        "unit": "g"
                },
                "arginin": {
                "name": "Arginin",
                        "type": "ESSENTIALAMINOACID",
                        "quantity": 250,
                        "unit": "g"
                },
                "histidin": {
                "name": "Histidin",
                        "type": "ESSENTIALAMINOACID",
                        "quantity": 250,
                        "unit": "g"
                },
                "alanin": {
                "name": "Alanin",
                        "type": "AMINOACID",
                        "quantity": 250,
                        "unit": "g"
                },
                "asparaginacid": {
                "name": "Asparagin",
                        "type": "AMINOACID",
                        "quantity": 250,
                        "unit": "g"
                },
                "glutaminacid": {
                "name": "Glutamin",
                        "type": "AMINOACID",
                        "quantity": 250,
                        "unit": "g"
                },
                "glycin": {
                "name": "Glycin",
                        "type": "AMINOACID",
                        "quantity": 250,
                        "unit": "g"
                },
                "prolin": {
                "name": "Prolin",
                        "type": "AMINOACID",
                        "quantity": 250,
                        "unit": "g"
                },
                "serin": {
                "name": "Serin",
                        "type": "AMINOACID",
                        "quantity": 250,
                        "unit": "g"
                }
            }
        }
    """;
}
