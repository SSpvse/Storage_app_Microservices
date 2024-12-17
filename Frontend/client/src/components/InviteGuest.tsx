import { useEffect, useState } from "react";
import { Unit } from "../types/Unit.tsx";
import {fetchAllUnits, inviteGuest, InviteGuestDTO} from "../services/UnitService.tsx";
import {useUser} from "../types/UserContext.tsx";

const InviteGuest = () => {
    const [units, setUnits] = useState<Unit[]>([]);
    const [selectedUnits, setSelectedUnits] = useState<number[]>([]);
    const [guestEmail, setGuestEmail] = useState<string>("");
    const [error, setError] = useState<string | null>(null);
    const [successMessage, setSuccessMessage] = useState<string | null>(null);
    const { id: ownerID } = useUser();

    useEffect(() => {
        const fetchUnits = async () => {
            try {
                const myUnits = await fetchAllUnits(); // Henter enheter fra backend
                setUnits(myUnits);
            } catch (err) {
                console.error("Failed to fetch units:", err);
                setError("Failed to load units. Please try again later.");
            }
        };
        fetchUnits();
    }, []);

    const handleUnitSelection = (unitId: number) => {
        setSelectedUnits((prev) =>
            prev.includes(unitId)
                ? prev.filter((id) => id !== unitId) // Fjern hvis allerede valgt
                : [...prev, unitId] // Legg til hvis ikke valgt
        );
    };
    const handleInvite = async () => {
        setError(null);
        setSuccessMessage(null);

        if (!guestEmail) {
            setError("Please enter a valid email address.");
            return;
        }
        if (selectedUnits.length === 0) {
            setError("Please select at least one unit.");
            return;
        }

        try {
            for (const unitId of selectedUnits) {
                const inviteData: InviteGuestDTO = {
                    unitID: unitId,
                    ownerID: ownerID,
                    guestEmail: guestEmail,
                    role: "GUEST",
                };

                await inviteGuest(inviteData);
            }
            setSuccessMessage("Guest invited successfully!");
            setSelectedUnits([]);
            setGuestEmail("");
        } catch (err) {
            console.error("Failed to invite guest:", err);
            setError("Failed to send invitation. Please try again.");
        }
    };

    return (
        <div className="invite-guest">
            <h2>Invite a Guest to Your Units</h2>

            {error && <p className="error-message">{error}</p>}
            {successMessage && <p className="success-message">{successMessage}</p>}

            <div className="guest-email-input">
                <label htmlFor="guestEmail">Guest Email:</label>
                <input
                    type="email"
                    id="guestEmail"
                    value={guestEmail}
                    onChange={(e) => setGuestEmail(e.target.value)}
                    placeholder="Enter guest's email"
                />
            </div>

            <div className="units-list">
                <h3>Select Units:</h3>
                {units.map((unit) => (
                    <div key={unit.id} className="unit-item">
                        <label>
                            <input
                                type="checkbox"
                                checked={selectedUnits.includes(unit.id)}
                                onChange={() => handleUnitSelection(unit.id)}
                            />
                            {unit.name}
                        </label>
                    </div>
                ))}
            </div>

            <button onClick={handleInvite} className="invite-button">
                Send Invitation
            </button>
        </div>
    );
};

export default InviteGuest;