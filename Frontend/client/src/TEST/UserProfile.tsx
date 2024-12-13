import React from 'react';
import { User } from 'react-feather'; // Import the User icon from react-feather
import '../css/UserProfile.css';

const UserProfile = () => {
    return (
        <div className="profile-container">
            <div className="profile-card">
                <User className="profile-image" size={150} /> {/* Use the User icon */}
                <h1 className="profile-name">User Name</h1>
                <div className="profile-details">
                    <p><strong>Email:</strong> example@example.com</p>
                    <p><strong>Location:</strong> Oslo, Norway</p>
                </div>
            </div>
        </div>
    );
};

export default UserProfile;
