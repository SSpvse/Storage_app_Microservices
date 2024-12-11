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

        // Checking if the data is empty or not
        if (!data || data.length === 0) {
            console.log("Not items found for this unit.");
        }
        return data;
    } catch (error) {
        console.error('Error fetching items:', error);
    }
};

// Fetching items from specific unit id
export const fetchItemsByUnitId = async (unitId: number): Promise<{items: Item[]; unitType:string}> => {
    console.log("This is the id:" + unitId)
    try {
                                                    // `http://localhost:8000/item/byid/${id}`
                                                    // `http://unitservice:8081/item/byid/${id}`
        const response = await fetch(`${ITEM_SERVICE_URL}/byid/${unitId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        console.log("Response status:", response.status);
        console.log("Response headers:", response.headers);

        //Handling empty response:
        if (response.status === 204) {
            console.warn(`No content returned from API, unit ID: ${unitId}`);
            return {items: [], unitType: ''};
        }
        const responseBody = await response.text();

        if (!responseBody) {
            console.warn("Empty response body");
            console.log("Response body:", responseBody);
            return {
                items: [],
                unitType: '',
            };
        }

        //const data = await response.json();
        const data = JSON.parse(responseBody);

        return {
            items: data.items || [],
            unitType:  data.unitType || '',
        };
    } catch (error) {
        console.error('Error fetching items:', error);
        return {items: [], unitType: ''}; // returning default empty values
    }
};

// Add a new item
export const addItem = async (newItem: NewItem): Promise<Item | null> => {
    try {
        console.log("Adding item:", newItem)
        const response = await fetch(`${ITEM_SERVICE_URL}/additem`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(newItem),
        });

        if (!response.ok) {
            throw new Error('Failed to add item from ItemService (Add a new item )');
        }
        const data = await response.json();
        return data;
    } catch (e){
        console.error("Failed adding item", e);
        return null;
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
        return data;
    } catch (e) {
        console.error("Failed adding item to unit", e);
        throw e;
    }
};
