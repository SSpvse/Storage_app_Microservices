import { User } from 'react-feather'; // Import the User icon from react-feather
import '../css/UserProfile.css';
import {useUser} from "../types/UserContext.tsx";


const UserProfile = () => {

    const {username, email} = useUser();
    return (
        <div className="profile-container">
            <div className="profile-card">
                <User className="profile-image" size={150} /> {/* Use the User icon */}
                <h1 className="profile-name">{username || 'User Name'}</h1>
                <div className="profile-details">
                    <p><strong>Email:</strong> {email || 'example@example.com'}</p>
                    <p><strong>Location:</strong> Oslo, Norway</p>
                </div>
            </div>
        </div>
    );
};

export default UserProfile;
