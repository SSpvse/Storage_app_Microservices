import { User } from 'react-feather'; // Import the User icon from react-feather
import '../css/UserProfile.css';
import {useUser} from "../types/UserContext.tsx";


const UserProfile = () => {

    const {name, email} = useUser();
    console.log("Userprofile - user", {name, email});
    return (
        <div className="profile-container">
            <div className="profile-card">
                <User className="profile-image" size={150} />
                <h1 className="profile-name">{name || 'User Name'}</h1>
                <div className="profile-details">
                    <p><strong>Email:</strong> {email || 'example@example.com'}</p>
                    <p><strong>Location:</strong> Oslo, Norway</p>
                </div>
            </div>
        </div>
    );
};

export default UserProfile;
