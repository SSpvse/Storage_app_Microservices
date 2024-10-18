import { useState } from "react";
import ItemList from "./ItemList";
import UnitManager from "./UnitList.tsx"; // Assuming ItemList is imported from the correct path

const MainPage = () => {
    const [selectedUnitId, setSelectedUnitId] = useState<number | null>(null);
    const [viewingUnits, setViewingUnits] = useState(false);

    const handleViewUnits = () => {
        setViewingUnits(true);
    }
    const handleUnitSelected = (unitId: number) => {
        setSelectedUnitId(unitId);
        setViewingUnits(false); // Hide unit list when a unir is selcted
    };

    return (
        <div>
            <h1>My Storage Management</h1>
            {viewingUnits ? (
                <UnitManager onUnitSelected={handleUnitSelected} />
            ) : (
                <div>
                    <button onClick={handleViewUnits}>See My Storage</button>
                    {selectedUnitId && (
                        <ItemList selectedUnit={selectedUnitId} onUnitDeselected={() => setSelectedUnitId(null)} />
                    )}
                </div>
                //<ItemList unitId={selectedUnitId} /> // Show items for the selected unit
            )}
        </div>
    );
}

export default MainPage;
