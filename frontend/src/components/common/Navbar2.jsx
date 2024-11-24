// Navbar.jsx
import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import UserService from '../service/UserService';

function Navbar2() {
    const isAuthenticated = UserService.isAuthenticated();
    const isAdmin = UserService.isAdmin();
    const navigate = useNavigate();

    const handleLogout = () => {
        const confirmDelete = window.confirm('Are you sure you want to logout this user?');
        if (confirmDelete) {
            UserService.logout();
            navigate("/login"); // Redirige al usuario al login después de cerrar sesión
        }
    };

    return (
        <nav className="bg-black p-4">
            <ul className="flex justify-between items-center text-white">
                <li className="font-bold text-2xl">
                    <Link to="/">ConciertosYa</Link>
                </li>
                <div className="flex space-x-4">
                    {!isAuthenticated && (
                        <li>
                            <Link to="/login" className="hover:underline">Login</Link>
                        </li>
                    )}
                    {isAuthenticated && (
                        <>
                            <li>
                                <Link to="/profile" className="hover:underline">Home</Link>
                            </li>
                            {isAdmin && (
                                <li>
                                    <Link to="/admin/user-management" className="hover:underline">User Management</Link>
                                </li>
                            )}
                            <li>
                                <button onClick={handleLogout} className="hover:underline">Logout</button>
                            </li>
                        </>
                    )}
                </div>
            </ul>
        </nav>
    );
}

export default Navbar2;
