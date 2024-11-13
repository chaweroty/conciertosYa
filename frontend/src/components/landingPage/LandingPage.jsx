// LandingPage.js
import React from 'react';
import Hero from '../Hero/Hero';
import Service from '../Services/Service';
import Concerts from '../Concerts/Concerts';


function LandingPage() {
    return (
        <div className="landing-page">
            <Hero />
            <Service />
            <Concerts />
        </div>
    );
}

export default LandingPage;
