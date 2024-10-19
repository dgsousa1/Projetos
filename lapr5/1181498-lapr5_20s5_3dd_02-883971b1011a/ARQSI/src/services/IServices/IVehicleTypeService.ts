import { Result } from "../../core/logic/Result";
import IVehicleTypeDTO from "../../dto/IVehicleTypeDTO";

export default interface IVehicleTypeService {
  /**
   * Service that creates a vehicle type and the necessary value objects then saves it on the database
   * @param vehicleTypeDTO  DTO with vehicle type information
   * @returns Result with a vehicle type DTO
   */
  createVehicleType(vehicleTypeDTO: IVehicleTypeDTO): Promise<Result<IVehicleTypeDTO>>;
  /**
    * Service that retrieves all vehicle types from the database and then returns in DTO form
    * @returns Result  with an array of VehicleTypeDTO
    */
  findAll(): Promise<Result<IVehicleTypeDTO[]>>;
  /**
   * Service that finds a vehicle type by it's ID on the database, then returns it in DTO Form
   * @param id the ID of the vehicle type to be found
   * @returns Result with a VehicleTypeDTO
   */
  findById(id: string): Promise<Result<IVehicleTypeDTO>>;
  /**
   *  Service that finds a vehicle type by it's name on the database, then returns it in DTO Form
   * @param name the name of the vehicle type to be found
   * @returns Result with a VehicleTypeDTO
   */
  findByExactName(name: string): Promise<Result<IVehicleTypeDTO>>;
}