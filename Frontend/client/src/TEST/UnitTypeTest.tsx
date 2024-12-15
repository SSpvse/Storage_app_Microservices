// UnitType.tsx or within the same file as UnitManager

import fridgeImage from "../assets/fridge.png";
import boxImage from "../assets/box.png";
import closetImage from "../assets/closet.png";
import React from "react";

interface UnitTypeTestProps {
    unit: {
        id: number;
        type: string;
        name: string;
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
        <div
            onClick={() => onSelect(unit.id)}
            style={{
                cursor: 'pointer',
                display: 'flex',
                alignItems: 'center',
                padding: '10px',
                margin: '10px',
                backgroundColor: '#f4f4f4',
                borderRadius: '8px',
            }}
        >
            <img
                src={images[unit.type] || boxImage}
                alt={unit.type}
                style={{
                    width: '100px',
                    height: '100px',
                    borderRadius: '8px',
                    marginRight: '15px', // space between
                }}
            />
            <span
                style={{
                    fontSize: '18px',
                    fontWeight: 'bold',
                }}
            >
                {unit.name}
            </span>
        </div>
    );
};


export default UnitTypeTest;
