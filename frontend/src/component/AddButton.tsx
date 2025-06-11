import {RiPlayListAddLine} from "react-icons/ri";
import React from "react";

type Props = {
    add:(e: React.MouseEvent<HTMLButtonElement>) =>  void
}

export default function AddButton(props:Readonly<Props>){

    function addIngredientToRecipe(e: React.MouseEvent<HTMLButtonElement>){
        e.preventDefault()
        props.add(e);
    }

    return(
        <>
            <button className=" col-span-2 border addbtn w-12"
                    onClick={addIngredientToRecipe}
            >
                <RiPlayListAddLine/>
            </button>
        </>
    )
}