import { ValueObject } from "../core/domain/ValueObject";
import { Result } from "../core/logic/Result";
import { TextUtil } from "../utils/TextUtil";


/**
 * Properties of a Description object
 */
export interface DescriptionProps {
    description: string
}

export class Description extends ValueObject<DescriptionProps> {

    get description(): string {
        return this.props.description
    }
   
    set description(desc: string) {
        this.props.description = desc
    }

    /**
     * Description constructor
     * @param props description properties
     */
    private constructor(props: DescriptionProps) {
        super(props);
    }

    /**
     * Method that creates the description object
     * @param desc Description string
     */
    public static create(desc: string) {
        if (!TextUtil.checkLength(desc, 0, 250)) {
            return Result.fail<Description>('Invalid length for driver type description');
        }
        if (!TextUtil.validateString(desc)) {
            return Result.fail<Description>('Description null or empty');
        }

        const description = new Description({ description: desc });
        return Result.ok<Description>(description);
    }











}





