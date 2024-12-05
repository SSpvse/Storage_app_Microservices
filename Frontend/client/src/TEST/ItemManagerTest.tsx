import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { fetchItemsByUnitId, addItem } from "../services/itemService";
import { Item } from "../types/Item";
import { NewItem } from "../types/NewItem";

const ItemManager = () => {
    const { unitId } = useParams<{ unitId: string }>();
    const [items, setItems] = useState<Item[]>([]);
    const [newItemName, setNewItemName] = useState("");
    const [newItemDescription, setNewItemDescription] = useState("");
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        const handleFetchItems = async () => {
            if (!unitId) return;
            try {
                setLoading(true);
                const fetchedItems = await fetchItemsByUnitId(Number(unitId));
                setItems(fetchedItems);
            } catch (err) {
                console.error("Failed to fetch items", err);
            } finally {
                setLoading(false);
            }
        };

        handleFetchItems();
    }, [unitId]);

    const handleAddItem = async () => {
        if (!newItemName || !newItemDescription) return;

        const newItem: NewItem = {
            name: newItemName,
            description: newItemDescription,
            location: null,
            quantity: 1,
            date: new Date().toISOString(),
            unitID: Number(unitId),
            userID: 1 // Replace with actual user ID
        };

        try {
            const addedItem = await addItem(newItem);
            if (addedItem.itemId) setItems((prevItems) => [...prevItems, addedItem]);
            setNewItemName("");
            setNewItemDescription("");
        } catch (err) {
            console.error("Failed to add the item", err);
        }
    };

    return (
        <div>
            <h3>Items in Selected Unit {unitId}</h3>
            {loading && <p>Loading...</p>}
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
};

export default ItemManager;
