import { DriverTypeId } from "../domain/driverTypeId";
import { PathId } from "../domain/pathId";
import { VehicleTypeId } from "../domain/vehicleTypeId";

export interface ILinePersistence{
    _id: string;
    lineId: string;
    name: string;
    color: string;
    pathGo: PathId[];
    pathReturn: PathId[];
    vehicleType: VehicleTypeId;
    driverType: DriverTypeId;
}