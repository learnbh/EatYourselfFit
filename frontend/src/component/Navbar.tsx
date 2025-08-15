import { useState } from "react";
import {useRecipeCart} from "../context/CardRecipeContext.tsx";
import {Link} from "react-router-dom";
import {GiCook, GiHotMeal} from "react-icons/gi";
import {GrSchedule, GrScheduleNew} from "react-icons/gr";
import { GiCorn } from "react-icons/gi";
import TooltipLink from "./TooltipLink.tsx";
import {MainNavNames} from "../enums.ts";

export default function Navbar(){
    const { recipeItems } = useRecipeCart();
    const [open, setOpen] = useState(false);



    return (
        <nav className="navbar">
            <button className="hamburger pr-2 pl-2 " onClick={() => setOpen(!open)}>
                ☰
            </button>
            <ul className={`nav-links ${open ? 'open' : ''}`}>
                <li><Link to="/">Start</Link></li>
                <li><Link to="/recipe">Rezepte</Link></li>
                <li><Link to="/recipeplan">Rezepte erstellen ({recipeItems.length})</Link></li>
                <li><Link to="/job/migrate/slugs">Jobs</Link></li>
            </ul>
            <div className={`nav-links ${!open ? 'open' : ''} `}>
                <TooltipLink
                    tooltipText={ MainNavNames.Recipe }
                    to = "/recipe"
                    element={ <GiCook style = {{ width: "inherit", height:"inherit" }} />  }
                    class="h-5"
                />
                <TooltipLink
                    tooltipText={ MainNavNames.CreateRecipe }
                    to = "/recipeplan"
                    element={ <GiHotMeal style = {{ width: "inherit", height:"inherit" }} />  }
                    class="h-5"
                />
                <TooltipLink
                    tooltipText={ MainNavNames.Weekplan }
                    to = "/weekplan"
                    element={ <GrSchedule style = {{ width: "inherit", height:"inherit" }} />  }
                    class="h-5"
                />
                <TooltipLink
                    tooltipText={ MainNavNames.Shoppinglist }
                    to = "/shoppinglist"
                    element={ <GrScheduleNew style = {{ width: "inherit", height:"inherit" }} />  }
                    class="h-6"
                />
                <TooltipLink
                    tooltipText={ MainNavNames.Ingredients }
                    to = "/ingredient"
                    element={ <GiCorn style = {{ width: "inherit", height:"inherit" }} />  }
                    class="h-6 hidden"
                />
            </div>
        </nav>
    );
}