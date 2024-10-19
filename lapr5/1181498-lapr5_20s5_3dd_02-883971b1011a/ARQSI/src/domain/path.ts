import { AggregateRoot } from "../core/domain/AggregateRoot";
import { UniqueEntityID } from "../core/domain/UniqueEntityID";
import { Result } from "../core/logic/Result";
import { Guard } from "../core/logic/Guard";
import { PathNode } from "./pathNode";
import { PathId } from "./pathId";

/**
 * Properties of Path
 */
interface PathProps {
    key: number;
    pathNodes: PathNode[];
}

export class Path extends AggregateRoot<PathProps> {
  get id (): UniqueEntityID {
    return this._id;
  }

  get pathId (): PathId {
    return PathId.create(this.id);
  }

  get key (): number {
    return this.props.key;
  }

  get pathNodes (): PathNode[] {
    return this.props.pathNodes;
  }

  /**
   * Path constructor
   * @param props Path properties
   * @param id Path id 
   */
  private constructor (props: PathProps, id?: UniqueEntityID) {
    super(props, id);
  }

  /**
   * Create Path from properties and id
   * @param props Path properties
   * @param id Path id
   */
  public static create (props: PathProps, id?: UniqueEntityID): Result<Path> {
    const guardedProps = [
      { argument: props.key, argumentName: 'key' },
      { argument: props.pathNodes, argumentName: 'pathNodes' },
    ];
    const guardResult = Guard.againstNullOrUndefinedBulk(guardedProps);
    if (!guardResult.succeeded) {
      return Result.fail<Path>(guardResult.message)
    }     
    else {
      const path = new Path({
        ...props
      }, id);
      return Result.ok<Path>(path);
    }
  }
}