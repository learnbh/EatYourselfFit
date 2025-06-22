import React, {useCallback} from "react";
import {Link, useNavigate} from "react-router-dom";

type Props = {
    class:string,
    to:string,
    id:string,
    children:React.ReactNode
}

export default function HideDetailIdLink(props:Readonly<Props>){
    const routeTo = useNavigate();

    const handleClick = useCallback((e: React.MouseEvent) => {
        e.preventDefault(); // Verhindert den Standard-Link-Klick
        sessionStorage.setItem('detailId', props.id);
        routeTo(props.to);
        //window.open(props.to, "_self"); // Öffnet den Link im neuen Tab
    }, [props.id, props.to, routeTo]);
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