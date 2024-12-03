import React, { useState, useEffect } from "react";
import UserService from "../service/UserService";
import Hero from "../Hero/Hero";
import Posters from "../Posters/Posters";


function ProfilePage() {
  const [profileInfo, setProfileInfo] = useState({});
  const [filteredEvents, setFilteredEvents] = useState([]);

  useEffect(() => {
    fetchProfileInfo();
  }, []);

  const fetchProfileInfo = async () => {
    try {
      const token = localStorage.getItem("token");
      const response = await UserService.getYourProfile(token);
      setProfileInfo(response.ourUsers);
    } catch (error) {
      console.error("Error fetching profile information:", error);
    }
  };

  return (
    <div className="profile-page-container">
      <Hero />
      <Posters events={filteredEvents} />
    </div>
  );
}

export default ProfilePage;
