import type {Ingredient} from "../types.ts";
import {handleKeyDownNumber} from "../helper.ts";
import HideDetailIdLink from "./HideDetailIdLink.tsx";
import React, {useState} from "react";
import AddButton from "./AddButton.tsx";

type Props = {
    ingredient:Ingredient,
    addIngredientToRecipe:(ingredient:Ingredient) =>  void
    removeIngredientFromRecipe: (ingredient:Ingredient) => void
    handleQuantity:(ingredient:Ingredient) =>  void
}

export default function IngredientSearch(props:Readonly<Props>) {
    const [isIngredientAdded, setIsIngredientAdded] = useState<boolean>(false)

    function addIngredientToRecipe(e: React.MouseEvent<HTMLButtonElement>){
        e.preventDefault()
        props.addIngredientToRecipe(props.ingredient);
        setIsIngredientAdded(!isIngredientAdded);
    }

    function handleQuantity(e:React.FormEvent<HTMLInputElement>){
        e.preventDefault()
        props.ingredient.quantity = Number(e.currentTarget.value);
        props.handleQuantity(props.ingredient);
    }

    return (
        <>
            <div className="grid  grid-cols-10  items-center gap-2 w-full border pl-2">
                <HideDetailIdLink
                    class="col-span-2 text-left"
                    to={"/ingredient/"+props.ingredient.id}
                    id= {props.ingredient.id}
                >
                    <span>{props.ingredient.product}</span>
                </HideDetailIdLink>
                <span className="text-left col-span-3">{props.ingredient.variation}</span>
                <input className="col-span-2 border w-20"
                       defaultValue={props.ingredient.quantity}
                       onChange={handleQuantity}
                       type="number"
                       min="0"
                       pattern="\d*"
                       onKeyDown={handleKeyDownNumber}
                />
                <span className="col-span-1 ">{props.ingredient.unit}</span>
                <AddButton add={addIngredientToRecipe}/>
            </div>
        </>
    );
}