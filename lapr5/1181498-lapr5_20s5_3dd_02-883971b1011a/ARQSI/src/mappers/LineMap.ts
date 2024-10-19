import { Mapper } from "../core/infra/Mapper";
import { Document, Model } from 'mongoose';
import { ILinePersistence } from "../dataschema/ILinePersistence";
import ILineDTO from "../dto/ILineDTO";
import { Line } from "../domain/line";
import { UniqueEntityID } from "../core/domain/UniqueEntityID";

export class LineMap extends Mapper<Line> {

  /**
   * Converts Object Line to DTO
   * @param line Object Line
   * @returns LineDTO
   */
  public static toDTO(line: Line): ILineDTO {
    return {
      lineId: line.id.toString(),
      name: line.name,
      color: line.color,
      pathGo: line.pathGo,
      pathReturn: line.pathReturn,
      vehicleType: line.vehicleType,
      driverType: line.driverType
    } as ILineDTO;
  }

  /**
   * Creates Object Line from ILinePersistence
   * @param node ILinePersistence
   * @returns Object Line
   */
  public static toDomain(line: any | Model<ILinePersistence & Document>): Line {    
    const lineOrError = Line.create(
      line,
      new UniqueEntityID(line.lineId)
    );
    lineOrError.isFailure ? console.log(lineOrError.error) : '';
    return lineOrError.isSuccess ? lineOrError.getValue() : null;
  }

  /**
   * Converts Object Line to ILinePersistence
   * @param node Object Line
   * @returns ILinePersistence
   */
  public static toPersistence(line: Line): any {
    return {
        lineId: line.id.toString(),
        name: line.name,
        color: line.color,
        pathGo: line.pathGo,
        pathReturn: line.pathReturn,
        vehicleType: line.vehicleType,
        driverType: line.driverType
    }
  }
}