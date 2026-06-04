import { createContext, useContext, useState } from "react"
import { jwtDecode } from "jwt-decode"

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
    const [token, setToken] = useState(localStorage.getItem("token"))
    const [user, setUser] = useState(() => {
        const savedToken = localStorage.getItem("token")
        if (savedToken) {
            try {
                const decoded = jwtDecode(savedToken)
                return {
                    email: decoded.sub,
                    name: decoded.sub,
                    role: decoded.scope?.replace("ROLE_", "")
                }
            } catch { return null }
        }
        return null
    });

    const login = (tokenValue) => {
        localStorage.setItem("token", tokenValue);
        setToken(tokenValue);
        const decoded = jwtDecode(tokenValue);
        setUser({
            email: decoded.sub,
            name: decoded.sub,
            role: decoded.scope?.replace("ROLE_", "")
        });
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
