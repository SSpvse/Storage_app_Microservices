import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { fetchItemById, deleteItem } from "../services/itemService";  // Importer deleteItem fra itemService
import { Item } from "../types/Item";  // Importer Item-typen

const ItemDetail = () => {
    const { itemId } = useParams(); // Hent itemId fra URL
    const navigate = useNavigate();
    const [item, setItem] = useState<Item | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        const getItemDetails = async () => {
            try {
                setLoading(true);
                if (itemId) {
                    const fetchedItem = await fetchItemById(Number(itemId)); // Hent item basert på itemId
                    setItem(fetchedItem);
                }
            } catch (err) {
                setError("Failed to fetch item details");
            } finally {
                setLoading(false);
            }
        };

        getItemDetails();
    }, [itemId]);

    const handleDelete = async () => {
        try {
            await deleteItem(Number(itemId)); // Kall deleteItem fra itemService
            navigate("/"); // Naviger til forsiden etter sletting
        } catch (err) {
            setError("Error deleting item");
        }
    };

    if (loading) return <p>Loading...</p>;
    if (error) return <p style={{ color: "red" }}>{error}</p>;

    return (
        <div className="item-detail">
            <h2>Item Details</h2>
            {item ? (
                <>
                    <h3>{item.name}</h3>
                    <p>{item.description}</p>
                    <button onClick={handleDelete}>Delete Item</button>
                </>
            ) : (
                <p>Item not found.</p>
            )}
        </div>
    );
};

export default ItemDetail;
