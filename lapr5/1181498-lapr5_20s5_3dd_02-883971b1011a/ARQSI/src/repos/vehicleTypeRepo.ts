import { Service, Inject } from 'typedi';
import IVehicleTypeRepo from "../services/IRepos/IVehicleTypeRepo";
import { Document, Model } from 'mongoose';
import { IVehicleTypePersistence } from '../dataschema/IVehicleTypePersistence';
import { VehicleTypeId } from '../domain/vehicleTypeId';
import { VehicleType } from '../domain/vehicleType';
import { VehicleTypeMap } from '../mappers/VehicleTypeMap';

@Service()
export default class VehicleTypeRepo implements IVehicleTypeRepo {
  private models: any;

  /**
   * Creates a vehicle type repository with the injected parameters
   * @param vehicleTypeSchema vehicle type schema injected
   */
  constructor(
    @Inject('vehicleTypeSchema') private vehicleTypeSchema: Model<IVehicleTypePersistence & Document>,
  ) { }

  private createBaseQuery(): any {
    return {
      where: {},
    }
  }

  /**
  * Checks if a vehicle type exists in the database by its ID
  * @param vehicleTypeId ID of the vehicle type to be checked
  * @returns boolean true or false if it exists or not
  */
  public async exists(vehicleTypeId: VehicleTypeId | string): Promise<boolean> {
    const idX = vehicleTypeId instanceof VehicleTypeId ? (<VehicleTypeId>vehicleTypeId).id.toValue() : vehicleTypeId;
    const query = { domainId: idX };
    const typeDocument = await this.vehicleTypeSchema.findOne(query);
    return !!typeDocument === true;
  }

  /**
   *  Saves a vehicle type object on the database
   * @param vehicleType vehicleType object to be saved on the database
   * @returns VehicleType object that was added to the database
   */
  public async save(vehicleType: VehicleType): Promise<VehicleType> {
    const query = { domainId: vehicleType.vehicleTypeId };
    const typeDocument = await this.vehicleTypeSchema.findOne(query);
    try {
      if (typeDocument === null) {
        const rawType: any = VehicleTypeMap.toPersistence(vehicleType);
        const typeCreated = await this.vehicleTypeSchema.create(rawType);
        return VehicleTypeMap.toDomain(typeCreated);
      } else {
        typeDocument.name = vehicleType.name;
        await typeDocument.save();
        return vehicleType;
      }
    } catch (err) {
      throw err;
    }
  }

  /**
   *  Finds a vehicle type by the id
   * @param vehicleTypeId id used on the query to find the vehicle type
   * @returns VehicleType object that was found by its id
   */
  public async findByDomainId(vehicleTypeId: VehicleTypeId | string): Promise<VehicleType> {
    const query = { vehicleTypeId: vehicleTypeId.toString() };
    const typeRecord = await this.vehicleTypeSchema.findOne(query);
    if (typeRecord != null) {
      return VehicleTypeMap.toDomain(typeRecord);
    }
    else
      return null;
  }

  /**
    * Finds a vehicle type by its exact name
    * @param name name used on the query to find the vehicle type
    * @returns VehicleType object that was found by its exact name
    */
  public async findByExactName(name: string): Promise<VehicleType> {
    const query = { name: name };
    const vtRecord = await this.vehicleTypeSchema.findOne(query);
    if (vtRecord != null) {
      return VehicleTypeMap.toDomain(vtRecord);
    }
    else
      return null;
  }

  /**
  *  Finds all vehicleTypes
  * @returns array of VehicleType objects
  */
  public async findAll(): Promise<VehicleType[]> {
    const query = {};
    return this.executeQuery(query);
  }

  /**
    * Executes the queries on the database that return a list
    * @param query will be used to query the database
    * @returns array of VehicleType objects result of the query
    */
  private async executeQuery(query: any): Promise<VehicleType[]> {
    const list = await this.vehicleTypeSchema.find(query);
    const result = new Array();

    if (list != null) {
      list.forEach(element => {
        result.push(VehicleTypeMap.toDomain(element));
      });
      return result;
    }
    else
      return null;
  }


}