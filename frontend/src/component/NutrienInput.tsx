import {type ChangeEvent, type ReactNode} from "react";
import {handleKeyDownNumber} from "../helper.ts";

type Props = {
    name: string
    value:number
    onChange: (e:ChangeEvent<HTMLInputElement>)=>void
    children?:ReactNode
}
export default function NutrientInput(props:Readonly<Props>){

    const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
        props.onChange(e);
    }

    return(
        <>
            { props.name !== "EnergieKJ" && (
                <div className="grid grid-cols-5 justify-items-start items-center">
                    <label>{ props.name[0].toUpperCase() + props.name.slice(1) }:</label>
                    <input
                        className="max-w-24"
                        name={ props.name }
                        defaultValue={ props.value }
                        type="number"
                        min="0"
                        step="0.01"
                        onKeyDown={ handleKeyDownNumber }
                        onChange={ handleChange }
                    />
                    { props.children }
                </div>
            )}
            { props.name === "EnergieKJ" && (
                <div className="grid grid-cols-5 justify-items-start items-center">
                    <label>Energie in KJ:</label>
                    <span className="border min-w-24 pt-2 pb-2 pl-2.5 text-start">{props.value}</span>
                    { props.children }
                </div>
            )}
        </>
    )

}