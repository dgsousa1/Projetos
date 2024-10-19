import { Repo } from "../../core/infra/Repo";
import { VehicleType } from "../../domain/vehicleType";

export default interface IVehicleTypeRepo extends Repo<VehicleType> {
  /**
   *  Saves a vehicle type object on the database
   * @param vehicleType vehicleType object to be saved on the database
   * @returns VehicleType object that was added to the database
   */
  save(vehicleType: VehicleType): Promise<VehicleType>;
  /**
  *  Finds all vehicleTypes
  * @returns array of VehicleType objects
  */
  findAll(): Promise<VehicleType[]>;
  /**
  *  Finds a vehicle type by the id
  * @param vehicleTypeId id used on the query to find the vehicle type
  * @returns VehicleType object that was found by its id
  */
  findByDomainId(vehicleTypeId: VehicleType | string): Promise<VehicleType>;
  /**
    * Finds a vehicle type by its exact name
    * @param name name used on the query to find the vehicle type
    * @returns VehicleType object that was found by its exact name
    */
  findByExactName(name: string): Promise<VehicleType>;
}
