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
          <div className="flex flex-row border items-center pl-2 w-full">
              <RemoveButton remove={removeIngredientFromRecipe} class="border-delete col-span-1 p-2"/>
              <div className="flex flex-row items-center w-full gap-6 p-1">
                  <div className="grid  grid-cols-5  items-center gap-2 w-full pl-2">
                      <span className="text-left col-span-2 break-keep">{props.ingredient.product}</span>
                      <span className="text-left col-span-2 pl-3 break-words">{props.ingredient.variation}</span>
                      <input className="col-span-1 border w-20"
                             defaultValue={props.ingredient.quantity}
                             onChange={handleQuantity}
                             type="number"
                             min="0"
                             pattern="\d*"
                             onKeyDown={handleKeyDownNumber}
                      />
                  </div>
                  <span className="text-left">{props.ingredient.unit}</span>
              </div>
          </div>
      </>
    );
}