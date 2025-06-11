import {useState} from "react";

export default function Navbar(){
    const [open, setOpen] = useState(false);

    return (
        <nav className="navbar">
            <button className="hamburger" onClick={() => setOpen(!open)}>
                ☰
            </button>
            <ul className={`nav-links ${open ? 'open' : ''}`}>
                <li><a href="/">Start</a></li>
                <li><a href="/recipe">Rezepte</a></li>
                <li><a href="/kontakt">Kontakt</a></li>
            </ul>
        </nav>
    );
}