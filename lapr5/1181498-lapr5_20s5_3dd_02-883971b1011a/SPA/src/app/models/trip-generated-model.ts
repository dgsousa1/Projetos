import { PathModel } from "./path.model";

export interface TripGeneratedModel {
    NViagens : number,
    frequencia: number,
    NParalelos: number,
    horaSaida: number,
    nomeLinha: string,
    path: PathModel[]//go e um return
}
