import { Repo } from "../../core/infra/Repo";
import { Line } from "../../domain/line";
import { LineId } from "../../domain/lineId";

export default interface ILineRepo extends Repo<Line> {
  /**
   * Saves the received Line into the DataBase
   * @param line Object Line
   * @returns Object Line
   */
  save(line: Line): Promise<Line>;

  /**
   * Gets all Lines saved in the DataBase
   * @param order Order of the list of Lines
   * @returns List of Lines
   */
  findAll(order: string): Promise<Line[]>;

  /**
   * Gets Lines saved in the DataBase with the received name
   * @param name Lines name
   * @param order Order of the list of Lines
   * @returns List of Lines
   */
  findByName(name : string, order : string) : Promise<Line[]>;

  /**
   * Gets Lines saved in the DataBase with the exact name received
   * @param name Lines name
   * @returns Object Lines
   */
  findByExactName(name : string) : Promise<Line>;

  /**
   * Gets Line with the received Id
   * @param lineId LineId
   * @returns Object Line
   */
  findByDomainId(lineId: LineId | string): Promise<Line>;
}
