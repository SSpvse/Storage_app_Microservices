import {useState} from 'react';
import { useNavigate } from 'react-router-dom';
import {useUser} from "../types/UserContext.tsx";

const LoginProfile = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();


    const { setUser } = useUser();
    const handleLogin = async () => {
        const loginData = { email, password };

        try {
            const response = await fetch('http://localhost:8000/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(loginData),
            });

            if (response.ok) {
                const data = await response.json();
                localStorage.setItem('isLoggedIn', 'true');
                localStorage.setItem('userRole', data.role);

                console.log("Login success data", data);
                localStorage.setItem('user', JSON.stringify(data));
                setUser({
                    id: data.id,
                    name: data.name,
                    email: data.email,
                    setUser: setUser,
                });

                console.log("User logged in:", data);

                navigate('/'); // Hvis admin, naviger til dashboard
            } else {
                setError('Invalid username or password');
            }
        } catch (error:unknown) {
            if (error instanceof Error) {
                setError('Error: ' + error.message);  // Nå kan du trygt bruke error.message
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
                    <label htmlFor="username">Email:</label>
                    <input
                        type="text"
                        id="username"
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
