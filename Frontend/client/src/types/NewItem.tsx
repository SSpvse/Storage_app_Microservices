// Define an interface for the new item structure
export interface NewItem {
    name: string;
    description: string;
    location: string | null;
    quantity: number;
    date: string;
    unitID: number;
    userID: number;
}