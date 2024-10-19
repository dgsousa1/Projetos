import { IVehicleTypePersistence } from '../../dataschema/IVehicleTypePersistence';
import mongoose from 'mongoose';

/**
 * Creates new VehicleTypeSchema
 */
const VehicleTypeSchema = new mongoose.Schema(
    {
        vehicleTypeId: {type: String, unique: true},
        name: {type: String, unique: true},
        fuelType: {type: String},
        autonomy: {type: Number},
        costPerKilometer: {type: Number},
        consumption: {type: Number},
        averageSpeed: {type: Number},
        description: {type: String},
        emission: {type: Number},
    },
    {
        timestamps: true
    }
);

export default mongoose.model<IVehicleTypePersistence & mongoose.Document>('VehicleType', VehicleTypeSchema);
