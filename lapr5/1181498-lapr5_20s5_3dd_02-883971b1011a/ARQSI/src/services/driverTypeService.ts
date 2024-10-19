import { Service, Inject } from 'typedi';
import config from "../../config";
import IDriverTypeDTO from '../dto/IDriverTypeDTO';
import { DriverType } from '../domain/driverType';
import IDriverTypeRepo from './IRepos/IDriverTypeRepo';
import IDriverTypeService from './IServices/IDriverTypeService';
import { Result } from "../core/logic/Result";
import { DriverTypeMap } from '../mappers/DriverTypeMap';
import { TypeName } from '../domain/typeName';
import { Description } from '../domain/description';



@Service()
export default class DriverTypeService implements IDriverTypeService {

  /**
   *  Creates a driver type service with the injected parameters
   * @param driverTypeRepo  driver type repository to be injected 
   */
  constructor(
    @Inject(config.repos.driverType.name) private driverTypeRepo: IDriverTypeRepo
  ) { }

  /**
   *  Service that creates a driver type and the necessary value objects then saves it on the database
   * @param driverTypeDTO DTO with the information of the driver type to be created
   * @returns Result with a DriverTypeDTO
   */
  public async createDriverType(driverTypeDTO: IDriverTypeDTO): Promise<Result<IDriverTypeDTO>> {
    try {
      const driverTypeName = TypeName.create(driverTypeDTO.name);
      if (driverTypeName.isFailure) { return Result.fail<IDriverTypeDTO>(driverTypeName.errorValue()) }
      const driverDescription = Description.create(driverTypeDTO.description);
      if (driverDescription.isFailure) { return Result.fail<IDriverTypeDTO>(driverDescription.errorValue()) }
      const driverTypeOrError = await DriverType.create(
        {
          name: driverTypeName.getValue(),
          description: driverDescription.getValue()
        }
      );
      if (driverTypeOrError.isFailure) {
        return Result.fail<IDriverTypeDTO>(driverTypeOrError.errorValue());
      }
      const driverTypeResult = driverTypeOrError.getValue();
      await this.driverTypeRepo.save(driverTypeResult);
      const driverTypeDTOResult = DriverTypeMap.toDTO(driverTypeResult) as IDriverTypeDTO;
      return Result.ok<IDriverTypeDTO>(driverTypeDTOResult)
    } catch (e) {
      throw e;
    }
  }

  /**
   *  Service that retrieves all driver types from the database and then returns in DTO form
   * @returns Result  with an array of DriverTypeDTO
   */
  public async findAll(): Promise<Result<IDriverTypeDTO[]>> {
    try {
      const resultDTO = new Array();
      const typeList = await this.driverTypeRepo.findAll();
      for (let index = 0; index < typeList.length; index++) {
        const element = typeList[index];
        const typeDTOResult = DriverTypeMap.toDTO(element) as IDriverTypeDTO;
        resultDTO.push(typeDTOResult);
      }
      return Result.ok<IDriverTypeDTO[]>(resultDTO);
    } catch (e) {
      throw e;
    }
  }

  /**
   *  Service that finds a driver type by it's ID on the database, then returns it in DTO Form
   * @param id the ID of the driver type to be found
   * @returns Result with a DriverTypeDTO
   */
  public async findById(id: string): Promise<Result<IDriverTypeDTO>> {
    try {
      const driverType = await this.driverTypeRepo.findByDomainId(id);
      const driverTypeDTOResult = DriverTypeMap.toDTO(driverType) as IDriverTypeDTO;
      if (driverType === null) {
        return Result.fail<IDriverTypeDTO>("Driver type not found");
      }
      else {
        return Result.ok<IDriverTypeDTO>(driverTypeDTOResult);
      }
    } catch (e) {
      throw e;
    }
  }

  /**
   *  Service that finds a driver type by it's name on the database, then returns it in DTO Form
   * @param name the name of the driver type to be found
   * @returns Result with a DriverTypeDTO
   */
  public async findByExactName(name: string): Promise<Result<IDriverTypeDTO>> {
    try {
      const driverType = await this.driverTypeRepo.findByExactName(name);
      const driverTypeDTOResult = DriverTypeMap.toDTO(driverType) as IDriverTypeDTO;
      if (driverType === null) {
        return Result.fail<IDriverTypeDTO>("Driver type not found");
      }
      else {
        return Result.ok<IDriverTypeDTO>(driverTypeDTOResult);
      }
    } catch (e) {
      throw e;
    }
  }
}