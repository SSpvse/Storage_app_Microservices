import { Unit } from "../types/Unit";
import { addUnit, fetchAllUnits } from "../services/UnitService";
import { useEffect, useState } from "react";
import fridgeImage from "../assets/oldfridge.png";
import boxImage from "../assets/oldbox.png";
import closetImage from "../assets/oldcloset.png";
import {Item} from "../types/Item.tsx";
import {addItemToUnit, fetchItemsByUnitId} from "../services/itemService.tsx";
import {NewItem} from "../types/NewItem.tsx";
import ItemManager from "./itemManager.tsx";
/*
interface UnitManagerProps {
    onUnitSelected: (unitId?: number) => void;
}

const UnitManager = ({ onUnitSelected }: UnitManagerProps) => {
    const [units, setUnits] = useState<Unit[]>([]);
    const [newUnitName, setNewUnitName] = useState("");
    // Tracking unit that is selected by the user
    const [selectedUnitId, setSelectedUnitId] = useState<number | null>(null);
    // Tracking the items for the selectd unit
    const [items, setItems] = useState<Item[]>([]);
    //const [newItemName, setNewItemName] = useState("");
    //const [newItemDescription, setNewItemDescription] = useState("");
    // For loading message
    const [loading, setLoading] = useState<boolean>(false);
    // For error message
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        // Fetchng units
        const fetchUnits = async () => {
            try {
                setLoading(true);
                const fetchedUnits = await fetchAllUnits();
                setUnits(fetchedUnits);
            } catch (err){
                setError("Failed to fetch units");
                console.error("Failed to fetch units", err);
            } finally {
                setLoading(false);
            }
        };

        fetchUnits();
    }, []);


    // Fetch items with unit ID when a unit is selected
    useEffect(() => {
        if(selectedUnitId !== null){
            console.log("SelectedUNITTTT:::", selectedUnitId);

        }
    }, [selectedUnitId]);

    const handleUnitSelection = async (unitId: number) => {

        // navigate("/item/${unitId}")

        setSelectedUnitId(unitId);
        onUnitSelected(unitId); // Notify MainPage of selected unit
        try {
            setLoading(true);
            const fetchedItems = await fetchItemsByUnitId(unitId);
            console.log("Fetching items in useeffect")
            console.log(fetchedItems)
            setItems(fetchedItems);
        } catch (err) {
            setError("Failed to fetch items");
            console.error(err);
        } finally {
            setLoading(false);
        }

    };

    const handleUnitDeselection = () => {
        setSelectedUnitId(null);
        onUnitSelected(undefined); // Notify MainPage to deselect unit
        console.log("Deselected unit");
    };



    // Creating a unit
    const handleCreateUnit = async () => {
        if (!newUnitName) return;
        try {
            // TODO add later all the other fiels of unit
            const newUnit = await addUnit({ name: newUnitName });
            setUnits((prevUnits) => [...prevUnits, newUnit]);
            setNewUnitName(""); // Clear input
        } catch (err){
            setError("Failet to create new unit");
            console.error("Failed to create unit", err);
        }
    };


    return (
        <div>
            <h2>Available Storage Units</h2>
            {loading && <p>Loading...</p>}
            {error && <p style={{ color: "red" }}>{error}</p>}
            <div>
                {units.map((unit) => (
                    <UnitCard
                        key={unit.id}
                        unit={unit}
                        onSelect={() => handleUnitSelection(unit.id)}
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
            {selectedUnitId !== null && (
                <>
                    <p>Selected Unit ID in UnitManager: {selectedUnitId}</p>
                    <ItemManager unitId={selectedUnitId} />
                </>

            )}
        </div>
    );
}

function UnitCard({ unit, onSelect }) {
    const images = {
        refrigerator: fridgeImage,
        box: boxImage,
        closet: closetImage,
    };

    return (
        <div onClick={onSelect} style={{ cursor: "pointer", display: 'inline-block', margin: '10px' }}>
            <img src={images[unit.type]} style={{ width: "100px", height: "100px" }} />
            <h4>{unit.name}</h4>
        </div>
    );
}

export default UnitManager;*/
