import { IPathPersistence } from '../../dataschema/IPathPersistence';
import mongoose, { Schema } from 'mongoose';
import { number } from 'joi';

/**
 * creates new PathSchema
 */
const PathSchema = new mongoose.Schema(
    {
        pathId: {type: String, unique: true},
        key: {type: Number, unique: true},
        pathNodes: [{
            duracao: Number,
            distancia: Number,
            inicialNode: String,
            finalNode: String,      
        }]
    },
    {
        timestamps: true
    }
);

export default mongoose.model<IPathPersistence & mongoose.Document>('Path', PathSchema);