import React, { type ChangeEvent, forwardRef, useImperativeHandle, useRef } from "react";
import type { InputRef } from "../types.ts";

type Props = {
    label:string
    name: string
    value:string
    onChange: ( e:ChangeEvent<HTMLInputElement> )=>void
}

function InputText (props:Readonly<Props>, ref: React.Ref<InputRef> ){
    const refInput = useRef<HTMLInputElement>( null );

    useImperativeHandle( ref, () => ( {
        focus: () => {
            refInput.current?.focus();
        }
    } ) );

    function handleKeyDownText(){
        console.log( "handleKeyDownText" )
    }
    function handleChange( e:ChangeEvent<HTMLInputElement> ){
        props.onChange( e );
    }
    return (
        <>
            <div className="flex flex-col">
                <label>{ props.label }:</label>
                <input
                    name = { props.name }
                    ref = { refInput }
                    value = { props.value }
                    onChange = { handleChange}
                    type = "text"
                    onKeyDown = { handleKeyDownText }
                />
            </div>
        </>
    );
}
export default forwardRef(InputText);