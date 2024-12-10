import { useState } from "react";
import { useNavigate } from "react-router-dom";
import UnitManagerTest from "./UnitManagerTest.tsx";

const MainPage = () => {
    //const [viewingUnits, setViewingUnits] = useState(false);
    const navigate = useNavigate();
/*
    const handleViewUnits = () => {
        setViewingUnits(true);
    };

    const handleUnitSelection = (unitId: number) => {
        navigate(`/unit/${unitId}`); // Navigate to ItemManager with unitId in the URL
    };
*/
    const handleViewUnits = () => {
        navigate("/my-storage");
    }
    const handleAddUnit = () => {
        navigate("/add-unit");
    }
    return (
        <div>
            <h1>Add a unit</h1>
            <button onClick={handleAddUnit}></button>
            <h1>or see "My Storage"</h1>
            <button onClick={handleViewUnits}>See My Storage</button>

        </div>

    );
};

export default MainPage;
