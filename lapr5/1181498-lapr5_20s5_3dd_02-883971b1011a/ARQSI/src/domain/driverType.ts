import { AggregateRoot } from "../core/domain/AggregateRoot";
import { UniqueEntityID } from "../core/domain/UniqueEntityID";
import { Guard } from "../core/logic/Guard";
import { Result } from "../core/logic/Result";
import { DriverTypeId } from "./driverTypeId";
import { TypeName, TypeNameProps } from "./typeName";
import IDriverTypeDTO from "../dto/IDriverTypeDTO";
import { Description, DescriptionProps } from "./description";

/**
 * Properties of a driverType object
 */
interface DriverTypeProps {
    name: TypeNameProps,
    description: DescriptionProps;
}

/**
 * DriverType class
 */
export class DriverType extends AggregateRoot<DriverTypeProps> {
    get id(): UniqueEntityID {
        return this._id;
    }

    get driverTypeId(): DriverTypeId {
        return DriverTypeId.create(this.id);
    }

    get name(): string {
        return this.props.name.typeName
    }

    get description(): string {
        return this.props.description.description;
    }

    /**
     * DriverType constructor
     * @param props DriverType properties
     * @param id  DriverType id 
     */
    private constructor(props: DriverTypeProps, id?: UniqueEntityID) {
        super(props, id);
    }

    /**
     * Method that creates the DriverType object
     * @param props DriverType properties
     * @param id  DriverType id
     */
    public static create(props: DriverTypeProps, id?: UniqueEntityID): Result<DriverType> {
        const guardedProps = [
            { argument: props.name, argumentName: 'name' },
            { argument: props.description, argumentName: 'description' },
        ];
        const guardResult = Guard.againstNullOrUndefinedBulk(guardedProps);
        if (!guardResult.succeeded) {
            return Result.fail<DriverType>(guardResult.message)
        }
        else {
            const driverType = new DriverType({
                ...props
            }, id);
            return Result.ok<DriverType>(driverType);
        }
    }
}