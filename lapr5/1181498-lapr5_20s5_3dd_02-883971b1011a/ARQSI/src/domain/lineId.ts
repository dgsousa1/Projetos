import { ValueObject } from "../core/domain/ValueObject";
import { UniqueEntityID } from "../core/domain/UniqueEntityID";

export class LineId extends ValueObject<any> {

  get id (): UniqueEntityID {
    return this.id;
  }

  /**
   * Create Line Id
   */
  public static create (id?: UniqueEntityID): LineId {
    return new LineId(id);
  }
}