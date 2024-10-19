import { INodePersistence } from '../../dataschema/INodePersistence';
import mongoose from 'mongoose';

/**
 * Creates new DataBase Schema
 */
const NodeSchema = new mongoose.Schema(
    {
        nodeId: {type: String, unique: true},
        name: {type: String, unique: true},
        shortName: {type: String, unique: true},
        latitude: {type: Number},
        longitude: {type: Number},
        isDepot: {type: Boolean},
        isReliefPoint: {type: Boolean}
    },
    {
        timestamps: true
    }
);

export default mongoose.model<INodePersistence & mongoose.Document>('Node', NodeSchema);
