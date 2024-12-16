import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { fetchItemById, deleteItem } from "../services/itemService";
import { Item } from "../types/Item";
import 'font-awesome/css/font-awesome.min.css';
import clothingIcon from "../assets/clothing.png";
import foodIcon from "../assets/food.png";
import thingIcon from "../assets/thing.png";

const ItemDetail = () => {
    const { id } = useParams <{id: string}>(); // Getting item numericId from URL as string
    const navigate = useNavigate();
    const [item, setItem] = useState<Item | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    console.log('Received id from URL.ITEMDETAIL {}:', id);
    const numericId = Number(id); //Convering numericId string to number

    useEffect(() => {
        const getItemDetails = async () => {
            console.log('numericId:', numericId);
            if (isNaN(numericId)) {
                setError("Invalid item ID");
                setLoading(false);
                return;
            }
            try {
                setLoading(true);
                if (numericId) {
                    console.log("FETCHED ITEM BY IDDD: {}", numericId);
                    const fetchedItem = await fetchItemById(numericId); // Getting item based on numericId
                    console.log("This is the fetchedItem in itemDetail.tsx" + fetchedItem.name)
                    console.log("This is the type of fetchedITem in itemDetail.tsx" + fetchedItem.type);

                    setItem(fetchedItem);
                }
            } catch (err) {
                setError("Failed to fetch item details");
            } finally {
                setLoading(false);
            }
        };

        getItemDetails();

    }, [numericId]);

    const handleDelete = async () => {
        try {
            await deleteItem(Number(numericId));
            navigate("/"); // Naviagte to homepage

        } catch (err) {
            setError("Error deleting item");
        }
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

    if (loading) return <p>Loading...</p>;
    if (error) return <p style={{ color: "red" }}>{error}</p>;

    return (
        <div className="item-detail">
            <h2>Item Details</h2>
            {item ? (
                <>
                    <div className="item-icon">
                        {renderIcon(item.type)}
                    </div>
                    <p><strong>Item name:</strong> {item.name}</p>
                    <p><strong>Description:</strong> {item.description}</p>
                    <p><strong>Quantity:</strong> {item.quantity}</p>
                    <p><strong>Type:</strong> {item.type}</p>
                    <p><strong>In Unit:</strong> {item.unitID}</p>

                    <button className="delete-btn" onClick={handleDelete}>Delete Item</button>

                </>
            ) : (
                <p>Item not found.</p>
            )}
        </div>
    );
};

export default ItemDetail;
