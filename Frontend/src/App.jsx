import { BrowserRouter, Route, Routes, Navigate } from "react-router-dom";
import { AuthProvider } from "./context/AuthContext";
import PrivateRoute from "./components/PrivateRoute";
import RoleGuard from "./components/RoleGuard";
import MainLayout from "./layouts/MainLayout";
import Login from "./pages/Login";
import ForgotPassword from "./pages/ForgotPassword";
import ResetPassword from "./pages/ResetPassword";
import Dashboard from "./pages/Dashboard";
import UserList from "./pages/users/UserList";
import UserCreate from "./pages/users/UserCreate";
import UserEdit from "./pages/users/UserEdit";
import TeamList from "./pages/teams/TeamList";
import TeamCreate from "./pages/teams/TeamCreate";
import TeamDetail from "./pages/teams/TeamDetail";
import Profile from "./pages/profile/Profile";

export default function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/forgot-password" element={<ForgotPassword />} />
          <Route path="/reset-password" element={<ResetPassword />} />

          <Route path="/dashboard" element={
            <PrivateRoute><MainLayout><Dashboard /></MainLayout></PrivateRoute>
          } />

          <Route path="/users" element={
            <PrivateRoute><MainLayout><RoleGuard roles={["ADMIN"]}><UserList /></RoleGuard></MainLayout></PrivateRoute>
          } />
          <Route path="/users/create" element={
            <PrivateRoute><MainLayout><RoleGuard roles={["ADMIN"]}><UserCreate /></RoleGuard></MainLayout></PrivateRoute>
          } />
          <Route path="/users/:id/edit" element={
            <PrivateRoute><MainLayout><RoleGuard roles={["ADMIN"]}><UserEdit /></RoleGuard></MainLayout></PrivateRoute>
          } />

          <Route path="/teams" element={
            <PrivateRoute><MainLayout><RoleGuard roles={["ADMIN", "COACH"]}><TeamList /></RoleGuard></MainLayout></PrivateRoute>
          } />
          <Route path="/teams/create" element={
            <PrivateRoute><MainLayout><RoleGuard roles={["ADMIN", "COACH"]}><TeamCreate /></RoleGuard></MainLayout></PrivateRoute>
          } />
          <Route path="/teams/:id" element={
            <PrivateRoute><MainLayout><RoleGuard roles={["ADMIN", "COACH"]}><TeamDetail /></RoleGuard></MainLayout></PrivateRoute>
          } />

          <Route path="/profile" element={
            <PrivateRoute><MainLayout><Profile /></MainLayout></PrivateRoute>
          } />

          <Route path="*" element={<Navigate to="/login" />} />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  )
}
