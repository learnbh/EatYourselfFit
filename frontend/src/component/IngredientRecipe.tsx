import RemoveButton from "./RemoveButton.tsx";
import React from "react";
import type {Ingredient} from "../types.ts";

type Props = {
    ingredient:Ingredient,
    removeIngredientFromRecipe: (ingredient:Ingredient) => void
}

export default function IngredientRecipe(props:Readonly<Props>){

    function removeIngredientFromRecipe(e: React.MouseEvent<HTMLButtonElement>){
        e.preventDefault()
        props.removeIngredientFromRecipe(props.ingredient);
    }
    return(
      <>
          <div className="flex flex-row gap-2">
              <RemoveButton remove={removeIngredientFromRecipe} class=" col-span-2 border-delete p-1"/>
              <span>{props.ingredient.product}</span>
              <span>{props.ingredient.variation}</span>
              <span>{props.ingredient.quantity}</span>
              <span>{props.ingredient.unit}</span>
          </div>
      </>
    );
}