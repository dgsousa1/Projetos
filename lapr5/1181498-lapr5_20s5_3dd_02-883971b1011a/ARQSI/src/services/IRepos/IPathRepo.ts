import { Repo } from "../../core/infra/Repo";
import { Path } from "../../domain/path";
import { PathId } from "../../domain/pathId";

export default interface IPathRepo extends Repo<Path> {
  /**
  * Gets all Paths saved in the database
  * @returns Array of Paths
  */
  findAll(): Promise<Path[]>;
  /**
   * Saves the received Path into the database
   * @param path Path object
   * @returns Path object
   */
  save(path: Path): Promise<Path>;
  /**
 * Gets Path with the received Id
 * @param pathId pathId
 * @returns Path object
 */
  findByDomainId(pathId: PathId | string): Promise<Path>;
  /**
  * Gets Path with the received key
  * @param key key
  * @returns Path object
  */
  findByKey(key: number): Promise<Path>;
}
