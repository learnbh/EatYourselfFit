import React, {useCallback} from "react";
import {Link} from "react-router-dom";

type Props = {
    class:string,
    to:string,
    id:string,
    children:React.ReactNode
}

export default function HideDetailIdLink(props:Readonly<Props>){
    const handleClick = useCallback((e: React.MouseEvent) => {
        e.preventDefault(); // Verhindert den Standard-Link-Klick
        sessionStorage.setItem('detailId', props.id);
        window.open(props.to, '_blank'); // Öffnet den Link im neuen Tab
    }, [props.id, props.to]);
    return(
        <>
            <Link
                className={props.class}
                to={props.to}
                onClick={handleClick}
            >
                {props.children}
            </Link>
        </>
    )
}