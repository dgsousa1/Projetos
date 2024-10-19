import { ILinePersistence } from '../../dataschema/ILinePersistence';
import mongoose from 'mongoose';

/**
 * Creates new DataBase Schema
 */
const LineSchema = new mongoose.Schema(
    {
        lineId: {type: String, unique: true},
        name: {type: String, unique: true},
        color: {type: String},
        pathGo: [{
            type: String
        }],
        pathReturn: [{
            type: String
        }],
        vehicleType: {type: String},
        driverType: {type: String}
    },
    {
        timestamps: true
    }
);

export default mongoose.model<ILinePersistence & mongoose.Document>('Line', LineSchema);