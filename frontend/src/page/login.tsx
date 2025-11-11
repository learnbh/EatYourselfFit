import SignInGithubButton from "../component/SignInGithubButton";
import SignInGoogleButton from "../component/SignInGoogleButton";

export default function Login() {

    /** 
     * öffnet die jeweilige Anmeldeseite für google oder github
     * @param caller "google" oder "github"
     * 
    */
    function login(caller: string){
        // soll für localhost, sowie production (z.b. render.com) funtionieren
        const host:string =
            window.location.host === "localhost:5173" ?
                "http://localhost:8080"
                :
                window.location.origin;
        // öffne folgende Seite im selben Fenster
        window.open(host + "/oauth2/authorization/" + caller, "_self");
    }  

    return (
        <>
            <div className="flex flex-col gap-3">
                <h1>Login</h1>
                <div className="flex flex-row gap-2">
                       <SignInGoogleButton login={login}/>
                       <SignInGithubButton login={login}/>
                </div>
                    <span>oder</span>
                <div className="flex flex-col gap-2">
                    <form className="flex flex-col gap-2 text-left">
                            <label htmlFor="email">Email:</label>
                            <input type="text" name="email" placeholder="deine@email.de" className="border p-2"/>
                        <label htmlFor="password">Password:</label>
                        <input type="password" name="password" placeholder="Password" className="border p-2"/>
                        <button type="submit" className="border pt-3 pb-3">Login</button>
                    </form>
                </div>
                <div className="flex flex-col gap-2 pt-3 pb-1" id="additional-links">
                    <div className="flex flex-row gap-2">
                        <p>Kein Account?</p>
                        <a href="/register" className="text-violet-400">Registrieren</a>
                    </div>
                    <div className="flex flex-row gap-2">
                        <p>Passwort vergessen?</p>
                        <a href="/reset-password" className="text-violet-400">Passwort zurücksetzen</a>
                    </div>
                </div>
            </div>
        </>
    );
}