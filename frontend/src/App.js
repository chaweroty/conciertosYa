import React from 'react';
import { BrowserRouter, Routes, Route, Navigate, useLocation } from "react-router-dom";
import Navbar from './components/common/Navbar';
import LoginPage from './components/auth/LoginPage';
import RegistrationPage from './components/auth/RegistrationPage';
import FooterComponent from './components/common/Footer';
import UserService from './components/service/UserService';
import UpdateUser from './components/userspage/Updateuser';

import UserManagementPage from './components/userspage/UserManagementPage';
import ProfilePage from './components/userspage/ProfilePage';

function App() {
  const isAuthenticated = UserService.isAuthenticated();
  const isAdmin = UserService.adminOnly();
  
  // Obtiene la ruta actual utilizando useLocation
  const location = useLocation();
  const isLoginOrRegister = location.pathname === "/login" || location.pathname === "/register";

  return (
    <div className="App">
      {/* Condicional para no mostrar Navbar ni Footer en login o register */}
      {!isLoginOrRegister && <Navbar />}
      
      <div className="content">
        <Routes>
          {/* Rutas Públicas */}
          <Route exact path="/" element={<LoginPage />} />
          <Route exact path="/login" element={<LoginPage />} />
          <Route exact path="/register" element={<RegistrationPage />} />

          {/* Rutas Protegidas - Solo accesibles si el usuario está autenticado */}
          {isAuthenticated && (
            <>
              <Route path="/profile" element={<ProfilePage />} />
            </>
          )}

          {/* Rutas de Admin - Solo accesibles si el usuario es admin */}
          {isAuthenticated && isAdmin && (
            <>
              <Route path="/admin/user-management" element={<UserManagementPage />} />
              <Route path="/update-user/:userId" element={<UpdateUser />} />
            </>
          )}

          {/* Redirecciona a login si ninguna ruta coincide */}
          <Route path="*" element={<Navigate to="/login" />} />
        </Routes>
      </div>

      {/* Condicional para no mostrar el Footer en login o register */}
      {!isLoginOrRegister && <FooterComponent />}
    </div>
  );
}

export default function AppWrapper() {
  return (
    <BrowserRouter>
      <App />
    </BrowserRouter>
  );
}
