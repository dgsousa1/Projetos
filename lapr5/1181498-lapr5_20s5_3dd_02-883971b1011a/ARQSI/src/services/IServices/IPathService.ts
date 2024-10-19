import { Result } from "../../core/logic/Result";
import IPathDTO from "../../dto/IPathDTO";


export default interface IPathService {
  /**
   * Creates and saves Path into the database
   * @param pathDTO DTO with the information to be created
   * @returns PathDTO of the created Path
   */
  createPath(pathDTO: IPathDTO): Promise<Result<IPathDTO>>;
  /**
  *  Gets all Paths saved in the database
  *  @returns array of PathDTO
  */
  findAll(): Promise<Result<IPathDTO[]>>;
  /**
   * Gets Path saved in the database with the received key
   * @param key path key
   * @returns PathDTO of the path found
   */
  findByKey(key: string): Promise<Result<IPathDTO>>;
  /**
   * Gets Path saved in the database with the received key
   * @param key path key
   * @returns PathDTO of the path found, and nodes have name
   */
  findByKeyWithNodeNames(key: string): Promise<Result<any>>;
  /**
   * Gets Paths saved in the database
   * @returns PathDTO of all the paths and nodes have name
   */
  findAllWithNodeNames(): Promise<Result<any[]>>;
}