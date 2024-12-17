// src/components/Register.tsx
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {registerUser} from "../services/LoginService.tsx";


const Register = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [username, setUsername] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleRegister = async () => {
        if (password !== confirmPassword) {
            setError("Passwords do not match");
            return;
        }

        try {


            // Hvis e-posten ikke finnes, set rolle til 'ADMIN' og registrer brukeren
            const user = {
                email,
                username,
                password,
                role: 'ADMIN',  // Set role to Admin if the email doesn't exist
            };

            await registerUser(user);
            console.log('User registered successfully');
            navigate('/login');
        } catch (error: any) {
            setError(error.message);
        }
    };

    return (
        <div className="register-container">
            <h2>Register</h2>
            <div className="register-form">
                <div>
                    <label htmlFor="email">Email:</label>
                    <input
                        type="email"
                        id="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                </div>
                <div>
                    <label htmlFor="username">Username:</label>
                    <input
                        type="text"
                        id="username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                    />
                </div>
                <div>
                    <label htmlFor="password">Password:</label>
                    <input
                        type="password"
                        id="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </div>
                <div>
                    <label htmlFor="confirmPassword">Confirm Password:</label>
                    <input
                        type="password"
                        id="confirmPassword"
                        value={confirmPassword}
                        onChange={(e) => setConfirmPassword(e.target.value)}
                    />
                </div>
                {error && <p className="error-message">{error}</p>}
                <button onClick={handleRegister}>Register</button>
            </div>
        </div>
    );
};

export default Register;
