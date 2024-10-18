import { useEffect, useState } from "react";
import {addItem, fetchAllItems, fetchItemsByUnitId} from "../services/itemService";
import {Item} from "../types/Item.tsx";
import UnitList from "./UnitList.tsx";
import UnitManager from "./UnitList.tsx";
import {NewItem} from "../types/NewItem.tsx";


const ItemList = ({selectedUnit, onUnitDeselected}) => {
    const [items, setItems] = useState<Item[]>([]);
    // holding the Id of the selected unit
    //const [selectedUnit, setSelectedUnit] = useState<number | null>();
    const [newItemName, setNewItemName] = useState("");
    const [newItemDescription, setNewItemDescription] = useState("");


    useEffect(() => {

        if(!selectedUnit) return; // Skipping if no unit is selected
        const handleFetchItems = async () => {
            try {
                const fetchedItems = await fetchItemsByUnitId(selectedUnit) as Item[]; //Fetch items based on selected unit
                setItems(fetchedItems); // Only set items for the selected unit
            } catch (err) {
                console.error("Failed to fetch items", err);
            }
        };

        handleFetchItems()
    }, [selectedUnit]); // Depends on selectedUnit

    const handleAddItem = async () => {
        if (!newItemName || !newItemDescription){
            console.error("Please provide item details");
            return; // EXit if no item name or descrip..
        }

        if (!selectedUnit){
            console.error("Please select a unit first");
            return;
        }
        const newItem : NewItem = {
            name: newItemName, // Set the name of the item
            description: newItemDescription, // Set the description of the item
            location: null, // Set location (optional, can be filled later)
            quantity: 1,
            date: new Date().toISOString(), // Use current date in ISO format
            unitID: selectedUnit, // Use the selected unit ID
            userID: 1 // Replace with actual user ID (for demo purposes, hardcoded here)
        };

        try {
            // Assuming that the backend returns the created item with its itemId
            const addedItem = await addItem(newItem) as Item; // Call the service and add the new item
            //handleFetchItems(); // Calling this method to refresh list after adding
            // Ensure addedItem is fully typed as an Item and contains `itemId`
            if (addedItem.itemId) {
                setItems((prevItems) => [...prevItems, addedItem]); // Only add `Item` objects to the state
            }
            // Here I am clering the input field after the user has added the item
            setNewItemName("");
            setNewItemDescription("");

        } catch (err) {
            console.error("failed to add the item", err)
        }
    }


    return (
        <div>
            <h3>Items in Selected Unit</h3>
            <button onClick={onUnitDeselected}>Back to Units</button>
            <div>
                {items.length > 0 ? (
                    <ul>
                        {items.map((item) => (
                            <li key={item.itemId}>
                                <strong>{item.name}</strong>: {item.description}
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p>No items available in this unit.</p>
                )}
            </div>
            <h3>Add Item to Unit</h3>
            <input
                type="text"
                value={newItemName}
                onChange={(e) => setNewItemName(e.target.value)}
                placeholder="Item Name"
            />
            <input
                type="text"
                value={newItemDescription}
                onChange={(e) => setNewItemDescription(e.target.value)}
                placeholder="Item Description"
            />
            <button onClick={handleAddItem}>Add Item</button>
        </div>
    );
}

export default ItemList;
