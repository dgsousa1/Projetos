import { PathModel } from "./path.model";

export interface TripAdhocModel {
    numeroViagem: number,
    horaSaida: string,
    orientation : string,
    nomeLinha : string,
    path : PathModel
}
