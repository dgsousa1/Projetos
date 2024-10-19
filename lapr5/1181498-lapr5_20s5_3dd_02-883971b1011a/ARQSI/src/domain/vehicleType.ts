import { AggregateRoot } from "../core/domain/AggregateRoot";
import { UniqueEntityID } from "../core/domain/UniqueEntityID";
import { Guard } from "../core/logic/Guard";
import { Result } from "../core/logic/Result";
import { VehicleTypeId } from "./vehicleTypeId";
import IVehicleTypeDTO from "../dto/IVehicleTypeDTO";
import { Description } from "./description";
import { TypeNameProps } from "./typeName";
import { FuelTypeProps } from "./fuelType";
import { ConsumptionTypeProps } from "./consumptionType";

/**
 * Properties of a vehicleType object
 */
interface VehicleTypeProps {
    name: TypeNameProps;
    fuelType: FuelTypeProps;
    autonomy: number;
    costPerKilometer: number;
    consumption: ConsumptionTypeProps;
    averageSpeed: number,
    description: Description,
    emission: number
}

export class VehicleType extends AggregateRoot<VehicleTypeProps> {
    get id(): UniqueEntityID {
        return this._id;
    }

    get vehicleTypeId(): VehicleTypeId {
        return VehicleTypeId.create(this.id);
    }

    get name(): string {
        return this.props.name.typeName
    }

    get fuelType(): string {
        return this.props.fuelType.fuelType
    }

    get autonomy(): number {
        return this.props.autonomy;
    }

    get costPerKilometer(): number {
        return this.props.costPerKilometer;
    }

    get consumptionValue(): number {
        return this.props.consumption.value
    }

    get consumptionUnit(): string {
        return this.props.consumption.unit
    }

    get averageSpeed(): number {
        return this.props.averageSpeed;
    }

    get description(): string {
        return this.props.description.description
    }

    get emission(): number {
        return this.props.emission
    }

    /**
       * VehicleType constructor
       * @param props VehicleType properties
       * @param id  VehicleType id 
       */
    private constructor(props: VehicleTypeProps, id?: UniqueEntityID) {
        super(props, id);
    }

    /**
     * Method that creates the VehicleType object
     * @param props VehicleType properties
     * @param id  VehicleType id
     */
    public static create(props: VehicleTypeProps, id?: UniqueEntityID): Result<VehicleType> {
        const guardedProps = [
            { argument: props.name, argumentName: 'name' },
            { argument: props.fuelType, argumentName: 'fuelType' },
            { argument: props.autonomy, argumentName: 'autonomy' },
            { argument: props.costPerKilometer, argumentName: 'costPerKilometer' },
            { argument: props.consumption, argumentName: 'consumption' },
            { argument: props.averageSpeed, argumentName: 'averageSpeed' },
            { argument: props.description, argumentName: 'description' },
            { argument: props.emission, argumentName: 'emission' },
        ];

        const guardResult = Guard.againstNullOrUndefinedBulk(guardedProps);

        if (!guardResult.succeeded) {
            return Result.fail<VehicleType>(guardResult.message)
        }
        else {

            if (props.autonomy < 0 || props.averageSpeed < 0) {
                Result.fail<VehicleType>('Error, negative values when positive are required')
            }

            if (props.costPerKilometer < 0.01) {
                Result.fail<VehicleType>('Error, invalid cost')
            }

            const vehicleType = new VehicleType(
                { ...props }, id)

            return Result.ok<VehicleType>(vehicleType);
        }
    }

}