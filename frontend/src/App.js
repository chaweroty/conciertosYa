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
function App() {
  const isAuthenticated = UserService.isAuthenticated();
  const isAdmin = UserService.adminOnly();
  
  // Obtiene la ruta actual utilizando useLocation
  const location = useLocation();
  const isLoginOrRegister = location.pathname === "/login" || location.pathname === "/register";

  return (
    <div className="App">
      {/* Condicional para no mostrar Navbar ni Footer en login o register */}
      {/* */}
      {!isLoginOrRegister && <Navbar />}
      
      <div className="content">
        <Routes>
          {/* Rutas Públicas */}
          <Route exact path="/login" element={<LoginPage />} />
          <Route exact path="/register" element={<RegistrationPage />} />
          <Route path="/admin/user-management" element={<UserManagementPage />} />
          <Route path="/update-user/:userId" element={<UpdateUser />} />
          <Route path="/concert-details/:artistId" element={<ConcertDetails />} />
          <Route path="/Checkout" element={<Checkout />} />
          <Route path="/profile" element={<ProfilePage />} />
          <Route path="/concert-details/:artistId" element={<ConcertDetails />} />
          <Route path="/invoice" element={<Invoice />} />

       {/* Rutas Protegidas - Solo accesibles si el usuario está autenticado */}
{isAuthenticated && (
  <>
    <Route path="/profile" element={<ProfilePage />} />
    <Route path="/concert-details/:artistId" element={<ConcertDetails />} />
  </>
)}


          {/* Rutas de Admin - Solo accesibles si el usuario es admin */}
          {isAuthenticated && isAdmin && (
            <>
            {/* <Route path="/admin/user-management" element={<UserManagementPage />} />*/}
            {/*<Route path="/update-user/:userId" element={<UpdateUser />} /> */}
            {/* <Route path="/concert-details/:artistId" element={<ConcertDetails />} />*/}
            {/*  <Route path="/Checkout" element={<Checkout />} />*/}

            
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
