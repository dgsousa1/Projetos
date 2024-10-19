import { Mapper } from "../core/infra/Mapper";

import { Document, Model } from 'mongoose';
import { IVehicleTypePersistence } from "../dataschema/IVehicleTypePersistence";

import IVehicleTypeDTO from "../dto/IVehicleTypeDTO";
import { VehicleType } from "../domain/vehicleType";

import { UniqueEntityID } from "../core/domain/UniqueEntityID";
import { TypeName } from "../domain/typeName";
import { FuelType } from "../domain/fuelType";
import { ConsumptionType } from "../domain/consumptionType";
import { Description } from "../domain/description";

export class VehicleTypeMap extends Mapper<VehicleType> {

    /**
     * Converts a VehicleType object to DTO
     * @param vehicleType object to be converted
     * @returns VehicleTypeDTO
     */
    public static toDTO(vehicleType: VehicleType): IVehicleTypeDTO {
        return {
            vehicleTypeId: vehicleType.id.toString(),
            name: vehicleType.name,
            fuelType: vehicleType.fuelType,
            autonomy: vehicleType.autonomy,
            costPerKilometer: vehicleType.costPerKilometer,
            consumption: vehicleType.consumptionValue,
            averageSpeed: vehicleType.averageSpeed,
            description: vehicleType.description,
            emission: vehicleType.emission
        } as IVehicleTypeDTO;
    }

    /**
     * Creates VehicleType object from VehicleTypePersistence
     * @param vehicleType VehicleTypePersistence object to be converted
     * @returns VehicleType object
     */
    public static toDomain(vehicleType: any | Model<IVehicleTypePersistence & Document>): VehicleType {
        const vehicleTypeName = TypeName.create(vehicleType.name);
        if (vehicleTypeName.isFailure) { return null }
        const fuelOrError = FuelType.create(vehicleType.fuelType);
        if (fuelOrError.isFailure) { return null }
        const conOrError = ConsumptionType.create(vehicleType.consumption,
            fuelOrError.getValue().fuelType);
        if (conOrError.isFailure) { return null }
        const descriptionOrError = Description.create(vehicleType.description)
        if (descriptionOrError.isFailure) { return null }

        const typeOrError = VehicleType.create({
            name: vehicleTypeName.getValue(),
            fuelType: fuelOrError.getValue(),
            autonomy: vehicleType.autonomy,
            costPerKilometer: vehicleType.costPerKilometer,
            consumption: conOrError.getValue(),
            averageSpeed: vehicleType.averageSpeed,
            description: descriptionOrError.getValue(),
            emission: vehicleType.emission
        },
            new UniqueEntityID(vehicleType.vehicleTypeId)
        );

        typeOrError.isFailure ? console.log(typeOrError.error) : '';

        return typeOrError.isSuccess ? typeOrError.getValue() : null;
    }

  /**
   * Converts VehicleType object to persistence
   * @param vehicleType object to be converted
   * @returns JSON object
   */
    public static toPersistence(vehicleType: VehicleType): any {
        return {
            vehicleTypeId: vehicleType.id.toString(),
            name: vehicleType.name,
            fuelType: vehicleType.fuelType,
            autonomy: vehicleType.autonomy,
            costPerKilometer: vehicleType.costPerKilometer,
            consumption: vehicleType.consumptionValue,
            averageSpeed: vehicleType.averageSpeed,
            description: vehicleType.description,
            emission: vehicleType.emission
        }
    }
}