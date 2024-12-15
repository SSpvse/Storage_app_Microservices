import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Item } from "../types/Item"; // Importing the correct Item type

const ITEM_SERVICE_URL = "http://localhost:8000/item";

// Function to fetch all items
export const fetchAllItems = async (): Promise<Item[]> => {
    try {
        const response = await fetch(`${ITEM_SERVICE_URL}/getall`);
        if (!response.ok) {
            throw new Error("Failed to fetch items");
        }
        const data = await response.json();
        return data;
    } catch (error) {
        console.error("Error fetching items:", error);
        return [];
    }
};

const Search = () => {
    const [query, setQuery] = useState<string>('');
    const [items, setItems] = useState<Item[]>([]);
    const [selectedItem, setSelectedItem] = useState<Item | null>(null);

    const navigate = useNavigate();

    useEffect(() => {
        const getItems = async () => {
            const itemsData = await fetchAllItems();
            setItems(itemsData);
        };

        getItems();
    }, []);

    // Filter items based on the search query
    const filteredItems = items.filter(item =>
        item.name.toLowerCase().includes(query.toLowerCase())
    );

    const handleItemClick = (item: Item) => {
        setSelectedItem(item);
        // Navigate to the item's detailed page
        navigate(`/item/${item.id}`);

    };

    return (
        <div>
            <h2>Search through all your items</h2>
            <div className={"searchingInputBox"}>
                {/* Search box */}
                <input
                    type="text"
                    placeholder="Search..."
                    value={query}
                    onChange={(e) => setQuery(e.target.value)}
                />
            </div>


            {/* List of matching items */}
            <ul>
                {filteredItems.map(item => (
                    <li key={item.id} onClick={() => handleItemClick(item)}>

                        {item.name}
                    </li>
                ))}
            </ul>

            {/* Display selected item details */}
            {selectedItem && (
                <div>
                    <h2>{selectedItem.name}</h2>
                    <p>{selectedItem.description}</p>
                    <p>Unit ID: {selectedItem.unitID}</p>
                    <p>Quantity: {selectedItem.quantity}</p>
                    <p>Date: {selectedItem.date}</p>
                    <p>User ID: {selectedItem.userID}</p>
                </div>
            )}
        </div>
    );
};

export default Search;
