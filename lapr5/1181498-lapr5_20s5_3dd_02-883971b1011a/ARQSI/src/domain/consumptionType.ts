import { ValueObject } from "../core/domain/ValueObject";
import { Result } from "../core/logic/Result";
import { TextUtil } from "../utils/TextUtil";
import { ConsumptionUnits } from "./consumptionUnitEnums";
import { FuelTypes } from "./fuelTypeEnums";

/**
 * Properties of a ConsumptionType object
 */
export interface ConsumptionTypeProps {
    value: number,
    unit: string
}

export class ConsumptionType extends ValueObject<ConsumptionTypeProps> {

    get value(): number {
        return this.props.value
    }

    get unit(): string {
        return this.props.unit
    }

    set value(num: number) {
        this.props.value = num;
    }

    set unit(name: string) {
        this.props.unit = name;
    }

    /**
     * ConsumptionType constructor
     * @param props ConsumptionType properties
     */
    private constructor(props: ConsumptionTypeProps) {
        super(props);
    }

    /**
     * Setting the consumption unit to the equivalent fuel type
     * @param fuel fuel type
     * @returns equivalent consumption unit
     */
    private static getUnitFromFuel(fuel: string): string {
        switch (fuel) {
            case FuelTypes.DIESEL.toString():
                return ConsumptionUnits.DIESEL.toString()
            case FuelTypes.GPL.toString():
                return ConsumptionUnits.GPL.toString()
            case FuelTypes.HYDROGEN.toString():
                return ConsumptionUnits.HYDROGEN.toString()
            case FuelTypes.ELETRIC.toString():
                return ConsumptionUnits.ELETRIC.toString()
            case FuelTypes.GASOLINE.toString():
                return ConsumptionUnits.GASOLINE.toString()
            default:
                return 'error'
        }
    }

    /**
     * Method that creates the consumptionType object
     * @param number quantity
     * @param fuel fuel type
     */
    public static create(number: number, fuel: string) {
        var unitString = this.getUnitFromFuel(fuel);
        if (unitString === 'error') {
            Result.fail<ConsumptionType>('Invalid fuel');
        }

        const newConsumption = new ConsumptionType(
            {
                value: number,
                unit: unitString
            }
        )
        return Result.ok<ConsumptionType>(newConsumption);
    }



}













