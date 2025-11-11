import {useRecipeCart} from "../context/CardRecipeContext.tsx";
import {Link} from "react-router-dom";
import { useUser } from "../context/user/useUser.tsx";

type Props = {
    open: boolean;
}

export default function NavText(props: Readonly<Props>){
    const { recipeItems } = useRecipeCart();
    const { user } = useUser();

    return (
        <>
            <div className={`nav-links ${props.open ? 'open pt-2 pb-2 border-t-[1px] border-t-solid border-t-[#4d8959] font-extralight justify-around' : ''}`}>
                <ul className="flex flex-col gap-2">
                    <li className="hidden"><Link to="/">Start</Link></li>
                    <li><Link to="/recipe">Rezepte</Link></li>
                    <li><Link to="/recipeplan">Rezept erstellen (Zutaten {recipeItems.length})</Link></li>
                    <li className="hidden"><Link to="/job/migrate/slugs">Jobs</Link></li>
                </ul>
                <ul className="flex flex-col gap-2">
                    <li><Link to="/ingredient">Zutaten</Link></li>
                </ul>
                <ul className="flex flex-col gap-2">
                    <li><Link to="/shoppinglist">Einkaufsliste</Link></li>
                    <li><Link to="/weekplan">Wochenplan</Link></li>
                </ul>
                <ul className="flex flex-col gap-2"> 
                    <li>
                        <Link to={user?"/profile":"/login"}>
                            {user?"Profile":"Login"}
                        </Link>
                    </li>
                </ul>
            </div>
        </>
    );
}