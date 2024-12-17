
const API_URL = "http://localhost:8000/auth/test";

// Funksjon for å sjekke om e-posten allerede finnes
export const checkEmailExists = async (email: string): Promise<boolean> => {
    try {
        console.log("This is the EMAIL in CHeckEmailExists" + email); // I get correct email here
        //const response = await fetch(`${API_URL}/check-email?email=${email}`);
        const response = await fetch(`${API_URL}/check-email/${email}`);
        if (response.ok) {
            const data = await response.json();
            return data.exists;
        } else {
            throw new Error('Failed to check email availability');
        }
    } catch (error) {
        console.error(error);
        throw new Error('Error during email check');
    }
};

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
