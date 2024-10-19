import { ValueObject } from "../core/domain/ValueObject";
import { Result } from "../core/logic/Result";
import { TextUtil } from "../utils/TextUtil";

/**
 * NodeName properties
 */
export interface NodeNameProps {
    nodeName : string
}

export class NodeName extends ValueObject<NodeNameProps> {

    get nodeName(): string {
        return this.props.nodeName;
    }

    set nodeName(name : string){
        this.props.nodeName = name;
    }

    /**
     * NodeName contructor
     * @param props Node properties
     */
    private constructor(props: NodeNameProps){
        super(props);
    }

    /**
     * Create NodeName
     * @param name Node Name
     */
    public static create(name: string){
        if(!TextUtil.checkLength(name,0,20)){
            return Result.fail<NodeName>('Invalid length for node name');
        }
        if(!TextUtil.validateString(name)){
            return Result.fail<NodeName>('node name null or empty');
        }
        const nodeName = new NodeName({nodeName : name});
        return Result.ok<NodeName>(nodeName);
    } 
}





