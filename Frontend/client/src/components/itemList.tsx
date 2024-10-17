import { useEffect, useState } from "react";
import { fetchAllItems } from "../services/itemService";


export interface Item {
    itemId: number;
    itemName: string;
    itemDescription: string;
    storageUnitId: number;
}

function ItemList() {
    const [items, setItems] = useState<Item[]>([]);

    const handleFetchItems = async () => {
        try {
            const data = await fetchAllItems() as Item[];
            setItems(data);
        } catch (err) {
            console.error("Failed to fetch items", err);
        }
    };

    return (
        <div>
            <button onClick={handleFetchItems}>Fetch all items</button>
            {items.length > 0 ? (
                <ul>
                    {items.map((item) => (
                        <li key={item.itemId}>
                            {item.itemName}: {item.itemDescription}
                        </li>
                    ))}
                </ul>
            ) : (
                <p>No items available</p>
            )}
        </div>
    );
}

export default ItemList;
