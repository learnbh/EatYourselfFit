import {Link} from "react-router-dom";
import {type ReactNode} from "react";

type Props = {
    to:string,
    title:string,
    element:ReactNode,
    img:string,
    imgAlt:string,
    imgWidth:string,
    imgHeight:string
}

export default function Tile(props:Readonly<Props>){
    return(
        <>
            <div className="border pt-2">
                <div className="tileHeader h-5">
                    {props.element}
                    <span className="pt-0.5">{props.title}</span>
                </div>
                <Link to={props.to}>
                    <img className="tile" src={props.img} alt={props.imgAlt} width={props.imgWidth} height={props.imgHeight}/>
                </Link>
            </div>
        </>
    )
}