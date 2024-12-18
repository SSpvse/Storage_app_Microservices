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

        setUser: () => {} // Tom funksjon, denne blir overskrevet senere
    });

    console.log("This is the usahh" + user);

    useEffect(() => {
        // Sjekk om vi har lagret brukerdata i localStorage
        const storedUser = localStorage.getItem('user');
        if (storedUser) {
            setUser(JSON.parse(storedUser));
        }
    }, []);
    // Returner Provider-komponenten som gir tilgang til context
    return (
        <UserContext.Provider value={{ ...user, setUser }}>
            {children}
        </UserContext.Provider>
    );
};

// En custom hook for å bruke UserContext
export const useUser = () => {
    const context = useContext(UserContext);
    if (!context) {
        throw new Error("useUser must be used within a UserProvider");
    }
    return context;
};
