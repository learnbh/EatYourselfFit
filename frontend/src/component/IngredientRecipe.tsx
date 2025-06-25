import RemoveButton from "./RemoveButton.tsx";
import React from "react";
import type {Ingredient} from "../types.ts";
import {handleKeyDownNumber} from "../helper.ts";

type Props = {
    ingredient:Ingredient,
    handleQuantity:(ingredient:Ingredient) =>  void,
    removeIngredientFromRecipe: (ingredient:Ingredient) => void
}

export default function IngredientRecipe(props:Readonly<Props>){

    function handleQuantity(e:React.FormEvent<HTMLInputElement>){
        e.preventDefault()
        props.ingredient.quantity = Number(e.currentTarget.value);
        props.handleQuantity(props.ingredient);
    }
    function removeIngredientFromRecipe(e: React.MouseEvent<HTMLButtonElement>){
        e.preventDefault()
        props.removeIngredientFromRecipe(props.ingredient);
    }
    return(
      <>
          <div className="grid  grid-cols-10  items-center gap-2 w-full border pl-2">
              <RemoveButton remove={removeIngredientFromRecipe} class="border-delete col-span-1 pl-2 pt-2 pb-2"/>
              <span className="text-left col-span-2">{props.ingredient.product}</span>
              <span className="text-left col-span-4 pl-3">{props.ingredient.variation}</span>
              <input className="col-span-2 border w-20"
                     defaultValue={props.ingredient.quantity}
                     onChange={handleQuantity}
                     type="number"
                     min="0"
                     pattern="\d*"
                     onKeyDown={handleKeyDownNumber}
              />
              <span className="text-left col-span-1 ml-2">{props.ingredient.unit}</span>
          </div>
      </>
    );
}