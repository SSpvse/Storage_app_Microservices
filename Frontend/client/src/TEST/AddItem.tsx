import { useState } from "react";
import { useNavigate } from "react-router-dom";
import {addItem} from "../services/itemService.tsx";

interface AddItemProps {
    unitId: number;
    unitType: string;
    onItemAdded: (newItem: any) => void;
}

const AddItem = ({ unitId, unitType, onItemAdded }: AddItemProps) => {
    const [name, setName] = useState('');
    const [description, setDescription] = useState('');
    const [date, setDate] = useState('');
    const [quantity, setQuantity] = useState('');
    //const [unitType, setUnitType] = useState('');
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();


    const handleAddItem = async (e: React.FormEvent) => {
        e.preventDefault();

        if (!name || !description) {
            setError("Name and Description are required.");
            return;
        }

        if (unitType === "fridge" && (!date || !quantity)) {
            setError("Date and Quantity are required for fridge items.");
            return;
        }

        const newItem = {
            name,
            description,
            date: unitType === 'fridge' ? date : undefined, // Add date only if the type is fridge
            quantity: unitType === 'fridge' ? quantity : undefined, // Add quantity only if the type is fridge
            unitId,
            type: unitType,
        };

        try {
            //await addItem(newItem);
            onItemAdded(newItem);
            alert('Item added successfully');
            navigate(`/unit/${unitId}`);
            setName('');
            setDescription('');
            setDate('');
            setQuantity('');
        } catch (err) {
            setError('Failed to add item');
            console.error('Error adding item:', err);
        }
    };

    return (
        <div>
            <h2>Add a new item</h2>
            <form onSubmit={handleAddItem}>
                <div>
                    <label>Name:</label>
                    <input
                        type="text"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        required
                    />
                </div>

                <div>
                    <label>Description:</label>
                    <input
                        type="text"
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                        required
                    />
                </div>

                {/* If type is fridge show Date and Quanity */}
                {unitType === 'fridge' && (
                    <>
                        <div>
                            <label>Date:</label>
                            <input
                                type="date"
                                value={date}
                                onChange={(e) => setDate(e.target.value)}
                                required
                            />
                        </div>

                        <div>
                            <label>Quantity:</label>
                            <input
                                type="number"
                                value={quantity}
                                onChange={(e) => setQuantity(e.target.value)}
                                required
                            />
                        </div>
                    </>
                )}

                <button type="submit">Add Item</button>
            </form>

            {error && <p style={{ color: "red" }}>{error}</p>}
        </div>
    );
};

export default AddItem;
