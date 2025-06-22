import Navbar from "./Navbar.tsx";
import {GiCook} from "react-icons/gi";
import { GrScheduleNew } from "react-icons/gr";
import { GrSchedule } from "react-icons/gr";
import { AiOutlineSchedule  } from "react-icons/ai";

export default function Header(){
    return (
        <>
            <header className="header">
                <Navbar />
                <div className="hide">
                    <GiCook />
                    <GrScheduleNew />
                    <GrSchedule />
                    <AiOutlineSchedule />
                </div>
                <div className="logo">🍏 Ess dich fit</div>
            </header>
        </>
    );
}