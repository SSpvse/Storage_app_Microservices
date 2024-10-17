const STORAGE_UNIT_SERVICE_URL = 'http://localhost:8082/storage-units';

// Define an interface for the storage unit structure
export interface StorageUnit {
    storageUnitId: number;
    storageUnitName: string;
    storageUnitDescription: string;
}

// Fetch all storage units
export const fetchAllStorageUnits = async (): Promise<StorageUnit[]> => {
    const response = await fetch(STORAGE_UNIT_SERVICE_URL);
    if (!response.ok) {
        throw new Error('Failed to fetch storage units');
    }
    return await response.json();
};
