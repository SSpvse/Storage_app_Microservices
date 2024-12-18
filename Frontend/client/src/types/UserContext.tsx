import React, {createContext, useState, ReactNode, useContext, useEffect} from 'react';

export interface UserContext {
    id: number;
    username: string;
    email: string;
    setUser: (user: UserContext) => void;
}

export const UserContext = createContext<UserContext | undefined>(undefined);

export const UserProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
    const [user, setUser] = useState<UserContext>({
        id: 0 ,
        username: '',
        email: '',

        setUser: () => {}
    });

    console.log("This is the usahh" + user);

    useEffect(() => {

        const storedUser = localStorage.getItem('user');
        if (storedUser) {
            setUser(JSON.parse(storedUser));
        }
    }, []);

    return (
        <UserContext.Provider value={{ ...user, setUser }}>
            {children}
        </UserContext.Provider>
    );
};


export const useUser = () => {
    const context = useContext(UserContext);
    if (!context) {
        throw new Error("useUser must be used within a UserProvider");
    }
    return context;
};
