import type {Ingredient} from "../types.ts";
import React from "react";
import HideDetailIdLink from "./HideDetailIdLink.tsx";

type Props = {
    ingredient: Ingredient
    children?: React.ReactNode
}
export default function IngredientSearchResultItem(props: Readonly<Props>){
    return(
        <>
            <div className="w-full md:w-1/3 lg:w-1/4 p-4 border">
                <HideDetailIdLink
                    class="col-span-2 text-left"
                    to={"/ingredient/"+props.ingredient.id}
                    id= {props.ingredient.id}
                >
                    <span>{props.ingredient.product}{props.ingredient.variation?", "+props.ingredient.variation:""}</span>
                    {props.children}
                </HideDetailIdLink>
            </div>
        </>
    )
}