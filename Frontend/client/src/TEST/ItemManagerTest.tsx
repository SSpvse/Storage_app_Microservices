import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { fetchItemsByUnitId, addItem } from "../services/itemService";
import { Item } from "../types/Item";
import AddItem from "./AddItem.tsx";

const ItemManager = () => {
    const { unitId } = useParams<{ unitId: string }>();
    const [items, setItems] = useState<Item[]>([]);
    const [unitType, setUnitType] = useState <string>('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const handleFetchItems = async () => {
            if (!unitId) return;
            try {
                setLoading(true);
                setError(null);
                const fetchedData = await fetchItemsByUnitId(Number(unitId));

                // Ensure the response has items and unitType before updating state
                if (fetchedData && Array.isArray(fetchedData.items)) {
                    setItems(fetchedData.items);
                    setUnitType(fetchedData.unitType || "");
                } else {
                    throw new Error("Invalid data format received");
                }
            } catch (err) {
                console.error("Failed to fetch items", err);
            } finally {
                setLoading(false);
            }
        };

        handleFetchItems();
    }, [unitId]);

    const handleItemAdded = async (newItem: Item) => {
        try {
            const response = await fetch(`http://localhost:8000/item/units/${unitId}/items`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(newItem),
            });

            if (!response.ok) {
                throw new Error("Failed to add item to backend.");
            }

            const savedItem = await response.json();
            setItems((prevItems) => (Array.isArray(prevItems) ? [...prevItems, newItem] : [newItem]));
        } catch (err) {
            console.error("Failed to add item:", err);
            setError("Failed to add idem. Please try again");
        }
    };

    return (
        <div>
            <h3>Items in Selected Unit {unitId}</h3>
            {loading && <p>Loading...</p>}
            {error && <p style={{ color: "red" }}>{error}</p>}
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
            <AddItem unitId={Number(unitId)} unitType={unitType} onItemAdded={handleItemAdded} />
        </div>
    );
};

export default ItemManager;
