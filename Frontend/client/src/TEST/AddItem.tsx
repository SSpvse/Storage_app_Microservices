import {useEffect, useState} from "react";

import { addItem } from "../services/itemService";
import { NewItem } from "../types/NewItem";
import {useNavigate, useParams} from "react-router-dom";
import {fetchUnitById} from "../services/UnitService.tsx";

interface AddItemProps {
    unitId: string;
    onItemAdded: (newItem: NewItem) => void;
}

const AddItem = ({ unitId, onItemAdded }: AddItemProps) => {
    const { unitId: paramUnitId } = useParams<{ unitId: string }>();
    const [name, setName] = useState('');
    const [description, setDescription] = useState('');
    const [date, setDate] = useState('');
    const [unitType, setUnitType] = useState<string | null>(null);
    const [quantity, setQuantity] = useState('');
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();

    console.log("UNIT ID FROM URRRL", unitId);
    // Converting the unitId from url from string to number
    const unitIdNumber = paramUnitId ? parseInt(paramUnitId) : NaN;

    useEffect(() => {

        const fetchUnitType = async () => {
            try{
                if (!unitId){
                    throw new Error("Unit ID is missing");
                }
                const unit = await fetchUnitById(unitIdNumber);
                console.log("Fetched Unit Type:", unit);
                setUnitType(unit.type);
            } catch (err){
                console.error("Error fetching unit:", err);
                setError("Failed to fetch unit info")
            }
        };
        fetchUnitType();
    }, [unitIdNumber]);
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
            quantity: quantity? parseInt(quantity):0,
            unitId: unitIdNumber,
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
                            <label className="label">Quantity:</label>
                            <input
                                type="number"
                                value={quantity}
                                onChange={(e) => setQuantity(e.target.value)}
                                required
                                className="input"
                            />
                        </div>
                        {unitType === "refrigerator" && (
                            <>
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

                            </>
                        )}

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
