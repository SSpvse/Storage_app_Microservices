import {Item} from "../types/Item.tsx";
import {NewItem} from "../types/NewItem.tsx";

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
export const fetchItemsByUnitId = async (unitId: number) => {
    try {
        const response = await fetch(`${ITEM_SERVICE_URL}/byid/{id}`, {
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
