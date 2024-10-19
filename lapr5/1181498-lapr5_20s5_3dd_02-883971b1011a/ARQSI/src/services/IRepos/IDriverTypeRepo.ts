import { Repo } from "../../core/infra/Repo";
import { DriverType } from "../../domain/driverType";

export default interface IDriverTypeRepo extends Repo<DriverType> {
  /**
   *  Saves a driver type object on the database
   * @param driverType driverType object to be saved on the database
   * @returns DriverType object that was added to the database
   */
  save(driverType: DriverType): Promise<DriverType>;
  /**
  *  Finds a driver type by the id
  * @param driverTypeId id used on the query to find the driver type
  * @returns DriverType object that was found by its id
  */
  findByDomainId(driverTypeId: DriverType | string): Promise<DriverType>;
  /**
  *  Finds all driverTypes
  * @returns array of DriverType objects
  */
  findAll(): Promise<DriverType[]>;
  /**
   * Finds a driver type by its exact name
   * @param name name used on the query to find the driver type
   * @returns DriverType object that was found by its exact name
   */
  findByExactName(name: string): Promise<DriverType>;

}