import React from "react";
import {Link} from "react-router-dom";

type Props = {
    class:string,
    to:string,
    id:string,
    children:React.ReactNode
}

export default function HideDetailIdLink(props:Readonly<Props>){
    function handleClick(){
        sessionStorage.setItem('detailId', props.id);
    }
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