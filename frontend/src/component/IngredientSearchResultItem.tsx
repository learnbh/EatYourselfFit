import type {Ingredient} from "../types.ts";
import {type ReactNode} from "react";
import HideDetailIdLink from "./HideDetailIdLink.tsx";

type Props = {
    ingredient: Ingredient
    children: ReactNode|null
}
export default function IngredientSearchResultItem(props: Readonly<Props>){
    return(
        <>

            <div className="w-full sm:w-1/3 lg:w-1/4 p-4  border">
                <HideDetailIdLink
                    class="col-span-2 text-left"
                    to={"/ingredient/"+props.ingredient.id}
                    id= {props.ingredient.id}
                >
                    <span>{props.ingredient.product}, {props.ingredient.variation}</span>
                </HideDetailIdLink>
            </div>
        </>
    )
}