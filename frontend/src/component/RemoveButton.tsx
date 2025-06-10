import { MdOutlinePlaylistRemove } from "react-icons/md";
import React from "react";

type Props = {
    remove: (e: React.MouseEvent<HTMLButtonElement>) => void
}
export default function RemoveButton(props:Readonly<Props>){

    function removeIngredientFromRecipe(e: React.MouseEvent<HTMLButtonElement>){
       props.remove(e);
    }

    return(
        <>
            <button className=" col-span-2 border-delete addbtn w-12"
                    onClick={removeIngredientFromRecipe}
            >
                <MdOutlinePlaylistRemove/>
            </button>
        </>
    )
}