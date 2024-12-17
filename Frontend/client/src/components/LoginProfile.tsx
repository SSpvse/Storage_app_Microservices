import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {loginUser} from "../services/LoginService.tsx";

const LoginProfile = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleLogin = async () => {

        try {
            const data = await loginUser(email, password); // Kall loginUser-funksjonen fra LoginService
            localStorage.setItem('isLoggedIn', 'true');
            localStorage.setItem('userRole', data.role); // Lagre rollen
            navigate('/'); // Hvis admin, naviger til dashboard
        } catch (error: unknown) {
            if (error instanceof Error) {
                setError(error.message); // Bruk feilmeldingen fra loginUser
            } else {
                setError('Unknown error occurred');
            }
        }
    };

    return (
        <div className="login-container">
            <h2>Login</h2>
            <div className="login-form">
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

export default LoginProfile;
