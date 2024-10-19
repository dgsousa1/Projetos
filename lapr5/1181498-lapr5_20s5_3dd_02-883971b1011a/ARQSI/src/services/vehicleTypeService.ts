import { Service, Inject } from 'typedi';
import config from "../../config";
import IVehicleTypeDTO from '../dto/IVehicleTypeDTO';
import { VehicleType } from '../domain/vehicleType';
import IVehicleTypeRepo from './IRepos/IVehicleTypeRepo';
import IVehicleTypeService from './IServices/IVehicleTypeService';
import { Result } from "../core/logic/Result";
import { VehicleTypeMap } from '../mappers/VehicleTypeMap';
import { FuelType } from '../domain/fuelType';
import { ConsumptionType } from '../domain/consumptionType';
import { Description } from '../domain/description';
import { TypeName } from '../domain/typeName';


@Service()
export default class VehicleTypeService implements IVehicleTypeService {

  /**
  *  Creates a vehicle type service with the injected parameters
  * @param vehicleTypeRepoTypeRepo  vehicle type repository to be injected 
  */
  constructor(
    @Inject(config.repos.vehicleType.name) private vehicleTypeRepo: IVehicleTypeRepo
  ) { }

  /**
   * Service that creates a vehicle type and the necessary value objects then saves it on the database
   * @param vehicleTypeDTO  DTO with vehicle type information
   * @returns Result with a vehicle type DTO
   */
  public async createVehicleType(vehicleTypeDTO: IVehicleTypeDTO): Promise<Result<IVehicleTypeDTO>> {
    try {

      const vehicleTypeName = TypeName.create(vehicleTypeDTO.name);
      if (vehicleTypeName.isFailure) { return Result.fail<IVehicleTypeDTO>(vehicleTypeName.errorValue()) }

      const fuelOrError = FuelType.create(vehicleTypeDTO.fuelType);
      if (fuelOrError.isFailure) { return Result.fail<IVehicleTypeDTO>(fuelOrError.errorValue()) }

      const conOrError = ConsumptionType.create(vehicleTypeDTO.consumption,
        fuelOrError.getValue().fuelType);
      if (conOrError.isFailure) { return Result.fail<IVehicleTypeDTO>(conOrError.errorValue()) }

      const descriptionOrError = Description.create(vehicleTypeDTO.description)
      if (descriptionOrError.isFailure) { return Result.fail<IVehicleTypeDTO>(descriptionOrError.errorValue()) }

      const typeOrError = await VehicleType.create({
        name: vehicleTypeName.getValue(),
        fuelType: fuelOrError.getValue(),
        autonomy: vehicleTypeDTO.autonomy,
        costPerKilometer: vehicleTypeDTO.costPerKilometer,
        consumption: conOrError.getValue(),
        averageSpeed: vehicleTypeDTO.averageSpeed,
        description: descriptionOrError.getValue(),
        emission: vehicleTypeDTO.emission
      });

      if (typeOrError.isFailure) {
        return Result.fail<IVehicleTypeDTO>(typeOrError.errorValue());
      }

      const typeResult = typeOrError.getValue();

      await this.vehicleTypeRepo.save(typeResult);

      const vehicleTypeDTOResult = VehicleTypeMap.toDTO(typeResult) as IVehicleTypeDTO;
      return Result.ok<IVehicleTypeDTO>(vehicleTypeDTOResult)
    } catch (e) {
      throw e;
    }
  }

  /**
    *  Service that retrieves all vehicle types from the database and then returns in DTO form
    * @returns Result  with an array of VehicleTypeDTO
    */
  public async findAll(): Promise<Result<IVehicleTypeDTO[]>> {
    try {
      const resultDTO = new Array();
      const typeList = await this.vehicleTypeRepo.findAll();

      for (let index = 0; index < typeList.length; index++) {
        const element = typeList[index];
        const typeDTOResult = VehicleTypeMap.toDTO(element) as IVehicleTypeDTO;
        resultDTO.push(typeDTOResult);
      }
      return Result.ok<IVehicleTypeDTO[]>(resultDTO);
    } catch (e) {
      throw e;
    }
  }

  /**
   *  Service that finds a vehicle type by it's ID on the database, then returns it in DTO Form
   * @param id the ID of the vehicle type to be found
   * @returns Result with a VehicleTypeDTO
   */
  public async findById(id: string): Promise<Result<IVehicleTypeDTO>> {
    try {
      const vehicleType = await this.vehicleTypeRepo.findByDomainId(id);
      const vehicleTypeDTOResult = VehicleTypeMap.toDTO(vehicleType) as IVehicleTypeDTO;
      if (vehicleType === null) {
        return Result.fail<IVehicleTypeDTO>("Vehicle type not found");
      }
      else {
        return Result.ok<IVehicleTypeDTO>(vehicleTypeDTOResult);
      }
    } catch (e) {
      throw e;
    }
  }

  /**
   *  Service that finds a vehicle type by it's name on the database, then returns it in DTO Form
   * @param name the name of the vehicle type to be found
   * @returns Result with a VehicleTypeDTO
   */
  public async findByExactName(name: string): Promise<Result<IVehicleTypeDTO>> {
    try {
      const vehicleType = await this.vehicleTypeRepo.findByExactName(name);
      const vehicleTypeDTOResult = VehicleTypeMap.toDTO(vehicleType) as IVehicleTypeDTO;
      if (vehicleType === null) {
        return Result.fail<IVehicleTypeDTO>("Vehicle type not found");
      }
      else {
        return Result.ok<IVehicleTypeDTO>(vehicleTypeDTOResult);
      }
    } catch (e) {
      throw e;
    }
  }
}
