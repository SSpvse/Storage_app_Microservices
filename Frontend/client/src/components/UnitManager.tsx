import { Unit } from "../types/Unit";
import { addUnit, fetchAllUnits } from "../services/UnitService";
import { useEffect, useState } from "react";
import fridgeImage from "../assets/fridge.png";
import boxImage from "../assets/box.png";
import closetImage from "../assets/closet.png";
import {Item} from "../types/Item.tsx";
import {addItemToUnit, fetchItemsByUnitId} from "../services/itemService.tsx";
import {NewItem} from "../types/NewItem.tsx";

interface UnitManagerProps {
    onUnitSelected: (unitId: number) => void;
}

const UnitManager = ({ onUnitSelected }) => {
    const [units, setUnits] = useState<Unit[]>([]);
    const [newUnitName, setNewUnitName] = useState("");
    // Tracking unit that is selected by the user
    const [selectedUnit, setSelectedUnit] = useState<Unit | null>(null);
    // Tracking the items for the selectd unit
    const [items, setItems] = useState<Item[]>([]);
    const [newItemName, setNewItemName] = useState("");
    const [newItemDescription, setNewItemDescription] = useState("");
    // For loading message
    const [loading, setLoading] = useState<boolean>(false);
    // For error message
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        // Fetchng units
        const fetchUnits = async () => {
            try {
                setLoading(true);
                const fetchedUnits = await fetchAllUnits();
                setUnits(fetchedUnits);
            } catch (err){
                setError("Failed to fetch units");
                console.error("Failed to fetch units", err);
            } finally {
                setLoading(false);
            }
        };

        fetchUnits();
    }, []);

    // Fetch items with unit ID when a unit is selected
    useEffect(() => {
        if(selectedUnit){
            const fetchItems = async () => {
                try {
                    setLoading(true);
                    const fetchedItems = await fetchItemsByUnitId(selectedUnit.unitId);
                    setItems(fetchedItems);
                } catch (err) {
                    setError("Failed to fetch items");
                    console.error(err);
                } finally {
                    setLoading(false);
                }
            };
            fetchItems();
        }
    }, [selectedUnit]);

    // Handling unit selection
    const handleUnitSelection = (unit: Unit) => {
        setSelectedUnit(unit);
        onUnitSelected(unit.unitId);
    };

    // Adding a new item
    const handleAddItem = async () => {
        if (!newItemName || !selectedUnit) return;

        // Creating a new item object that matches the NewItem type
        const newItem: NewItem = {
            name: newItemName, // Set the name of the item
            description: newItemDescription, // Set the description of the item
            location: null, // Set location (optional, can be filled later)
            quantity: 1, // Set the quantity, defaulting to 1
            date: new Date().toISOString(), // Use current date in ISO format
            unitID: selectedUnit.unitId, // Use the selected unit ID, which should be a number
            userID: 1 // Replace with actual user ID if needed
        };

        try {
            const addedItem = await addItemToUnit(selectedUnit.unitId, newItem);
            setItems((prevItems) => [...prevItems, addedItem]);
            setNewItemName(""); // Clear input
            setNewItemDescription(""); // Clear description
        } catch (error) {
            console.error("Failed to add item:", error);
        }
    };

    // Creating a unit
    const handleCreateUnit = async () => {
        if (!newUnitName) return;

                                    // TODO add later all the other fiels of unit
        const newUnit = await addUnit({ name: newUnitName });
        setUnits((prevUnits) => [...prevUnits, newUnit]);
        setNewUnitName(""); // Clear input
    };

    return (
        <div>
            <h2>Available Storage Units</h2>
            {loading && <p>Loading...</p>}
            {error && <p style={{ color: "red" }}>{error}</p>}
            <div>
                {units.map((unit) => (
                    <UnitCard
                        key={unit.unitId}
                        unit={unit}
                        onSelect={() => handleUnitSelection(unit)} // Call back to select unit
                    />
                ))}
            </div>

            {selectedUnit && (
                <div>
                    <h3>Items in {selectedUnit.name}</h3>
                    <ul>
                        {items.map((item) => (
                            <li key={item.itemId}>
                                {item.name} - {item.description}
                            </li>
                        ))}
                    </ul>

                    <h3>Add New Item to {selectedUnit.name}</h3>
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
            )}

            <div>
                <h3>Create New Unit</h3>
                <input
                    type="text"
                    value={newUnitName}
                    onChange={(e) => setNewUnitName(e.target.value)}
                    placeholder="Unit Name"
                />
                <button onClick={handleCreateUnit}>Create Unit</button>
            </div>
        </div>
    );
}

function UnitCard({ unit, onSelect }) {
    const images = {
        refrigerator: fridgeImage,
        box: boxImage,
        closet: closetImage,
    };

    return (
        <div onClick={onSelect} style={{ cursor: "pointer", display: 'inline-block', margin: '10px' }}>
            <img src={images[unit.type]} style={{ width: "100px", height: "100px" }} />
            <h4>{unit.name}</h4>
        </div>
    );
}

export default UnitManager;
