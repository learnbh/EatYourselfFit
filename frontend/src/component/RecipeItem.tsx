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
            <div className="border flex flex-col justify-between w-1/3 sm:w-1/4 p-1">
                <HideDetailIdLink
                    class=""
                    to={"/recipe/"+props.recipe.id}
                    id= {props.recipe.id}
                >
                    <span className="">{ props.recipe.title }</span>
                </HideDetailIdLink>
                <img className="" src={props.img} alt={props.imgAlt} width={props.imgWidth} height={props.imgHeight}/>
            </div>
        </>
    );
}
