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
        console.log("ADDUNIT SERVICE in unit ?? Hello" + newUnit); //Jeg kommer hit!!!

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

// Delete a unit by its ID
export const deleteUnitById = async (id: number): Promise<void> => {

    console.log("DELETEUNIT SERVICE in unit ?? Hello" + id) //Jeg kommer hit!!!
    try {
        const response = await fetch(`${UNIT_SERVICE_URL}/delete/${id}`, {
            method: "DELETE",
        });

        if (!response.ok) {
            throw new Error(`Failed to delete unit with ID ${id}`);
        }

        console.log(`Unit with ID ${id} has been deleted.`);
    } catch (error) {
        console.error("Error deleting unit:", error);
        alert("Failed to delete unit. Please try again later.");
        throw error;
    }
};


// Invite a guest to a unit
export interface InviteGuestDTO {
    unitID: number;
    ownerID: number;
    guestEmail: string;
    role: "GUEST" | "OWNER";
}

export const inviteGuest = async (inviteData: InviteGuestDTO): Promise<number> => {
    try {
        const response = await fetch(`${UNIT_SERVICE_URL}/invite`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(inviteData),
        });

        if (!response.ok) {
            throw new Error('Failed to invite guest');
        }

        const accessId: number = await response.json();
        console.log('Guest invited successfully. Access ID:', accessId);
        return accessId;
    } catch (error) {
        console.error('Error inviting guest:', error);
        throw error;
    }
};