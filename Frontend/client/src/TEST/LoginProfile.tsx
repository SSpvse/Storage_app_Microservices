import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleLogin = () => {
        // Hardkodet brukernavn og passord
        const validUsername = 'hotmail';
        const validPassword = 'password';

        if (username === validUsername && password === validPassword) {
            // Hvis brukernavn og passord er riktig, sett innlogget status
            localStorage.setItem('isLoggedIn', 'true'); // Lagre innloggingsstatusen
            navigate('/userPage'); // Naviger til brukerens side
        } else {
            setError('Invalid username or password');
        }
    };

    return (
        <div className="login-container">
            <h2>Login</h2>
            <div className="login-form">
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
                {error && <p className="error-message">{error}</p>}
                <button onClick={handleLogin}>Login</button>
            </div>
        </div>
    );
};

export default Login;
