import { Entity } from "../core/domain/Entity";
import { UniqueEntityID } from "../core/domain/UniqueEntityID";
import { Guard } from "../core/logic/Guard";
import { Result } from "../core/logic/Result";
import { NodeId } from "./nodeId";

/**
 * Properties of PathNode
 */
interface PathNodeProps {
  duracao: Number;
  distancia: Number;
  inicialNode: NodeId;
  finalNode: NodeId;
}

export class PathNode extends Entity<PathNodeProps> {
  get id(): UniqueEntityID {
    return this._id;
  }

  get duracao(): Number {
    return this.duracao;
  }

  get distancia(): Number {
    return this.duracao;
  }

  get inicialNode(): NodeId {
    return this.inicialNode;
  }

  get finalNode(): NodeId {
    return this.finalNode;
  }

  /**
   * PathNode constructor
   * @param props PathNode properties
   * @param id PathNode id
   */
  private constructor(props: PathNodeProps, id?: UniqueEntityID) {
    super(props, id);
  }

  /**
   * Create PathNode from properties and id
   * @param props PathNode properties
   * @param id PathNode Id
   */
  public static create(props: PathNodeProps, id?: UniqueEntityID): Result<PathNode> {
    const guardedProps = [
      { argument: props.duracao, argumentName: 'duracao' },
      { argument: props.distancia, argumentName: 'distancia' },
      { argument: props.inicialNode, argumentName: 'inicialNode' },
      { argument: props.finalNode, argumentName: 'finalNode' }
    ];
    const guardResult = Guard.againstNullOrUndefinedBulk(guardedProps);
    if (!guardResult.succeeded) {
      return Result.fail<PathNode>(guardResult.message)
    }
    else {
      const pathNode = new PathNode({
        ...props
      }, id);
      return Result.ok<PathNode>(pathNode);
    }
  }
}
