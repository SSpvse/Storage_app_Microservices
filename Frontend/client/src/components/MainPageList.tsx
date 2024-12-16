/*
import { useState } from "react";
import ItemManager from "./itemManager.tsx";
import UnitManager from "./UnitManager.tsx";

const MainPage = () => {
    const [selectedUnitId, setSelectedUnitId] = useState<number | null>(null);
    const [viewingUnits, setViewingUnits] = useState(false);

    const handleViewUnits = () => {
        setViewingUnits(true);
        console.log("Viewing units list")
    }
    const handleUnitSelection = (unitId?: number) => {
        setSelectedUnitId(unitId?? null);
        setViewingUnits(false); // Hiding unit list when a unir is selcted
        console.log(unitId? `Selceted unit: ${unitId}` : "Deselected unit" );
    };



    return (
        <div>
            <h1>My Storage Management</h1>
            {viewingUnits ? (
                <UnitManager onUnitSelected={handleUnitSelection} />
            ) : (
                <div>
                    <button onClick={handleViewUnits}>See My Storage</button>
                    {selectedUnitId && (
                        <ItemManager
                            selectedUnit={selectedUnitId}
                            onUnitDeselected={() => handleUnitSelection(undefined)}
                        />
                    )}
                </div>

            )}
        </div>
    );
}

export default MainPage;
*/