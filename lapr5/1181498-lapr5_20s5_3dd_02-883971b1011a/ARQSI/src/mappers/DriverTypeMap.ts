import { Mapper } from "../core/infra/Mapper";

import { Document, Model } from 'mongoose';
import { IDriverTypePersistence } from "../dataschema/IDriverTypePersistence";

import IDriverTypeDTO from "../dto/IDriverTypeDTO";
import { DriverType } from "../domain/driverType";

import { UniqueEntityID } from "../core/domain/UniqueEntityID";
import { TypeName } from "../domain/typeName";
import { Description } from "../domain/description";

export class DriverTypeMap extends Mapper<DriverType> {

  /**
   * Converts a DriverType object to DTO
   * @param driverType object to be converted
   * @returns DriverTypeDTO object
   */
  public static toDTO(driverType: DriverType): IDriverTypeDTO {
    return {
      driverTypeId: driverType.id.toString(),
      name: driverType.name,
      description: driverType.description,
    } as IDriverTypeDTO;
  }

  /**
   * Creates DriverType object from DriverTypePersistence
   * @param driverType DriverTypePersistence object to be converted
   * @returns DriverType object
   */
  public static toDomain(driverType: any | Model<IDriverTypePersistence & Document>): DriverType {
    const driverTypeName = TypeName.create(driverType.name);
    if (driverTypeName.isFailure) { return null }
    const driverDescription = Description.create(driverType.description);
    if (driverDescription.isFailure) { return null }
    const driverTypeOrError = DriverType.create(
      {
        name: driverTypeName.getValue(),
        description: driverDescription.getValue()
      },
      new UniqueEntityID(driverType.driverTypeId)
    );
    driverTypeOrError.isFailure ? console.log(driverTypeOrError.error) : '';
    return driverTypeOrError.isSuccess ? driverTypeOrError.getValue() : null;
  }

  /**
   * Converts DriverType object to persistence
   * @param driverType object to be converted
   * @returns JSON object
   */
  public static toPersistence(driverType: DriverType): any {
    return {
      driverTypeId: driverType.id.toString(),
      name: driverType.name,
      description: driverType.description
    }
  }
}