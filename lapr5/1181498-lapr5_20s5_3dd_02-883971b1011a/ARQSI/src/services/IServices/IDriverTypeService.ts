import { Result } from "../../core/logic/Result";
import IDriverTypeDTO from "../../dto/IDriverTypeDTO";


export default interface IDriverTypeService {
  /**
  *  Creates a driver type service with the injected parameters
  * @param driverTypeRepo  driver type repository to be injected 
  */
  createDriverType(driverTypeDTO: IDriverTypeDTO): Promise<Result<IDriverTypeDTO>>;
  /**
  *  Services that retrieves all driver types from the database and then returns in DTO form
  * @returns Result  with an array of DriverTypeDTO
  */
  findAll(): Promise<Result<IDriverTypeDTO[]>>;
  /**
   *  Service that finds a driver type by it's ID on the database, then returns it in DTO Form
   * @param id the ID of the driver type to be found
   * @returns Result with a DriverTypeDTO
   */
  findById(id: string): Promise<Result<IDriverTypeDTO>>;
  /**
   *  Service that finds a driver type by it's name on the database, then returns it in DTO Form
   * @param name the name of the driver type to be found
   * @returns Result with a DriverTypeDTO
   */
  findByExactName(name: string): Promise<Result<IDriverTypeDTO>>;
}