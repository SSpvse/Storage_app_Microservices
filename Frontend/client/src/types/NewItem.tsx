
// Define an interface for the new item structure
export interface NewItem {
    name: string;
    description: string;
    quantity?: number | null;
    date: string // | LocalDate;
    unitId: number;
    //unitType: string;
    userId: 1;
}