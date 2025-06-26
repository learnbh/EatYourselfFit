import type {RecipeType} from "../types.ts";
import HideDetailIdLink from "./HideDetailIdLink.tsx";

type Props = {
    recipe:RecipeType
    img:string,
    imgAlt:string,
    imgWidth:string,
    imgHeight:string
}

export default function RecipeItem(props:Readonly<Props>){

    return (
        <>
            <div className="border grid grid-cols-1 items-end w-1/3 sm:w-1/4">
                <HideDetailIdLink
                    class=""
                    to={"/recipe/"+props.recipe.id}
                    id= {props.recipe.id}
                >
                    <span className="pl-2">{ props.recipe.title }</span>
                </HideDetailIdLink>
                <img className="tile" src={props.img} alt={props.imgAlt} width={props.imgWidth} height={props.imgHeight}/>
            </div>
        </>
    );
}
