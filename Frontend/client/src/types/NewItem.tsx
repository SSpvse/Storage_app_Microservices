
// Define an interface for the new item structure
export interface NewItem {
    name: string;
    description: string;
    quantity?: number;
    date?: string
    unitId: number;
    userId: number;
}