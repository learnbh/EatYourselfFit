import { MdOutlinePlaylistRemove } from "react-icons/md";
import React from "react";

type Props = {
    remove: (e: React.MouseEvent<HTMLButtonElement>) => void
    class:string
}
export default function RemoveButton(props:Readonly<Props>){

    function removeIngredientFromRecipe(e: React.MouseEvent<HTMLButtonElement>){
       props.remove(e);
    }

    return(
        <>
            <button className ={props.class}
                    onClick={removeIngredientFromRecipe}
            >
                <MdOutlinePlaylistRemove/>
            </button>
        </>
    )
}