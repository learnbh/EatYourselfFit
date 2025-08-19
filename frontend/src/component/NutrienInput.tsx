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
                <div className="md:grid md:grid-cols-4 md:gap-0 flex flex-row flex-wrap  justify-items-start items-center gap-1">
                    <label className="grid min-w-30 justify-items-start ">{ props.name[0].toUpperCase() + props.name.slice(1) }:</label>
                    <input
                        className="max-w-32"
                        name={ props.name }
                        defaultValue={ props.value }
                        type="number"
                        min="0"
                        step="0.001"
                        onKeyDown={ handleKeyDownNumber }
                        onChange={ handleChange }
                    />
                    { props.children }
                </div>
            )}
            { props.name === "EnergieKJ" && (
                <div className="md:grid md:grid-cols-4 md:gap-0 flex flex-row flex-wrap  justify-items-start items-center gap-1">
                    <label className="flex justify-start min-w-31">Energie in KJ:</label>
                    <span className="border min-w-32 pt-2 pb-2 pl-2.5 md:ml-0 -ml-1 text-start">{props.value}</span>
                    { props.children }
                </div>
            )}
        </>
    )

}