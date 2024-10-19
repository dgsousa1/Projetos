
export interface INodePersistence{
    _id: string;
    nodeId: string;
    name: string;
    latitude: Number;
    longitude: Number;
    shortName: String;
    isDepot: Boolean;
    isReliefPoint: Boolean;
}