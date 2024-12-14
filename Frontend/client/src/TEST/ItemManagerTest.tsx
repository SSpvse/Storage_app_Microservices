import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import {fetchItemsByUnitId, addItem, fetchAllItems} from "../services/itemService";
import { Item } from "../types/Item";
import AddItem from "./AddItem.tsx";

const ItemManager = () => {
    const { unitId } = useParams<{ unitId: string }>();
    const [items, setItems] = useState<Item[]>([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const handleFetchItems = async () => {
            try {
                setLoading(true);
                setError(null);
                const fetchedData = await fetchItemsByUnitId(Number(unitId));

                // Ensuring the response has items and unitType before updating state
                if (fetchedData && Array.isArray(fetchedData)) {
                    setItems(fetchedData);
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
            //const savedItem = await addItem(newItem);
            setItems((prevItems) => [...prevItems, newItem]); // TODO TIdligere var det savedItems og ikke newItem
            setError(null);
        } catch (err) {
            console.error("Failed to add item:", err);
            setError("Failed to add idem. Please try again");
        }
    };

    return (
        <div className="item-manager">
            <h3>Items in this Unit {unitId}</h3>
            {loading && <p className="loading-message">Loading...</p>}
            {error && <p className="error-message">{error}</p>}

            <div className="item-list">
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

            <AddItem unitId={unitId} onItemAdded={handleItemAdded} />
        </div>
    );
};

export default ItemManager;
