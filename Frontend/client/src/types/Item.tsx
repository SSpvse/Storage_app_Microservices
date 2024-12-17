import {NewItem} from "./NewItem.tsx";
/*
export interface Item {

    id: number;
    name: string;
    description: string;
    quantity: number;
    date: string;
    unitID: number; // ID of the storage unit the item belongs to
    userID: number; // ID of the user who added the item
    type: string;

}*/

export interface Item extends NewItem {
    id: number; // ID added after creation
}
