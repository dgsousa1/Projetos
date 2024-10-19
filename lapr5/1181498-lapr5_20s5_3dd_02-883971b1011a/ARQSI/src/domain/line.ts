import { AggregateRoot } from "../core/domain/AggregateRoot";
import { UniqueEntityID } from "../core/domain/UniqueEntityID";
import { Result } from "../core/logic/Result";
import { Guard } from "../core/logic/Guard";
import { PathId } from "./pathId";
import { LineId } from "./lineId";
import { VehicleTypeId } from "./vehicleTypeId";
import { DriverTypeId } from "./driverTypeId";
import ILineDTO from "../dto/ILineDTO";

/**
 * Properties of Line
 */
interface LineProps {
    name: String;
    color: String
    pathGo: PathId[];
    pathReturn: PathId[];
    vehicleType: VehicleTypeId;
    driverType: DriverTypeId;
}

export class Line extends AggregateRoot<LineProps> {
    get id(): UniqueEntityID {
        return this._id;
    }

    get lineId(): LineId {
        return LineId.create(this.id);
    }

    get name(): String {
        return this.props.name;
    }

    get color(): String {
        return this.props.color;
    }

    get pathGo(): PathId[] {
        return this.props.pathGo;
    }

    get pathReturn(): PathId[] {
        return this.props.pathReturn;
    }

    get vehicleType(): VehicleTypeId {
        return this.props.vehicleType;
    }

    get driverType(): DriverTypeId {
        return this.props.driverType;
    }

    /**
     * Line constructor
     * @param props Line Properties
     * @param id Line Id
     */
    private constructor(props: LineProps, id?: UniqueEntityID) {
        super(props, id);
    }

    /**
     * Create Line
     * @param lineDTO Line DTO
     * @param id Line Id
     */
    public static create(lineDTO: ILineDTO, id?: UniqueEntityID): Result<Line> {
        const guardedProps = [
            {argument: lineDTO.name, argumentName: 'name'},
            {argument: lineDTO.color, argumentName: 'color'},
            {argument: lineDTO.pathGo, argumentName: 'pathGo'},
            {argument: lineDTO.pathReturn, argumentName: 'pathReturn'},
            {argument: lineDTO.vehicleType, argumentName: 'vehicleType'},
            {argument: lineDTO.driverType, argumentName: 'driverType'},
        ];
        const guardResult = Guard.againstNullOrUndefinedBulk(guardedProps);
        if (!guardResult.succeeded) {
            return Result.fail<Line>(guardResult.message)
        }
        else {
            const line = new Line({ name: lineDTO.name, color : lineDTO.color,
                pathGo: lineDTO.pathGo, pathReturn: lineDTO.pathReturn,
                vehicleType: lineDTO.vehicleType, driverType: lineDTO.driverType
            }, id);
            return Result.ok<Line>(line);
        }
    }
}