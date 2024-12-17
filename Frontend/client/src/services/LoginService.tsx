
const API_URL = "http://localhost:8000/auth";



// Funksjon for å registrere en bruker
export const registerUser = async (user: { email: string, username: string, password: string, role: string }) => {
    try {
        const response = await fetch(`${API_URL}/register`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(user),
        });

        if (response.ok) {
            return await response.json();
        } else {
            throw new Error('Registration failed');
        }
    } catch (error) {
        console.error(error);
        throw new Error('Error during registration');
    }
};

export const loginUser = async (email: string, password: string) => {
    try {
        const response = await fetch(`${API_URL}/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ email, password }),
        });

        if (response.ok) {
            return await response.json();
        } else {
            throw new Error('Invalid username or password');
        }
    } catch (error) {
        console.error(error);
        throw new Error('Error during login');
    }
};