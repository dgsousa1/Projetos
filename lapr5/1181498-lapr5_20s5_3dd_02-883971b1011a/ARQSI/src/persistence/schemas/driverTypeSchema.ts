import { IDriverTypePersistence } from '../../dataschema/IDriverTypePersistence';
import mongoose from 'mongoose';

/**
 * Creates new DriverTypeSchema
 */
const DriverTypeSchema = new mongoose.Schema(
    {
        driverTypeId: {type: String, unique: true},
        name: {type: String, unique: true},
        description: {type: String}
    },
    {
        timestamps: true
    }
);

export default mongoose.model<IDriverTypePersistence & mongoose.Document>('DriverType', DriverTypeSchema);