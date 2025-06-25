import Navbar from "./Navbar.tsx";
import {Link} from "react-router-dom";

export default function Header(){
    return (
        <>
            <header className="header">
                <Navbar />
                <Link to="/"><div className="logo">🍏 Ess dich fit</div></Link>
            </header>
        </>
    );
}