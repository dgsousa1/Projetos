import { DriverTypeId } from "../domain/driverTypeId";
import { PathId } from "../domain/pathId";
import { VehicleTypeId } from "../domain/vehicleTypeId";

export default interface ILineDTO {
    name: String;
    color: String;
    pathGo: PathId[];
    pathReturn: PathId[];
    vehicleType: VehicleTypeId;
    driverType: DriverTypeId;
  }