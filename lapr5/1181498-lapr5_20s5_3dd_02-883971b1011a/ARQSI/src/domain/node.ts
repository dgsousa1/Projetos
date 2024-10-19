import { AggregateRoot } from "../core/domain/AggregateRoot";
import { UniqueEntityID } from "../core/domain/UniqueEntityID";
import { Guard } from "../core/logic/Guard";
import { Result } from "../core/logic/Result";
import { NodeId } from "./nodeId";
import { NodeNameProps } from "./nodeName";
import { ShortNameProps } from "./shortName";

/**
 * Properties of Node
 */
interface NodeProps {
    name: NodeNameProps;
    shortName: ShortNameProps;
    latitude: number;
    longitude: number;
    isDepot: boolean;
    isReliefPoint: boolean;
}


export class Node extends AggregateRoot<NodeProps> {
    get id(): UniqueEntityID {
        return this._id;
    }

    get nodeId(): NodeId {
        return NodeId.create(this.id);
    }

    get name(): string {
        return this.props.name.nodeName;
    }

    get shortName(): string {
        return this.props.shortName.shortName;
    }

    get latitude(): number {
        return this.props.latitude;
    }

    get longitude(): number {
        return this.props.longitude;
    }

    get isDepot(): boolean {
        return this.props.isDepot;
    }

    get isReliefPoint(): boolean {
        return this.props.isReliefPoint;
    }

    /**
     * Node constructor
     * @param props Node Properties
     * @param id Node id
     */
    private constructor(props: NodeProps, id?: UniqueEntityID) {
        super(props, id);
    }

    /**
     * Create Node from properties and id
     * @param props Node Properties 
     * @param id Node Id
     */
    public static create(props: NodeProps, id?: UniqueEntityID): Result<Node> {
        const guardedProps = [
            { argument: props.name, argumentName: 'name' },
            { argument: props.shortName, argumentName: 'shortName' },
            { argument: props.latitude, argumentName: 'latitude' },
            { argument: props.longitude, argumentName: 'longitude' },
            { argument: props.isDepot, argumentName: 'isDepot' },
            { argument: props.isReliefPoint, argumentName: 'isReliefPoint' }
        ];
        const guardResult = Guard.againstNullOrUndefinedBulk(guardedProps);
        if (!guardResult.succeeded) {
            return Result.fail<Node>(guardResult.message)
        }
        else {
            const node = new Node({ ...props }, id);
            return Result.ok<Node>(node);
        }
    }
}