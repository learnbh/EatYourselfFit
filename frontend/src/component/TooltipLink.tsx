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
    const [tooltipX, setTooltipX] = useState<number>(0);
    const [tooltipWidth, setTooltipWidth] = useState<number>(0);
    const tooltipRef = useRef<HTMLDivElement | null>(null);

    function handleHover(event:React.MouseEvent<HTMLAnchorElement, MouseEvent>){
        const icon = event.currentTarget as HTMLElement;
        setTooltipX(getTooltipX(icon.getBoundingClientRect()));
        setTooltipShown(true);
    }
    function handleLeave(){
        setTooltipWidth(0);
        setTooltipShown(false);
    }
    function getTooltipX(iconRect:DOMRect):number{
        return iconRect.left + iconRect.width/2;
    }
    useEffect(() => {
        if (tooltipRef.current && tooltipShown) {
            const tooltipRect = tooltipRef.current.getBoundingClientRect();
            setTooltipWidth(tooltipRect.width);
        }
    }, [tooltipShown]); // Abhängig vom Tooltip und seiner Anzeige

    return(
        <>
            {
                tooltipShown && (
                    <div
                        ref={tooltipRef}
                        className ={`absolute -top-0.5 opacity-60 duration-900 ease-out}`}
                        style = {{ left: tooltipX - tooltipWidth/2}}
                    >
                        {props.tooltipText}
                    </div>
                )
            }
            <Link
                className={` peer ${props.class} ${tooltipShown?" border-white border-t-2 pt-0.5" : ""}`}
                to={props.to}
                onMouseEnter={e => handleHover(e)}
                onMouseLeave={handleLeave}
            >
                {props.element}
            </Link>
        </>
    )
}