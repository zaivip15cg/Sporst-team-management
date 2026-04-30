import { createContext, useContext, useState } from "react"


const AuthContext = createContext(null);

export function AuthProvider({ children }) {
    const [token, setToken] = useState(localStorage.getItem("token"))
    const [user, setUser] = useState(null);

    const login = (tokenValue) => {
        localStorage.setItem("token", tokenValue);
        setToken(tokenValue);
    };


    const logout = () => {
        localStorage.removeItem("token");
        setToken(null);
        setUser(null);
    }

    return (
        <AuthContext.Provider value={{ token, user, setUser, login, logout }}>
            {children}
        </AuthContext.Provider>
    )


}


export function useAuth() {
    return useContext(AuthContext);
}


