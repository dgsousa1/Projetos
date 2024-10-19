export interface IVehicleTypePersistence {
    _id: string;
    vehicleTypeId: string;
    name: string;
    fuelType: string;
    autonomy: Number;
    costPerKilometer: Number;
    consumption: Number;
    averageSpeed: Number;
}