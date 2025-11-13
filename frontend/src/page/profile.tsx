import { useUser } from "../context/user/useUser";
import { LoginTypes } from "../enums";

export default function Profile() {     
    const { user, logout } = useUser()

    function handleLogout() {
        logout();
    }
    
  if (user === null || user === undefined) {
    return <p>⏳ Lade Benutzerdaten oder nicht eingeloggt...</p>;
  }
    return (
      <div className="flex flex-col bg-gray-100 p-5">          
        <header className="mb-5">
            <h1>Welcome back {user.name}!</h1>
        </header>
        <main className="grid grid-cols-2 justify-items-start gap-1 mb-5 "> 
          <span >Name:</span>  
          <span >{user.name}</span>
          {user.type !== LoginTypes.Google && (       
            <span> Profilbild: </span>)
          }  
          {user.type !== LoginTypes.Google && ( 
            <img src={user.imageUrl} alt="Profilbild" width={25}/>)
          }  
          <span>Email:</span>
          <span>{user.email}</span>
          <span>Role:</span>
          <span>{user.role}</span>
          <span> Logintyp:</span>
          <span> {user.type}</span>
        </main>
        
        <div className="flex-1"></div> {/* Spacer */}
        
        <button  className=" border addbtn w-full mt-auto" onClick={handleLogout}>Logout</button>
      </div>
    );
}
