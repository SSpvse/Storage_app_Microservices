import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { addItem } from "../services/itemService";
import { NewItem } from "../types/NewItem";

interface AddItemProps {
    unitId: number;
    onItemAdded: (newItem: any) => void;
}

const AddItem = ({ unitId, onItemAdded }: AddItemProps) => {
    const [name, setName] = useState('');
    const [description, setDescription] = useState('');
    const [date, setDate] = useState('');
    const [quantity, setQuantity] = useState('');
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();

    const handleAddItem = async (e: React.FormEvent) => {
        e.preventDefault();

        if (!name || !description) {
            setError("Name and Description are required.");
            return;
        }

        const newItem: NewItem = {
            name,
            description,
            date,
            quantity: quantity ? Number(quantity) : undefined,
            unitId,
            userId: 1,
        };

        try {
            const savedItem = await addItem(newItem);
            onItemAdded(savedItem);
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
        <div className="min-h-screen">
            <div className="container">
                <div className="form-container">
                    <h2 className="form-title">Add a new item</h2>
                    <form onSubmit={handleAddItem} className="form">
                        <div className="form-group">
                            <label className="label">Name:</label>
                            <input
                                type="text"
                                value={name}
                                onChange={(e) => setName(e.target.value)}
                                required
                                className="input"
                            />
                        </div>

                        <div className="form-group">
                            <label className="label">Description:</label>
                            <input
                                type="text"
                                value={description}
                                onChange={(e) => setDescription(e.target.value)}
                                required
                                className="input"
                            />
                        </div>

                        <div className="form-group">
                            <label className="label">Date:</label>
                            <input
                                type="date"
                                value={date}
                                onChange={(e) => setDate(e.target.value)}
                                required
                                className="input"
                            />
                        </div>

                        <div className="form-group">
                            <label className="label">Quantity:</label>
                            <input
                                type="number"
                                value={quantity}
                                onChange={(e) => setQuantity(e.target.value)}
                                required
                                className="input"
                            />
                        </div>

                        <button type="submit" className="btn-primary">
                            Add Item
                        </button>
                    </form>

                    {error && <p className="error-message">{error}</p>}
                </div>
            </div>
        </div>
    );
};

export default AddItem;
