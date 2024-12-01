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
import ConcertDetails from './components/ConcertDetails/ConcertDetails';
import Checkout from './components/checkout/Checkout';
import Invoice from './components/Invoice/Invoice';
import { PrivateRoute, AdminRoute } from './components/routes/ProtectedRoutes';


function App() {
  const location = useLocation();
  const isLoginOrRegister = location.pathname === '/login' || location.pathname === '/register';

  return (
      <div className="App">
          {!isLoginOrRegister && <Navbar />}
          <div className="content">
              <Routes>
                  {/* Rutas Públicas */}
                  <Route path="/login" element={<LoginPage />} />
                  <Route path="/register" element={<RegistrationPage />} />

                  {/* Rutas Protegidas para Usuarios Autenticados */}
                  <Route element={<PrivateRoute />}>
                      <Route path="/profile" element={<ProfilePage />} />
                      <Route path="/concert-details/:eventId" element={<ConcertDetails />} />
                      <Route path="/checkout" element={<Checkout />} />
                      <Route path="/invoice" element={<Invoice />} />
                  </Route>

                  {/* Rutas Exclusivas para Administradores */}
                  <Route element={<AdminRoute />}>
                      <Route path="/admin/user-management" element={<UserManagementPage />} />
                      <Route path="/update-user/:userId" element={<UpdateUser />} />
                  </Route>

                  {/* Ruta por defecto */}
                  <Route path="*" element={<Navigate to="/login" />} />
              </Routes>
          </div>
          {!isLoginOrRegister && <FooterComponent />}
      </div>
  )}

export default function AppWrapper() {
  return (
      <BrowserRouter>
          <App />
      </BrowserRouter>
  );
}