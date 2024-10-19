import { Result } from "../../core/logic/Result";
import ILineDTO from "../../dto/ILineDTO";

export default interface ILineService {
  /**
   * Creates and saves Line into the DataBase
   * @param lineDTO ILineDTO to be created
   * @returns ILineDTO created
   */
  createLine(lineDTO: ILineDTO): Promise<Result<ILineDTO>>;

  /**
   * Gets all Lines saved in the DataBase
   * @param order Order of the list of Lines
   * @returns List of Lines
   */
  findAll(order: string): Promise<Result<ILineDTO[]>>;

  /**
   * Gets Lines saved in the DataBase with the received name
   * @param name Lines name
   * @param order Order of the list of Lines
   * @returns List of Lines
   */
  findByName(name: string, order: string): Promise<Result<ILineDTO[]>>;

  /**
   * Gets Line with the received Id
   * @param lineId LineId
   * @returns Object Line
   */
  findById(id: string): Promise<Result<ILineDTO>>;

  /**
   * Lists Paths from Line with the received id
   * @param id LineId
   * @returns Paths from Line
   */
  findPathsByLineId(id: string): Promise<any>;

  /**
   * Lists coordinates from all Nodes in a Line with the received name
   * @param id LineId
   * @returns List of resulting coordinates from all Nodes in a Line
   */
  findCoordinatesByLineId(id: string): Promise<Result<any>>;

  /**
   * Lists Paths from Line with the received name
   * @param name Name of the Line
   * @returns Paths from Line with names instead of Id
   */
  findPathsFromLineWithName(id: string): Promise<Result<any>>;

  /**
   * Gets all Lines saved in the DataBase
   * @param order Order of the list of Lines
   * @returns List of Lines
   */
  findAllWithoutId(order: string): Promise<Result<any[]>>;

  /**
  * Gets Lines saved in the DataBase with the received name
  * @param name Lines name
  * @param order Order of the list of Lines
  * @returns List of Lines
  */
  findByNameWithoutId(name: string, order: string): Promise<Result<ILineDTO[]>>;

  /**
   * Gets Line with the received Id
   * @param lineId LineId
   * @returns Object Line
   */
  findByIdWithoutId(id: string): Promise<Result<ILineDTO>>;
}