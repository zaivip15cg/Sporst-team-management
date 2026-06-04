import { Navigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import { jwtDecode } from "jwt-decode";

export default function PrivateRoute({ children }) {
    const { token, logout } = useAuth();

    if (!token) return <Navigate to="/login" replace />;

    try {
        const decoded = jwtDecode(token);
        const isExpired = decoded.exp * 1000 < Date.now();
        if (isExpired) {
            logout();
            return <Navigate to="/login" replace />;
        }
    } catch {
        logout();
        return <Navigate to="/login" replace />;
    }

    return children;
}
