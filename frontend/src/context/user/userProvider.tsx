import {  useState, type ReactNode } from "react";
import type { UserType } from "../../types/user";
import { UserContext } from "./UserContext";
import axios from "axios";

export function UserProvider({children}:{children: ReactNode}){
    const [user, setUser] = useState<UserType|undefined|null>(null);
    const [isLoggedIn, setIsLoggedIn] = useState<boolean>(false);

    function logout(){
        // soll für localhost, sowie production (z.b. render.com) funktionieren
        axios.get("/logout", { withCredentials: true })
            .then(response => {
                setIsLoggedIn(false);
                setUser(null);
                console.log("Response from /logout:", JSON.stringify(response.data));
                const host:string =
                    window.location.host === "localhost:5173" ?
                        "http://localhost:8080"
                        :
                        window.location.origin;
                // öffne folgende Seite im selben Fenster
                window.open(host + "/logout", "_self");
            })
            .catch(e=> {
                console.log(e.message);
            });
        console.log("logout:" + user);
    } 

    return (
        <UserContext.Provider value={{ user, setUser, isLoggedIn, setIsLoggedIn, logout }}>
            {children}
        </UserContext.Provider>
    );
}