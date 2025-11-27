import { useCallback, useEffect } from "react";
import { useUser } from "../context/user/useUser";
import { useNavigate } from "react-router-dom";
import type { UserType } from "../types/user";
import axios from "axios";

export default function LoginSuccess() {     
    const { setUser, setIsLoggedIn } = useUser()
    const navigateTo = useNavigate();          

    const loadUser = useCallback((): void => {
        // withCredentials: true - Wichtig, um Cookies zu senden
        axios.get("/eyf/login/success", { withCredentials: true })
            .then(response => {
                const userHelper: UserType = {
                    name: response.data.name,
                    email: response.data.email,
                    role: response.data.role,
                    imageUrl: response.data.imageUrl,
                    type: response.data.type
                }
                // setze den User-Zustand
                setIsLoggedIn(true);
                setUser(userHelper);
                navigateTo("/profile");
                console.log("login:"+JSON.stringify(userHelper));
            })
            .catch(e=> {
                setUser(null);
                setIsLoggedIn(false);
                console.log(e.message);
            });
    }, [setUser, setIsLoggedIn, navigateTo]);  

    useEffect(() => {
        loadUser();
    }, [loadUser]);
    return (
        <div>
            <p>⏳ Logging in...</p>
        </div>
    );
}
