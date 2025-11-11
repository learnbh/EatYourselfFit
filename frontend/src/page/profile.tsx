import { useUser } from "../context/user/useUser";

export default function Profile() {     
    const { user, logout } = useUser()

    function handleLogout() {
        logout();
    }
    
  if (user === null || user === undefined) {
    return <p>⏳ Lade Benutzerdaten oder nicht eingeloggt...</p>;
  }
    return (
        <div className="flex flex-col items-center bg-gray-100 p-5">
            <p>Welcome back {user.name}!</p>
            <p>Email: {user.email}</p>
            <img src={user.imageUrl} alt="Profilbild" width={100} />
            <button onClick={handleLogout}>Logout</button>
        </div>
    );
}
