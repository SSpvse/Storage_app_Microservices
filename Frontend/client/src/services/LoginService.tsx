
const API_URL = "http://localhost:8000/auth";


// Funksjon for å registrere en bruker
export const registerUser = async (email: string, username: string, password: string) => {
    try {

        const user = {
            email,
            username,
            password
        };

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
        throw new Error('Email already taken!');
    }
};
