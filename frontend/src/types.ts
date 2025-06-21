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
export type IngredientCreate = {
    product:string,
    variation:string,
    quantity:number,
    unit:string,
    prices:number
}

export type IngredientProfile = {
    ingredientCreate:IngredientCreate,
    nutrientsArray:Nutrient[]
}

export type Nutrient = {
    name:string,
    type:string,
    quantity:number,
    unit:string
}

export type Nutrients = {
    [key: string]: Nutrient | string;
};

