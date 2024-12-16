
// Define an interface for the new item structure
export interface NewItem {
    id?: number;
    name: string;
    description: string;
    quantity?: number;
    date?: string
    unitID: number;
    userID: number;
    type: string;
}