import { ValueObject } from "../core/domain/ValueObject";
import { Result } from "../core/logic/Result";
import { TextUtil } from "../utils/TextUtil";
import { FuelTypes } from "./fuelTypeEnums";


/**
 * Properties of a fuelType object
 */
export interface FuelTypeProps {
    fuelType: string
}

export class FuelType extends ValueObject<FuelTypeProps> {

    get fuelType(): string {
        return this.props.fuelType
    }

    set fuelType(fuelType: string) {
        this.props.fuelType = fuelType
    }

    /**
     * FuelType constructor
     * @param props FuelType properties
     */
    private constructor(props: FuelTypeProps) {
        super(props);
    }

    /**
     * Converting a num representing a fuelType to a string
     * @param num number representing a fuelType
     * @returns fuelType string
     */
    private static getFuelFromNumber(num: string): string {
        switch (num) {
            case '23':
                return FuelTypes.DIESEL.toString()
            case FuelTypes.DIESEL.toString():
                return FuelTypes.DIESEL.toString()
            case '20':
                return FuelTypes.GPL.toString()
            case FuelTypes.GPL.toString():
                return FuelTypes.GPL.toString()
            case '50':
                return FuelTypes.HYDROGEN.toString()
            case FuelTypes.HYDROGEN.toString():
                return FuelTypes.HYDROGEN.toString()
            case '75':
                return FuelTypes.ELETRIC.toString()
            case FuelTypes.ELETRIC.toString():
                return FuelTypes.ELETRIC.toString()
            case '1':
                return FuelTypes.GASOLINE.toString()
            case FuelTypes.GASOLINE.toString():
                return FuelTypes.GASOLINE.toString()
            default:
                return 'error'
        }
    }

    /**
     * Method that creates the FuelType object
     * @param fuelType string or number representing the fuel type
     */
    public static create(fuelType: string) {
        var fuelString = this.getFuelFromNumber(fuelType);
        if (fuelString === 'error') {
            Result.fail<FuelType>('Invalid fuel');
        }

        const newFuel = new FuelType({ fuelType: fuelString });
        return Result.ok<FuelType>(newFuel);
    }



}













