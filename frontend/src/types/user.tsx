export type UserType = {
    //id:string,
    name:string,
    email:string,
    role:string,
    imageUrl:string,
    type:string
}

export type UserContextType = {
    user: UserType | null | undefined;
    setUser: (user: UserType | null | undefined) => void;
    isLoggedIn: boolean;
    setIsLoggedIn: (isLoggedIn: boolean) => void;
    logout: () => void;
}