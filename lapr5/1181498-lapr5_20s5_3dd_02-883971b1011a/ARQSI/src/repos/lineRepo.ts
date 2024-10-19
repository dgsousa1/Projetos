import Container, { Service, Inject } from 'typedi';
import ILineRepo from "../services/IRepos/ILineRepo";
import { Line } from "../domain/line";
import { LineId } from "../domain/lineId";
import { LineMap } from "../mappers/LineMap";
import ILineDTO from '../dto/ILineDTO';
import { Document, Model, MongooseFilterQuery } from 'mongoose';
import { ILinePersistence } from '../dataschema/ILinePersistence';
import VehicleTypeRepo from './vehicleTypeRepo';
import DriverTypeRepo from './driverTypeRepo';
import PathRepo from './pathRepo';

@Service()
export default class LineRepo implements ILineRepo {

  /**
   * Creates LineRepo
   * @param lineSchema Injected ILinePersistence into LineRepo
   */
  constructor(
    @Inject('lineSchema') private lineSchema: Model<ILinePersistence & Document>
  ) { }

  /**
  * Executes received query
  * @param query Query to be executed
  * @param order Order of the list of Nodes
  * @returns Query result or null in case of error
  */
  private async executeQuery(query: any, order: string): Promise<Line[]> {
    const lineList = await this.lineSchema.find(query);
    const result = new Array();
    if (lineList != null) {
      lineList.forEach(element => {
        result.push(LineMap.toDomain(element));
      });
      return await this.orderList(result, order);
    }
    else
      return null;
  }

  /**
   * Orders List
   * @param list List without order 
   * @param order Order of the list of Lines
   * @returns List in order
   */
  private async orderList(list: Line[], order: string): Promise<Line[]> {
    //source: https://flaviocopes.com/how-to-sort-array-of-objects-by-property-javascript/
    switch (order) {
      case 'name':
        list.sort((a, b) => (a.name > b.name) ? 1 : -1);
        return list;
      case 'id':
        list.sort((a, b) => (a.id > b.id) ? 1 : -1);
        return list;
      default:
        return list;
    }
  }

  /**
  * Validates de Line attributes
  * @param line Line to be validated
  *  @returns true if the Line is valid and false otherwise
  */
  private async validateLine(line: ILineDTO) {
    var validationError = true;
    const vehicleRepo = Container.get(VehicleTypeRepo);
    const driverRepo = Container.get(DriverTypeRepo);
    const pathRepo = Container.get(PathRepo);
    var vehicleError = await vehicleRepo.findByDomainId(line.vehicleType);
    var driverError = await driverRepo.findByDomainId(line.driverType);
    var pathGoError = null;
    var valPathGo = true;
    for (let index = 0; index < line.pathGo.length; index++) {
      pathGoError = await pathRepo.findByDomainId(line.pathGo[index]);
      if (pathGoError === null) {
        valPathGo = false;
      }
    }
    var pathReturnError = null;
    var valPathReturn = true;
    for (let index = 0; index < line.pathReturn.length; index++) {
      pathReturnError = await pathRepo.findByDomainId(line.pathReturn[index]);
      if (pathReturnError === null) {
        valPathReturn = false;
      }
    }
    var driverTypeId = line.driverType.toString();
    var vehicleTypeId = line.vehicleType.toString();
    if ((vehicleError === null && vehicleTypeId != '') || (driverError === null && driverTypeId != '') || !valPathGo || !valPathReturn)
      validationError = false;
    return validationError;
  }

  /**
   * Validates if a Line with the received Id exists
   * @param lineId LineId
   * @returns true if Line exists in the DataBase and false otherwise
   */
  public async exists(lineId: LineId | string): Promise<boolean> {

    const idX = lineId instanceof LineId ? (<LineId>lineId).id.toValue() : lineId;

    const query = { domainId: idX };
    const lineDocument = await this.lineSchema.findOne(query);

    return !!lineDocument === true;
  }

  /**
   * Saves the received Line into the DataBase
   * @param line Object Line
   * @returns Object Line
   */
  public async save(line: Line): Promise<Line> {
    const query = { domainId: line.lineId };
    const lineDocument = await this.lineSchema.findOne(query);
    try {
      if (lineDocument === null) {
        const rawLine: any = LineMap.toPersistence(line);
        if (await this.validateLine(rawLine)) {
          const lineCreated = await this.lineSchema.create(rawLine);
          return LineMap.toDomain(lineCreated);
        } else {
          return null;
        }
      } else {
        lineDocument.name = line.name.toString();
        lineDocument.color = line.color.toString();
        await lineDocument.save();
        return line;
      }
    } catch (err) {
      throw err;
    }
  }

  /**
   * Gets all Lines saved in the DataBase
   * @param order Order of the list of Lines
   * @returns List of Lines
   */
  public async findAll(order: string): Promise<Line[]> {
    const query = {};
    return this.executeQuery(query, order);
  }

  /**
   * Gets Lines saved in the DataBase with the received name
   * @param name Lines name
   * @param order Order of the list of Lines
   * @returns List of Lines
   */
  public async findByName(name: string, order: string): Promise<Line[]> {
    var exp = new RegExp("^" + name);
    const query = { name: exp };
    return this.executeQuery(query, order);
  }

  /**
   * Gets Lines saved in the DataBase with the exact name received
   * @param name Lines name
   * @returns Object Lines
   */
  public async findByExactName(name: string): Promise<Line> {
    const query = { name: name };
    const lineRecord = await this.lineSchema.findOne(query);
    if (lineRecord != null) {
      return LineMap.toDomain(lineRecord);
    }
    else
      return null;
  }

  /**
   * Gets Line with the received Id
   * @param lineId LineId
   * @returns Object Line
   */
  public async findByDomainId(lineId: LineId | string): Promise<Line> {
    const query = { lineId: lineId.toString() };
    const lineRecord = await this.lineSchema.findOne(query);

    if (lineRecord != null) {
      return LineMap.toDomain(lineRecord);
    }
    else
      return null;
  }
}