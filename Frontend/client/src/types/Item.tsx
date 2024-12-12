export interface Item {
    itemId: number;
    name: string;
    description: string;
    quantity: number;
    date: string;
    unitId: number; // ID of the storage unit the item belongs to
   // userID: number; // ID of the user who added the item
}

