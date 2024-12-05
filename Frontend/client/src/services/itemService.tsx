import {Item} from "../types/Item.tsx";
import {NewItem} from "../types/NewItem.tsx";

//const ITEM_SERVICE_URL = "http://itemservice:8082/item";
const ITEM_SERVICE_URL = "http://localhost:8000/item";

// Fetch all items
export const fetchAllItems = async (): Promise<Item[]> => {
    try {
        const response = await fetch(`${ITEM_SERVICE_URL}/getall`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Error fetching items:', error);
    }
};

// Fetching items from specific unit id
export const fetchItemsByUnitId = async (id: number): Promise<Item[]> => {
    console.log("This is the id:" + id)
    try {
                                                    // `http://localhost:8000/item/byid/${id}`
                                                    // `http://unitservice:8081/item/byid/${id}`
        const response = await fetch(`http://localhost:8000/item/byid/${id}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const data = await response.json();

        return data;
    } catch (error) {
        console.error('Error fetching items:', error);
        return []; // returning an empty array if error
    }
};

// Add a new item
export const addItem = async (newItem: NewItem): Promise<Item> => {
    try {
        const response = await fetch(`${ITEM_SERVICE_URL}/additem`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(newItem),
        });

        if (!response.ok) {
            throw new Error('Failed to add item');
        }
        const data = await response.json();
        return data;
    } catch (e){
        console.error("Failed adding item", e);
        return e;
    }
};
// Add an item to a specific unit
export const addItemToUnit = async (unitId: number, newItem: NewItem): Promise<Item> => {
    try {
        const response = await fetch(`${ITEM_SERVICE_URL}/units/${unitId}/items`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(newItem),
        });

        if (!response.ok) {
            throw new Error('Failed to add item to unit');
        }
        const data = await response.json();
        return data; // Assuming this returns the newly created item
    } catch (e) {
        console.error("Failed adding item to unit", e);
        throw e; // Re-throwing the error for further handling
    }
};
