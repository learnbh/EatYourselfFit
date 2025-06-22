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

export type IngredientProductRef = {
    focusField: (fieldName: string) => void;
};

export type InputRef = {
    focus: () => void;
};

export type RecipeContextType  = {
    recipeItems: Ingredient[]
    dishName:string
    changeDishName: (dishname:string) => void
    changeQuantity: (ingredient:Ingredient) => void
    addToRecipe: (ingredient:Ingredient) => void
    removeFromRecipe: (ingredient:Ingredient) => void
    clearRecipe: () => void;
}



