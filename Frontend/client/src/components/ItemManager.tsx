import { useEffect, useState } from "react";
import {useNavigate, useParams} from "react-router-dom";
import {fetchItemsByUnitId} from "../services/itemService.tsx";
import { Item } from "../types/Item.tsx";
import AddItem from "./AddItem.tsx";
import clothingIcon from "../assets/clothing.png";
import foodIcon from "../assets/food.png";
import thingIcon from "../assets/thing.png";
import {NewItem} from "../types/NewItem.tsx";
import {deleteUnitById} from "../services/UnitService.tsx";

const ItemManager = () => {
    const { unitId } = useParams<{ unitId: string | undefined }>();
    const [items, setItems] = useState<Item[]>([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const navigate = useNavigate();
    // Fallback to empty string if unitId is undefined
    const unitIdString: string = unitId ?? "";
    // Converting unitIdString to number
    const unitIdNumber = unitIdString ? Number(unitIdString) : NaN;

    useEffect(() => {
        if (isNaN(unitIdNumber)) return;
        const handleFetchItems = async () => {
            try {
                setLoading(true);
                setError(null);                                 // Number (unitId)
                const fetchedData = await fetchItemsByUnitId(unitIdNumber);

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
        // unitId
    }, [unitIdNumber]);


                                    //(newItem: Item)
    const handleItemAdded = async (newItem: NewItem) => {

        // Calculate the next ID based on existing items
        const maxId = items.reduce((max, item) => (item.id > max ? item.id : max), 0);
        const itemToAdd: Item = { ...newItem, id: maxId + 1 };
        try {
            setItems((prevItems) => [...prevItems, itemToAdd]);
            setError(null);
        } catch (err) {
            console.error("Failed to add item:", err);
            setError("Failed to add idem. Please try again");
        }
    };

    const handleDeleteUnit = async () => {
        console.log("INSIDE HandleDelete")
        if (!unitIdNumber || isNaN(unitIdNumber)){
            setError("Invalid unit ID. Please provide a valid number.")
            return;
        }

        try {
            console.log("Am I HERE??" + unitIdNumber)
            await deleteUnitById(unitIdNumber);
            navigate("/"); // Redirect to the main page or units list after deletion

        } catch (err) {
            setError("Failed to delete unit. Please try again.");
        }
    };


    const handleItemClick = async (item:Item) => {
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

            <h3 className="title-items-in-unit"><button className="delete-unit-btn" onClick={handleDeleteUnit}>
                Delete Unit
            </button>Your items in chosen Unit {unitId}</h3>

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

                    </div>
                ) : (
                    <p className="itemsAvailable">No items available in this unit.</p>
                )}
            </div>
            <div className={"add-new-item-box"}>
                <AddItem unitId={unitId || ""} onItemAdded={handleItemAdded} />
            </div>

        </div>
    );
};

export default ItemManager;
