import {useState} from "react";
import {Link} from "react-router-dom";

import NavIcons from "./NavIcons.tsx";
import NavText from "./NavText.tsx";

export default function Header(){
    const [open, setOpen] = useState(false);
    return (
        <>
            <header className="header">
                <nav className="navbar">
                    <div className="flex items-center gap-1">
                        <button className="hamburger pr-2 pl-2 " onClick={() => setOpen(!open)} onMouseEnter={() => setOpen(!open)}>
                            ☰
                        </button>
                        <NavIcons />
                    </div>
                    <Link to="/">
                        <div className="logo">
                            <span>🍏 Ess</span> 
                            <span> dich fit</span>
                        </div>
                    </Link>
                </nav>
                <div className="pl-2 pr-2">
                    <NavText open={open} />
                </div>
            </header>
        </>
    );
}