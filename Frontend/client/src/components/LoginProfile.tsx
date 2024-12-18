import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { registerUser } from "../services/LoginService.tsx";

const AuthForm = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [username, setUsername] = useState('');
    const [error, setError] = useState('');
    const [isLogin, setIsLogin] = useState(true); // Tilstand for å bytte mellom login og register
    const navigate = useNavigate();

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
                // localStorage.setItem('isLoggedIn', 'true');
                // localStorage.setItem('userRole', data.role); // Lagre rollen
                console.log("The user DATA:", data);

                navigate('/'); // Naviger til homepage
            } else {
                setError('Invalid username or password');
            }
        } catch (error: unknown) {
            if (error instanceof Error) {
                setError('Error: ' + error.message);  // Nå kan du trygt bruke error.message
            } else {
                setError('Unknown error occurred');
            }
        }
    };

    const handleRegister = async () => {
        if (password !== confirmPassword) {
            setError("Passwords do not match");
            return;
        }

        try {
            // Først, sjekk om e-posten allerede finnes
            const registeredUser = await registerUser(email, username, password);
            if (registeredUser) {
                setError('User already exists with that email. Please log in.');
                return;
            }

            await registerUser(email, username, password);
            console.log('User registered successfully');
            navigate('/login'); // Naviger til login etter vellykket registrering
        } catch (error: any) {
            setError(error.message);
        }
    };

    return (
        <div className="auth-container">
            <h2>{isLogin ? "Login" : "Register"}</h2>
            <div className="auth-form">
                <div>
                    <label htmlFor="email">Email:</label>
                    <input
                        type="email"
                        id="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                </div>
                {!isLogin && (
                    <div>
                        <label htmlFor="username">Username:</label>
                        <input
                            type="text"
                            id="username"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                        />
                    </div>
                )}
                <div>
                    <label htmlFor="password">Password:</label>
                    <input
                        type="password"
                        id="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </div>
                {!isLogin && (
                    <div>
                        <label htmlFor="confirmPassword">Confirm Password:</label>
                        <input
                            type="password"
                            id="confirmPassword"
                            value={confirmPassword}
                            onChange={(e) => setConfirmPassword(e.target.value)}
                        />
                    </div>
                )}
                {error && <p className="error-message">{error}</p>}
                <button onClick={isLogin ? handleLogin : handleRegister}>
                    {isLogin ? "Login" : "Register"}
                </button>
                <p className="toggle-auth">
                    {isLogin ? "Not registered? " : "Already have an account? "}
                    <span
                        onClick={() => setIsLogin(!isLogin)}
                        style={{ color: "#007bff", cursor: "pointer" }}
                    >
                        {isLogin ? "Sign up!" : "Login"}
                    </span>
                </p>
            </div>
        </div>
    );
};

export default AuthForm;
