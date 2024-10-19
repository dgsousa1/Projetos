import { ValueObject } from "../core/domain/ValueObject";
import { Result } from "../core/logic/Result";
import { TextUtil } from "../utils/TextUtil";



export interface TypeNameProps {
    typeName : string
}

export class TypeName extends ValueObject<TypeNameProps> {

    get typeName(): string {
        return this.props.typeName;
    }

    set typeName(name : string){
        this.props.typeName = name;
    }

    private constructor(props: TypeNameProps){
        super(props);
    }


    public static create(name: string){
        if(!TextUtil.checkLength(name,0,20)){
            return Result.fail<TypeName>('Invalid length for type name');
        }
        if(!TextUtil.validateString(name)){
            return Result.fail<TypeName>(' type name null or empty');
        }

        const typeName = new TypeName({typeName : name});
        return Result.ok<TypeName>(typeName);
    }










 
}





