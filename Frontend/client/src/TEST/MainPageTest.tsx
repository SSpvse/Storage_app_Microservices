import { useState } from "react";
import { useNavigate } from "react-router-dom";
import UnitManagerTest from "./UnitManagerTest.tsx";

const MainPage = () => {
    const [viewingUnits, setViewingUnits] = useState(false);
    const navigate = useNavigate();

    const handleViewUnits = () => {
        setViewingUnits(true);
    };

    const handleUnitSelection = (unitId: number) => {
        navigate(`/unit/${unitId}`); // Navigate to ItemManager with unitId in the URL
    };

    return (
        <div>
            <h1>My Storage Management</h1>
            {viewingUnits ? (
                <UnitManagerTest onUnitSelected={handleUnitSelection} />
            ) : (
                <div>
                    <button onClick={handleViewUnits}>See My Storage</button>
                </div>
            )}
        </div>
    );
};

export default MainPage;
