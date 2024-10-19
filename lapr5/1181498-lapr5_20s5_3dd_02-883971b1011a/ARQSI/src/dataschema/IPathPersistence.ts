import { PathNode } from "../domain/pathNode";

export interface IPathPersistence{
    _id: string;
    pathId: String;
    key: Number;
    pathNodes: PathNode[];
}