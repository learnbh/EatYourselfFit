import {handleKeyDownNumber} from "../helper.ts";
import type {ChangeEvent, ReactNode} from "react";

type Props = {
    label:string
    name: string
    value:number
    onChange: (e:ChangeEvent<HTMLInputElement>)=>void
    children?:ReactNode
}


export default function InputNumber(props:Readonly<Props>){
    function handleChange(e:ChangeEvent<HTMLInputElement>){
        props.onChange(e);
    }
    return(
        <>
            <div className="flex flex-col">
                <label>{props.label}:</label>
                <div className="flex flex-row items-center gap-2">
                    <input
                        className="max-w-24"
                        name={props.name}
                        defaultValue={props.value}
                        type={ props.label === "Menge" ? "string" : "number" }
                        min="0"
                        step="0.01"
                        pattern="\d*"
                        readOnly={ props.label === "Menge" }
                        onKeyDown={handleKeyDownNumber}
                        onChange={handleChange}
                    />
                    {props.children}
                </div>
            </div>
        </>
    )
}