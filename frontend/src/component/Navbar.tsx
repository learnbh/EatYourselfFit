import { useState } from "react";
import {useRecipeCart} from "../context/CardRecipeContext.tsx";
import {Link} from "react-router-dom";
import {GiCook, GiHotMeal} from "react-icons/gi";
import {GrSchedule, GrScheduleNew} from "react-icons/gr";

export default function Navbar(){
    const { recipeItems } = useRecipeCart();
    const [open, setOpen] = useState(false);

    return (
        <nav className="navbar">
            <button className="hamburger pr-2 pl-2" onClick={() => setOpen(!open)}>
                ☰
            </button>
            <ul className={`nav-links ${open ? 'open' : ''}`}>
                <li><Link to="/">Start</Link></li>
                <li><Link to="/recipe">Rezepte</Link></li>
                <li><Link to="/recipeplan">Rezepte erstellen ({recipeItems.length})</Link></li>
            </ul>
            <div className={`nav-links ${!open ? 'open' : ''}`}>
                <Link className="h-5" to="/recipe"><GiCook style = {{ width: "inherit", height:"inherit"}} /></Link>
                <Link className="h-5" to="/recipeplan"><GiHotMeal style = {{ width: "inherit", height:"inherit"}} /></Link>
                <Link className="h-5" to="/weekplan"><GrSchedule style = {{ width: "inherit", height:"inherit"}} /></Link>
                <Link className="h-6" to="/shoppinglist"><GrScheduleNew style = {{ width: "inherit", height:"inherit"}} /></Link>
            </div>
        </nav>
    );
}