import {Unit} from "../types/Unit.tsx";

const UNIT_SERVICE_URL = 'http://localhost:8000/unit';


// Fetch all storage units
export const fetchAllUnits = async (): Promise<Unit[]> => {
    const response = await fetch(`${UNIT_SERVICE_URL}/getall`);
    if (!response.ok) {
        throw new Error('Failed to fetch storage units');
    }
    return await response.json();
};

// Fetch a specific unit by ID
export const fetchUnitById = async (unitId: number): Promise<Unit> => {
    const response = await fetch(`${UNIT_SERVICE_URL}/byid/${unitId}`);
    if (!response.ok) {
        throw new Error(`Failed to fetch unit with ID ${unitId}`);
    }
    return await response.json();
};
// Add a new unit
export const addUnit = async (newUnit: { name: string }): Promise<Unit> => {
    try {
        const response = await fetch(`${UNIT_SERVICE_URL}/addunit`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(newUnit),
        });

        if (!response.ok) {
            throw new Error('Failed to add unit');
        }

        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Error adding unit:', error);
        throw error;
    }
};
