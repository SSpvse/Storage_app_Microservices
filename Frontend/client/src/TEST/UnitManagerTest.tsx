
import {useEffect, useState} from "react";
import {Unit} from "../types/Unit.tsx";
import {fetchAllUnits} from "../services/UnitService.tsx";
import UnitTypeTest from "./UnitTypeTest.tsx";

interface UnitManagerTestProps {
    onUnitSelected: (unitId: number) => void;
}
const UnitManagerTest = ({ onUnitSelected }: UnitManagerTestProps) => {
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

    return (
        <div>
            <h2>Available Storage Units</h2>
            {loading && <p>Loading...</p>}
            {error && <p style={{ color: "red" }}>{error}</p>}
            <div>
                {unit.map((unit) => (
                    <UnitTypeTest key={unit.id} unit={unit} onSelect={onUnitSelected} />
                ))}
            </div>
        </div>
    );
};

export default UnitManagerTest;
