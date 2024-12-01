import React, { useState, useEffect } from 'react';
import UserService from '../service/UserService';
import { Link } from 'react-router-dom';
import Hero from '../Hero/Hero';
import Concerts from '../Concerts/Concerts';
import Posters from '../Posters/Posters';
import Searcher from '../Searcher/Searcher';

function ProfilePage() {
    const [profileInfo, setProfileInfo] = useState({});

    useEffect(() => {
        fetchProfileInfo();
    }, []);

    const fetchProfileInfo = async () => {
        try {
            const token = localStorage.getItem('token'); // Retrieve the token from localStorage
            const response = await UserService.getYourProfile(token);
            setProfileInfo(response.ourUsers);
        } catch (error) {
            console.error('Error fetching profile information:', error);
        }
    };
    return (
        <div className="profile-page-container">
            <Hero/>
            <div className="flex justify-center items-center py-10">
                <Searcher />
            </div>
            <Posters/>         
        </div>
    );
}

export default ProfilePage;