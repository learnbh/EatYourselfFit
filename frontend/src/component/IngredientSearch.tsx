import type {Ingredient} from "../types.ts";
import React, {useState} from "react";
import RemoveButton from "./RemoveButton.tsx";
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

    function removeIngredientFromRecipe(e: React.MouseEvent<HTMLButtonElement>){
        e.preventDefault()
        props.removeIngredientFromRecipe(props.ingredient);
        setIsIngredientAdded(!isIngredientAdded);
    }

    function handleQuantity(e:React.FormEvent<HTMLInputElement>){
        e.preventDefault()
        const input = e.currentTarget.value;
        if (input === '' || /^\d*\.?\d*$/.test(input)) {
            props.ingredient.quantity = Number(input);
            props.handleQuantity(props.ingredient);
        }
    }

    function handleKeyDown (e: React.KeyboardEvent<HTMLInputElement>) {
        if (!/\d/.test(e.key) && e.key !== 'Backspace' && e.key !== 'Delete') {
            e.preventDefault();
        }
    }
    return (
        <>
            <div className="grid  grid-cols-10  items-center gap-4 w-full border pl-2">
                <span className="col-span-2 text-left">{props.ingredient.product}</span>
                <span className="text-left col-span-3">{props.ingredient.variation}</span>
                <input className="col-span-2 border w-20"
                       defaultValue={props.ingredient.quantity}
                       onChange={handleQuantity}
                       type="number"
                       min="0"
                       pattern="\d*"
                       onKeyDown={handleKeyDown}
                />
                <span className="col-span-1 ">{props.ingredient.unit}</span>
                { isIngredientAdded
                    ? <RemoveButton remove={removeIngredientFromRecipe}/>
                    : <AddButton add={addIngredientToRecipe}/>
                }
            </div>
        </>
    );
}