import { ValueObject } from "../core/domain/ValueObject";
import { UniqueEntityID } from "../core/domain/UniqueEntityID";

export class PathId extends ValueObject<any> {

  get id (): UniqueEntityID {
    return this.id;
  }

  /**
   * Create PathId
   * @param id path id
   */
  public static create (id?: UniqueEntityID): PathId {
    return new PathId(id);
  }
}