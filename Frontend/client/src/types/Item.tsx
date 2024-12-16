export interface Item {
    id: number;
    name: string;
    description: string;
    quantity: number;
    date: string;
    unitID: number; // ID of the storage unit the item belongs to
    userID: number; // ID of the user who added the item
    type: string;

}

