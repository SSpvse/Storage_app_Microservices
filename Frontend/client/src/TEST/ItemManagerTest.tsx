import React, { useEffect, useState } from "react";
import {useNavigate, useParams} from "react-router-dom";
import {fetchItemsByUnitId, addItem, fetchAllItems} from "../services/itemService";
import { Item } from "../types/Item";
import AddItem from "./AddItem.tsx";
import clothingIcon from "../assets/clothing.png";
import foodIcon from "../assets/food.png";
import thingIcon from "../assets/thing.png";

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
                    console.log("FetchedDaTTTA itemmanagertest:::" + fetchedData);
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



    const handleItemClick = async (item) => {
        console.log("Clicked item:", item); // Log the item to check if itemId exists
        navigate(`/item/${item.id}`);
    };

    const renderIcon = (type: string) => {
        switch (type.toLowerCase()) {
            case 'clothes':
                return <img src={clothingIcon} alt="Clothing" className="item-detail-icon" />;
            case 'food':
                return <img src={foodIcon} alt="Food" className="item-detail-icon" />;
            case 'thing':
                return <img src={thingIcon} alt="Thing" className="item-detail-icon" />;
            default:
                return <img src={thingIcon} alt="Other" className="item-detail-icon" />;
        }
    };

    return (
        <div className="item-manager">
            <h3 className="title-items-in-unit">Your items in chosen Unit {unitId}</h3>
            {loading && <p className="loading-message">Loading...</p>}
            {error && <p className="error-message">{error}</p>}

            <div className="item-list">
                {items.length > 0 ? (
                    <div className="items-container">
                        {items.map((item) => (
                            <div
                                key={item.id}

                                className="item-box"
                                onClick={() => handleItemClick(item)}
                            >

                                <h3 className="item-name">{item.name}</h3>
                                <div className="render-icon">{renderIcon(item.type)}</div>

                            </div>
                        ))}
                        <div className={"add-new-item-box"}>
                            <AddItem unitId={unitId} onItemAdded={handleItemAdded} />
                        </div>
                    </div>
                ) : (
                    <p className="itemsAvailable">No items available in this unit.</p>
                )}
            </div>

        </div>
    );
};

export default ItemManager;
