import { useState } from "react";
import {useRecipeCart} from "../context/CardRecipeContext.tsx";
import {Link} from "react-router-dom";

export default function Navbar(){
    const { recipeItems } = useRecipeCart();
    const [open, setOpen] = useState(false);

    return (
        <nav className="navbar">
            <button className="hamburger" onClick={() => setOpen(!open)}>
                ☰
            </button>
            <ul className={`nav-links ${open ? 'open' : ''}`}>
                <li><Link to="/">Start</Link></li>
                <li><Link to="/recipe">Rezepte</Link></li>
                <li><Link to="/recipeplan">Rezepte erstellen ({recipeItems.length})</Link></li>
            </ul>
        </nav>
    );
}