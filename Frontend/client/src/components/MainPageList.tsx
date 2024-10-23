import { useState } from "react";
import ItemManager from "./itemManager.tsx";
import UnitManager from "./UnitManager.tsx"; // Assuming ItemList is imported from the correct path

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

    const handleUnitDeselected = () => {
        setSelectedUnitId(null); // Reset selected unit
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
                        <ItemManager
                            selectedUnit={selectedUnitId}
                            onUnitDeselected={handleUnitDeselected}
                        />
                    )}
                </div>

            )}
        </div>
    );
}

export default MainPage;
