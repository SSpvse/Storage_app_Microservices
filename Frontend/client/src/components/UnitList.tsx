import { Unit } from "../types/Unit"; // Ensure correct import path
import { addUnit, fetchAllUnits } from "../services/UnitService";
import { useEffect, useState } from "react";

interface UnitManagerProps {
    onUnitSelected: (unitId: number) => void;
}

const UnitManager = ({ onUnitSelected }) => {
    const [units, setUnits] = useState<Unit[]>([]);
    const [newUnitName, setNewUnitName] = useState("");

    useEffect(() => {
        const fetchUnits = async () => {
            const fetchedUnits = await fetchAllUnits();
            setUnits(fetchedUnits);
        };

        fetchUnits();
    }, []);

    const handleCreateUnit = async () => {
        if (!newUnitName) return;

        const newUnit = await addUnit({ name: newUnitName });
        setUnits((prevUnits) => [...prevUnits, newUnit]);
        setNewUnitName(""); // Clear input
    };

    return (
        <div>
            <h2>Available Storage Units</h2>
            <div>
                {units.map((unit) => (
                    <UnitCard
                        key={unit.unitId}
                        unit={unit}
                        onSelect={() => onUnitSelected(unit.unitId)} // Call back to select unit
                    />
                ))}
            </div>
            <div>
                <h3>Create New Unit</h3>
                <input
                    type="text"
                    value={newUnitName}
                    onChange={(e) => setNewUnitName(e.target.value)}
                    placeholder="Unit Name"
                />
                <button onClick={handleCreateUnit}>Create Unit</button>
            </div>
        </div>
    );
}

function UnitCard({ unit, onSelect }) {
    const images = {
        refrigerator: "/assets/fridge.png",
        box: "/images/box.png",
        closet: "/images/closet.png",
    };

    return (
        <div onClick={onSelect} style={{ cursor: "pointer", display: 'inline-block', margin: '10px' }}>
            <img src={images[unit.type]} alt={unit.name} style={{ width: "100px", height: "100px" }} />
            <h4>{unit.name}</h4>
        </div>
    );
}

export default UnitManager;
