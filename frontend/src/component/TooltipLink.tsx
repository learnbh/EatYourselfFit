import React, {type ReactNode, useEffect, useRef, useState} from "react";
import {Link} from "react-router-dom";

type Props = {
    tooltipText: string
    to: string
    element: ReactNode
    class: string
}
export default function TooltipLink(props:Readonly<Props>){
    const [tooltipShown, setTooltipShown] = useState<boolean>(false);
    const [iconMiddleX, setIconMiddleX] = useState<number>(0);
    const [tooltipWidth, setTooltipWidth] = useState<number>(30);
    const tooltipRef = useRef<HTMLDivElement | null>(null);

    function handleHover(event:React.MouseEvent<HTMLAnchorElement, MouseEvent>){
        const icon = event.currentTarget as HTMLElement;
        const parentRect = icon.offsetParent?.getBoundingClientRect()
        if (parentRect){
            setIconMiddleX(getIconMiddleX(icon.getBoundingClientRect())-parentRect.left);
        } else {
            setIconMiddleX(getIconMiddleX(icon.getBoundingClientRect()));
        }
        setTooltipShown(true);
    }
    console.log("icon.iconMiddleX: "+iconMiddleX);
    function handleLeave(){
        setTooltipWidth(0);
        setTooltipShown(false);
    }
    function getIconMiddleX(iconRect:DOMRect):number{
        return iconRect.left + iconRect.width/2;
    }
    useEffect(() => {
        if (tooltipRef.current && tooltipShown) {
            const tooltipRect = tooltipRef.current.getBoundingClientRect();
            setTooltipWidth(tooltipRect.width);
        }
    }, [tooltipShown]);

    return(
        <>
            {
                tooltipShown && (
                    <div
                        ref={tooltipRef}
                        className ="absolute -top-0.5 opacity-60"
                        style = {{
                            left: `${iconMiddleX - tooltipWidth / 2}px`}}
                    >
                        {props.tooltipText}
                    </div>
                )
            }
            <Link
                className={`${props.class} ${tooltipShown?" border-white border-t-2 pt-0.5" : ""}`}
                to={props.to}
                onMouseEnter={e => handleHover(e)}
                onMouseLeave={handleLeave}
            >
                {props.element}
            </Link>
        </>
    )
}