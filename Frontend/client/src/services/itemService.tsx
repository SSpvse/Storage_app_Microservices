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
        alert('Failed to fetch items. Please try again later');
    }
};

// Fetching items from specific unit id
export const fetchItemsByUnitId = async (unitId: number): Promise<{Item}> => {
    console.log("This is the id:" + unitId)
    const response = await fetch(`${ITEM_SERVICE_URL}/byid/${unitId}`);
    if (!response.ok) {
        throw new Error(`Failed to fetch item with ID ${unitId}`);
    }
    return await response.json();
    /*
        try {
                                                        // `http://localhost:8000/item/byid/${id}`
                                                        // `http://unitservice:8081/item/byid/${id}`
            const response = await fetch(`${ITEM_SERVICE_URL}/byid/${unitId}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

    /*

            //Handling empty response:
            if (response.status === 204) {
                console.warn(`No content returned from API, unit ID: ${unitId}`);
                return {items: []};
            }

            const data = await response.json();
            if (!data || data.length === 0) {
                console.log("Empty Response body:", data);
                return {
                    items: [],
                };
            }


            console.log("responce DATA :: : :: "+data)
            return {
                items: data.items || data,
            };
        } catch (error) {
            console.error('Error fetching items:', error);
            return {items: []}; // returning default empty values
        }*/
};
// Fetch a specific item by its ID
export const fetchItemById = async (itemId: number): Promise<Item> => {
    try {
        const response = await fetch(`${ITEM_SERVICE_URL}/get/${itemId}`); //TODO THIS SHOULD BE id not itemID !!!! (see backend)
        if (!response.ok) {
            throw new Error(`Failed to fetch item with ID ${itemId}`);
        }
        return await response.json();
    } catch (error) {
        console.error('Error fetching item:', error);
        throw new Error('Failed to fetch item');
    }
};
// Add a new item
export const addItem = async (newItem: NewItem): Promise<Item> => {
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
    const savedItem = await response.json();
    return savedItem;
}

// delete an item by its ID
export const deleteItem = async (itemId: number): Promise<void> => {
    try {
        const response = await fetch(`${ITEM_SERVICE_URL}/delete/${itemId}`, {
            method: "DELETE",
        });

        if (!response.ok) {
            throw new Error(`Failed to delete item with ID ${itemId}`);
        }
    } catch (error) {
        console.error("Error deleting item:", error);
        throw new Error("Failed to delete item");
    }
};

/*
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
*/