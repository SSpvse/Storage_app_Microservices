import { useEffect, useState } from "react";
import {useNavigate, useParams} from "react-router-dom";
import {fetchItemsByUnitId, addItem, fetchAllItems} from "../services/itemService";
import { Item } from "../types/Item";
import AddItem from "./AddItem.tsx";

const ItemManager = () => {
    const { unitId } = useParams<{ unitId: string }>();
    const [items, setItems] = useState<Item[]>([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const navigate = useNavigate();
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


    const handleItemClick = async (item)=>{
        navigate(`/item/${item.itemId}`);
    }
    return (
        <div className="item-manager">
            <h3>Your items in chosen Unit {unitId}</h3>
            {loading && <p className="loading-message">Loading...</p>}
            {error && <p className="error-message">{error}</p>}

            <div className="item-list">
                {items.length > 0 ? (
                    <div className="items-container">
                        {items.map((item) => (
                            <div
                                key={item.itemId}
                                className="item-box"
                                onClick={() => handleItemClick(item)}
                            >
                                {/* Picture <img src={item.imageUrl} alt={item.name} className="item-image" />*/}

                                {/* Name */}
                                <h3 className="item-name">{item.name}</h3>

                                {/* Description */}
                                <p className="item-description">{item.description}</p>
                            </div>
                        ))}
                    </div>
                ) : (
                    <p className="itemsAvailable">No items available in this unit.</p>
                )}
            </div>

            <AddItem unitId={unitId} onItemAdded={handleItemAdded} />
        </div>
    );
};

export default ItemManager;
