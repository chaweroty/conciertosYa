import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import UserService from "../service/UserService";
import Sidebar from "../sidebar/Sidebar";
import Dashboard from "../dashboard/Dashboard";
import Users from "../users/Users";
import ConcertsTable from "../ConcertsTable/ConcertsTable";
import ArtistsTable from "../ArtistsTable/ArtistsTable";
import PlaceTable from "../PlaceTable/PlaceTable";

function UserManagementPage() {
  const [users, setUsers] = useState([]);
  const [activeContent, setActiveContent] = useState("dashboard"); // Controla el contenido activo

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = async () => {
    try {
      const token = localStorage.getItem("token");
      const response = await UserService.getAllUsers(token);
      setUsers(response.ourUsersList);
    } catch (error) {
      console.error("Error fetching users:", error);
    }
  };

  const deleteUser = async (userId) => {
    try {
      const confirmDelete = window.confirm(
        "Are you sure you want to delete this user?"
      );

      const token = localStorage.getItem("token");
      if (confirmDelete) {
        await UserService.deleteUser(userId, token);
        fetchUsers();
      }
    } catch (error) {
      console.error("Error deleting user:", error);
    }
  };

  const renderContent = () => {
    if (activeContent === "dashboard") {
      return <Dashboard />;
    } else if (activeContent === "users") {
      return <Users />; // Renderiza el componente Users
    } else if (activeContent === "userList") {
      return  <Users />;
    } else if (activeContent === "eventList") {
      return <ConcertsTable/>;
       ///
    }else if (activeContent === "placeList") {
      return <PlaceTable/>;
       ///
    }else if (activeContent === "artistList") {
      return <ArtistsTable/>;
       ///
    } 
    else {
      return <div>Contenido no encontrado</div>;
    }
  };

  return (
    <div className="user-management-container flex h-screen">
      {/* Sidebar */}
      <div className="sidebar bg-gray-800 text-white w-64 h-screen">
        <Sidebar setActiveContent={setActiveContent} />
      </div>

      {/* Main Content */}
      <div className="content flex-1 p-4 bg-gray-100 overflow-y-auto">
        {renderContent()}
      </div>
    </div>
  );
}

export default UserManagementPage;
