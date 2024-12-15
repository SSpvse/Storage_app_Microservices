import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { fetchItemById, deleteItem } from "../services/itemService";
import { Item } from "../types/Item";

const ItemDetail = () => {
    const { id } = useParams <{id: string}>(); // Getting item numericId from URL as string
    const navigate = useNavigate();
    const [item, setItem] = useState<Item | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    console.log('Received id from URL:', id);
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
                    console.log("FETCHED ITEM BY IDDD::", numericId);
                    const fetchedItem = await fetchItemById(numericId); // Getting item based on numericId
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
        switch (type) {
            case 'Clothing':
                return <i className="fa fa-tshirt icon clothing"></i>; // Clothing icon
            case 'Food':
                return <i className="fa fa-apple-alt icon food"></i>; // Food icon
            case 'Thing':
                return <i className="fa fa-cogs icon other"></i>; // Default icon for "Other"
        }
    };

    if (loading) return <p>Loading...</p>;
    if (error) return <p style={{ color: "red" }}>{error}</p>;

    return (
        <div className="item-detail">
            <h2>Item Details</h2>
            {item ? (
                <>
                    <div className="item-header">
                        <h3>{item.name}</h3>
                        <div className="item-icon">
                            {renderIcon(item.type)}
                        </div>
                    </div>
                    <p><strong>Description:</strong> {item.description}</p>
                    <p><strong>Quantity:</strong> {item.quantity}</p>
                    <p>Type: {item.type}</p>
                    <button className="delete-btn" onClick={handleDelete}>Delete Item</button>

                </>
            ) : (
                <p>Item not found.</p>
            )}
        </div>
    );
};

export default ItemDetail;
