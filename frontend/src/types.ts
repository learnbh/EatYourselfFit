export type Ingredient = {
    id:string,
    product:string,
    variation:string,
    quantity:number,
    unit:string,
    prices:number,
    nutrientsId:string
}
export type IngredientDto = {
    product:string,
    variation:string,
    quantity:number,
    unit:string,
    prices:number,
    nutrientsId:string
}

export type Nutrient = {
    name:string,
    type:string,
    quantity:number,
    unit:string
}

export type NutrientsRecord = {
    id:string,
    // Macronutrients
    energyKcal:Nutrient,
    energyKJ:Nutrient,
    fat:Nutrient,
    protein:Nutrient,
    carbohydrate:Nutrient,
    fiber:Nutrient,
    water:Nutrient,
    // Vitamins
    vitaminA:Nutrient,
    vitaminB1:Nutrient,
    vitaminB2:Nutrient,
    vitaminB3:Nutrient,
    vitaminB5:Nutrient,
    vitaminB6:Nutrient,
    vitaminB7:Nutrient,
    vitaminB9:Nutrient,
    vitaminB12:Nutrient,
    vitaminC:Nutrient,
    vitaminD:Nutrient,
    vitaminE:Nutrient,
    vitaminK:Nutrient,
    // Major elements
    salt:Nutrient,
    pral:Nutrient,
    sodium:Nutrient
    potassium:Nutrient,
    calcium:Nutrient,
    magnesium:Nutrient,
    phosphorus:Nutrient,
    sulfur:Nutrient,
    chloride:Nutrient,
    // Trac elements
    iron:Nutrient,
    zinc:Nutrient,
    copper:Nutrient,
    manganese:Nutrient,
    fluoride:Nutrient,
    ioide:Nutrient,
    // Amino acid essential
    isoleucin:Nutrient,
    leucin:Nutrient,
    lysin:Nutrient,
    methionin:Nutrient,
    cystein:Nutrient,
    phenylalanin:Nutrient,
    tyrosin:Nutrient,
    threonin:Nutrient,
    tryptophan:Nutrient,
    valin:Nutrient,
    arginin:Nutrient,
    histidin:Nutrient,
    // Amino acid non essential
    alanin:Nutrient,
    asparaginacid:Nutrient,
    glutaminacid:Nutrient,
    glycin:Nutrient,
    prolin:Nutrient,
    serin:Nutrient
};

export type Nutrients = {
    [key: string]: Nutrient | string;
};

