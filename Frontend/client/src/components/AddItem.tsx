import {useEffect, useState} from "react";
import { addItem } from "../services/itemService.tsx";
import { NewItem } from "../types/NewItem.tsx";
import {useNavigate, useParams} from "react-router-dom";
import {fetchUnitById} from "../services/UnitService.tsx";
import {useUser} from "../types/UserContext.tsx";

const itemTypes = {
    food: "food",
    other: "other",
    clothes: "clothes",
};
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
    const [itemType, setItemType] = useState ('');
    const [quantity, setQuantity] = useState('');
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();

    const {id: user_id} = useUser();

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

        if (!name || !description || !itemType) {

            setError("Name, Description and type are required.");
            return;
        }
        console.log("-----Item Type before submission:", itemType);
        console.log("------Item User id:", user_id);

        const newItem: NewItem = {
            name,
            description,
            date,
            quantity: quantity? parseInt(quantity):0,
            unitID: unitIdNumber,
            userID: user_id,
            type: itemType,
        };
        console.log("New Item:", newItem);
        try {
            const savedItem = await addItem(newItem);
            onItemAdded(savedItem);
            alert('Item added successfully');
            navigate(`/unit/${unitId}`);

            setName('');
            setDescription('');
            setDate('');
            setQuantity('');
            setItemType('');
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
                                    <label className="label">Expiry Date:</label>
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

                        <div className="form-group">
                            <label className="label">Item Type:</label>
                            <select
                                value={itemType}
                                onChange={(e) => setItemType(e.target.value)}
                                required
                                className="input"

                            >
                                <option value="" disabled>
                                    Select item type
                                </option>
                                {unitType === "refrigerator" ? (
                                    <>
                                        // Only 'food' option for refrigerator
                                        <option value={itemTypes.food}>Food</option>
                                        <option value={itemTypes.other}>Other</option>
                                    </>
                                ) : (
                                    <>
                                        {/* For non-refrigerator units, show these types */}
                                        <option value={itemTypes.clothes}>Clothes</option>
                                        <option value={itemTypes.other}>Other</option>
                                    </>
                                )}
                            </select>
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
