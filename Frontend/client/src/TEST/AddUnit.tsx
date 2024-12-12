import { useState } from "react";
import { addUnit } from "../services/UnitService";
import {useNavigate} from "react-router-dom";

const AddUnit = () => {
    const [unitName, setUnitName] = useState('');
    const [unitDescription, setUnitDescription] = useState('');
    const [unitLocation, setUnitLocation] = useState('');
    const [unitType, setUnitType] = useState('');
    const [error, setError] = useState<string | null>(null);

    const navigate = useNavigate();
    const handleAddUnit = async (e: React.FormEvent) => {
        e.preventDefault();


        const newUnit = {
            name: unitName,
            description: unitDescription,
            location: unitLocation,
            type: unitType,
        };

        try {
            //await addUnit(newUnit);
            const createdUnit = await addUnit(newUnit);
            // After adding the unit, navigate to the unit detail page
            navigate(`/unit/${createdUnit.id}`); // Redirect to the detail page of the new unit
            alert('Unit added successfully');
            setUnitName('');
            setUnitDescription('');
            setUnitLocation('');
            setUnitType('');
        } catch (err) {
            setError('Failed to add unit');
            console.error('Error adding unit:', err);
        }
    };

    return (
        <div className="form-container">
            <h2 className="form-title">Add a new unit</h2>
            <form onSubmit={handleAddUnit} className="form">
                <div className="form-group">
                    <label className="label">Unit Name:</label>
                    <input
                        type="text"
                        value={unitName}
                        onChange={(e) => setUnitName(e.target.value)}
                        required
                        className="input"
                    />
                </div>
                <div className="form-group">
                    <label className="label">Unit Description:</label>
                    <input
                        type="text"
                        value={unitDescription}
                        onChange={(e) => setUnitDescription(e.target.value)}
                        required
                        className="input"
                    />
                </div>
                <div className="form-group">
                    <label className="label">Location:</label>
                    <input
                        type="text"
                        value={unitLocation}
                        onChange={(e) => setUnitLocation(e.target.value)}
                        required
                        className="input"
                    />
                </div>
                <div className="form-group">
                    <label className="label">Unit Type:</label>
                    <select
                        value={unitType}
                        onChange={(e) => setUnitType(e.target.value)}
                        required
                        className="input"
                    >
                        <option value="">Select a unit type</option>
                        <option value="refrigerator">Refrigerator</option>
                        <option value="box">Box</option>
                        <option value="closet">Closet</option>
                    </select>
                </div>
                <button type="submit" className="btn-primary">Add Unit</button>
            </form>
            {error && <p className="error-message">{error}</p>}
        </div>
    );
};

export default AddUnit;
