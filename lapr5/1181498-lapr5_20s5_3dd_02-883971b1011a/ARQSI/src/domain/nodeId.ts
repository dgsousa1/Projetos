import { ValueObject } from "../core/domain/ValueObject";
import { UniqueEntityID } from "../core/domain/UniqueEntityID";

export class NodeId extends ValueObject<any> {

  get id (): UniqueEntityID {
    return this.id;
  }

  /**
   * Create NodeId
   * @param id Node Id
   */
  public static create (id?: UniqueEntityID): NodeId {
    return new NodeId(id);
  }
}