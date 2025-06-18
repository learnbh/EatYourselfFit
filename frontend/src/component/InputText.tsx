import type {ChangeEvent} from "react";

type Props = {
    label:string
    name: string
    value:string
    onChange: (e:ChangeEvent<HTMLInputElement>)=>void
}

export default function InputText(props:Readonly<Props>){
    function handleKeyDownText(){
        console.log("handleKeyDownText")
    }
    function handleChange(e:ChangeEvent<HTMLInputElement>){
        props.onChange(e);
    }
    return (
        <>
            <div className="flex flex-col">
                <label>{props.label}:</label>
                <input
                    name={props.name}
                    value={props.value}
                    onChange={handleChange}
                    type="text"
                    onKeyDown={handleKeyDownText}
                />
            </div>
        </>
    );
}