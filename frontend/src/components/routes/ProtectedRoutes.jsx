// components/routes/ProtectedRoutes.jsx
import React from 'react';
import { Navigate, Outlet } from 'react-router-dom';
import UserService from '../service/UserService';

export const PrivateRoute = () => {
    const isAuthenticated = UserService.isAuthenticated();
    return isAuthenticated ? <Outlet /> : <Navigate to="/login" />;
};

export const AdminRoute = () => {
    const isAuthenticated = UserService.isAuthenticated();
    const isAdmin = UserService.adminOnly();
    return isAuthenticated && isAdmin ? <Outlet /> : <Navigate to="/profile" />;
};
