import { ValueObject } from "../core/domain/ValueObject";
import { UniqueEntityID } from "../core/domain/UniqueEntityID";

export class DriverTypeId extends ValueObject<any> {

  get id (): UniqueEntityID {
    return this.id;
  }

  /**
   * Creates a DriverTypeId
   * @param id  UniqueEntityID
   * @returns DriverTypeId object
   */
  public static create (id?: UniqueEntityID): DriverTypeId {
    return new DriverTypeId(id);
  }
}