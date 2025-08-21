import RemoveButton from "./RemoveButton.tsx";
import React from "react";
import type {Ingredient} from "../types.ts";
import {handleKeyDownNumber} from "../helper.ts";
import HideDetailIdLink from "./HideDetailIdLink.tsx";

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
          <div className="grid grid-cols-[auto_1fr_1fr_auto] gap-2 items-center  pl-1 pt-1 pb-1 pr-2 w-full border">
              <RemoveButton remove={removeIngredientFromRecipe} class="border-delete p-2"/>
              <HideDetailIdLink
                  class="text-left"
                  to={"/ingredient/"+props.ingredient.id}
                  id= {props.ingredient.id}
              >
                  <span className="text-left break-keep">{props.ingredient.product}</span>
              </HideDetailIdLink>
              <span className="text-left col-span-1 pl-3 break-words">{props.ingredient.variation}</span>
              <div className="col-span-1 gap-1 flex flex-row items-center">
                  <input className="w-20"
                     defaultValue={props.ingredient.quantity}
                     onChange={handleQuantity}
                     type="number"
                     min="0"
                     pattern="\d*"
                     onKeyDown={handleKeyDownNumber}
                 />
                  <span className="text-left">{props.ingredient.unit}</span>
              </div>
          </div>
      </>
    );
}