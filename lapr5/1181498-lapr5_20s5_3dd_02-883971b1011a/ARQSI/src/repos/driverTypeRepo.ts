import { Service, Inject } from 'typedi';

import IDriverTypeRepo from "../services/IRepos/IDriverTypeRepo";
import { DriverType } from "../domain/driverType";
import { DriverTypeId } from "../domain/driverTypeId";
import { DriverTypeMap } from "../mappers/DriverTypeMap";

import { Document, Model } from 'mongoose';
import { IDriverTypePersistence } from '../dataschema/IDriverTypePersistence';

@Service()
export default class DriverTypeRepo implements IDriverTypeRepo {
  private models: any;

  /**
   * Creates a driver type repository with the injected parameters
   * @param driverTypeSchema driver type schema injected
   */
  constructor(
    @Inject('driverTypeSchema') private driverTypeSchema: Model<IDriverTypePersistence & Document>,
  ) { }

  private createBaseQuery(): any {
    return {
      where: {},
    }
  }

  /**
   * Checks if a driver type exists in the database by its ID
   * @param driverTypeId ID of the driver type to be checked
   * @returns boolean true or false if it exists or not
   */
  public async exists(driverTypeId: DriverTypeId | string): Promise<boolean> {
    const idX = driverTypeId instanceof DriverTypeId ? (<DriverTypeId>driverTypeId).id.toValue() : driverTypeId;
    const query = { domainId: idX };
    const driverTypeDocument = await this.driverTypeSchema.findOne(query);
    return !!driverTypeDocument === true;
  }

  /**
   *  Saves a driver type object on the database
   * @param driverType driverType object to be saved on the database
   * @returns DriverType object that was added to the database
   */
  public async save(driverType: DriverType): Promise<DriverType> {
    const query = { domainId: driverType.driverTypeId };
    const driverTypeDocument = await this.driverTypeSchema.findOne(query);
    try {
      if (driverTypeDocument === null) {
        const rawDriverType: any = DriverTypeMap.toPersistence(driverType);
        const driverTypeCreated = await this.driverTypeSchema.create(rawDriverType);
        return DriverTypeMap.toDomain(driverTypeCreated);
      } else {
        driverTypeDocument.name = driverType.name;
        driverTypeDocument.description = driverType.description;
        await driverTypeDocument.save();
        return driverType;
      }
    } catch (err) {
      throw err;
    }
  }

  /**
   *  Finds a driver type by the id
   * @param driverTypeId id used on the query to find the driver type
   * @returns DriverType object that was found by its id
   */
  public async findByDomainId(driverTypeId: DriverTypeId | string): Promise<DriverType> {
    const query = { driverTypeId: driverTypeId.toString() };
    const driverTypeRecord = await this.driverTypeSchema.findOne(query);
    if (driverTypeRecord != null) {
      return DriverTypeMap.toDomain(driverTypeRecord);
    }
    else
      return null;
  }

  /**
   *  Finds all driverTypes
   * @returns array of DriverType objects
   */
  public async findAll(): Promise<DriverType[]> {
    const query = {};
    return this.executeQuery(query);
  }

  /**
   * Finds a driver type by its exact name
   * @param name name used on the query to find the driver type
   * @returns DriverType object that was found by its exact name
   */
  public async findByExactName(name: string): Promise<DriverType> {
    const query = { name: name };
    const nodeRecord = await this.driverTypeSchema.findOne(query);
    if (nodeRecord != null) {
      return DriverTypeMap.toDomain(nodeRecord);
    }
    else
      return null;

  }

  /**
   * Executes the queries on the database that return a list
   * @param query will be used to query the database
   * @returns array of DriverType objects result of the query
   */
  private async executeQuery(query: any): Promise<DriverType[]> {
    const list = await this.driverTypeSchema.find(query);
    const result = new Array();
    if (list != null) {
      list.forEach(element => {
        result.push(DriverTypeMap.toDomain(element));
      });
      return result;
    }
    else
      return null;
  }
}