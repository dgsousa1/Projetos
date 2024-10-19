import { ValueObject } from "../core/domain/ValueObject";
import { Result } from "../core/logic/Result";
import { TextUtil } from "../utils/TextUtil";



export interface ShortNameProps {
    shortName : string
}

export class ShortName extends ValueObject<ShortNameProps> {

    get shortName(): string {
        return this.props.shortName
    }

    set shortName(name : string){
        this.props.shortName = name
    }

    private constructor(props: ShortNameProps){
        super(props);
    }


    public static create(name: string){
        if(!TextUtil.checkLength(name,0,20)){
            return Result.fail<ShortName>('Invalid length for short name');
        }
        if(!TextUtil.validateString(name)){
            return Result.fail<ShortName>('short name null or empty');
        }

        const shortName = new ShortName({shortName : name});
        return Result.ok<ShortName>(shortName);
    }










 
}





