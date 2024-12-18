
import {useEffect, useState} from "react";
import {Unit} from "../types/Unit.tsx";
import {fetchAllUnits} from "../services/UnitService.tsx";
import UnitType from "./UnitType.tsx";
import {useNavigate} from "react-router-dom";
/*
interface UnitManagerTestProps {
    onUnitSelected: (unitId: number) => void;
}
*/

const UnitManager = () => {
    const navigate = useNavigate();
    const [unit, setUnit] = useState<Unit[]>([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const fetchUnits = async () => {
            try {
                setLoading(true);
                const fetchedUnits = await fetchAllUnits();
                setUnit(fetchedUnits);
            } catch (err) {
                setError("Failed to fetch unit");
                console.error("Error fetching unit:", err);
            } finally {
                setLoading(false);
            }
        };
        fetchUnits();
    }, []);


    const onUnitSelected = (unitId: number) => {
        navigate(`/unit/${unitId}`);
    };
    return (
        <div className={"unit-manager"}>
            <h2>Available Storage Units</h2>
            {loading && <p>Loading...</p>}
            {error && <p style={{ color: "red" }}>{error}</p>}
            <div className="unit-list">
                {unit.map((unit) => (
                    <UnitType key={unit.id} unit={unit} onSelect={onUnitSelected} />
                ))}
            </div>
        </div>
    );
};

export default UnitManager;
