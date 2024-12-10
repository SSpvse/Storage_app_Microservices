// UnitType.tsx or within the same file as UnitManager

import fridgeImage from "../assets/fridge.png";
import boxImage from "../assets/box.png";
import closetImage from "../assets/closet.png";
import React from "react";

interface UnitTypeTestProps {
    unit: {
        id: number;
        type: string;
    };
    onSelect: (unitId: number) => void;
}

const UnitTypeTest: React.FC<UnitTypeTestProps> = ({ unit, onSelect }) => {
    const images: Record<string, string> = {
        refrigerator: fridgeImage,
        box: boxImage,
        closet: closetImage,
    };

    return (
        <div onClick={() => onSelect(unit.id)} style={{ cursor: 'pointer', padding: '10px', margin: '10px' }}>
            <img src={images[unit.type] || boxImage} style={{ width: '100px', height: '100px' }} />

        </div>
    );
};


export default UnitTypeTest;
