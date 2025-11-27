import { useContext } from "react";
import type { UserContextType } from "../../types/user";
import { UserContext } from "./UserContext";

export function useUser(): UserContextType {
    const context = useContext(UserContext);
    if (!context) {
        throw new Error("useUser must be used within a UserProvider");
    }
    return context;
}