import { Navigate, UNSAFE_createClientRoutesWithHMRRevalidationOptOut } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

 export default function RoleGuard({ children, roles = [] }) {
    const { user } = useAuth();

    if (!user || (roles.length > 0 && !roles.includes(user.role))) {
        return <Navigate to="/dashboard" />;
    }

    return children;
}

