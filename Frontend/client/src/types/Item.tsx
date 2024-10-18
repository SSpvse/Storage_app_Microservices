export interface Item {
    itemId: number;
    name: string;
    description: string;
    location: string | null;
    quantity: number;
    date: string;
    unitID: number; // ID of the storage unit the item belongs to
    userID: number; // ID of the user who added the item
}

