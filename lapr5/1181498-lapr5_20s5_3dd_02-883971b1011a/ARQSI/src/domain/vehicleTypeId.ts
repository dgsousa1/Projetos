import { ValueObject } from "../core/domain/ValueObject";
import { UniqueEntityID } from "../core/domain/UniqueEntityID";


export class VehicleTypeId extends ValueObject<any> {

    get id(): UniqueEntityID {
        return this.id;
    }

    /**
     * Creates a VehicleTypeId
     * @param id  UniqueEntityID
     * @returns VehicleTypeId object
     */
    public static create(id?: UniqueEntityID): VehicleTypeId {
        return new VehicleTypeId(id);
    }

}