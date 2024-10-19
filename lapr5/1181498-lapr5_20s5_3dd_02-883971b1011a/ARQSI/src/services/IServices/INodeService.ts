import { Result } from "../../core/logic/Result";
import INodeDTO from "../../dto/INodeDTO";


export default interface INodeService {
  /**
   * Creates and saves Node into the DataBase
   * @param nodeDTO INodeDTO to be created
   * @returns INodeDTO created
   */
  createNode(nodeDTO: INodeDTO): Promise<Result<INodeDTO>>;

  /**
   * Gets all Nodes saved in the DataBase
   * @param order Order of the list of Node
   * @returns List of INodeDTO
   */
  findAll(order: string): Promise<Result<INodeDTO[]>>;

  /**
   * Gets all Nodes saved in the DataBase that are depots
   * @param order Order of the list of Nodes
   * @returns List of INodeDTO
   */
  findDepots(order: string): Promise<Result<INodeDTO[]>>;

  /**
   * Gets all Nodes saved in the DataBase with received name and that are depots/relief points
   * @param name Name of the Node
   * @param depot true if it is depot
   * @param reliefPoint true if it is relief point
   * @param order Order of the list of Nodes
   * @returns List of INodeDTO
   */
  findByNameAndDepotsOrReliefPoint(name :string, depot : boolean, reliefPoint : boolean, order : string): Promise<Result<INodeDTO[]>>;

  /**
   * Gets all Nodes saved in the DataBase that are relief points
   * @param order Order of the list of Nodes
   * @returns List of INodeDTO
   */
  findReliefPoints(order: string): Promise<Result<INodeDTO[]>>;

  /**
   * Gets Nodes saved in the DataBase with the received name
   * @param name Node name
   * @param order Order of the list of Nodes
   * @returns List of INodeDTO
   */
  findByName(name: string, order: string): Promise<Result<INodeDTO[]>>;

  /**
   * Gets Node with the received Id
   * @param nodeId NodeId
   * @returns INodeDTO
   */
  findById(id: string): Promise<Result<INodeDTO>>;

  /**
   * Gets Nodes saved in the DataBase with the received name
   * @param name Node name
   * @param order Order of the list of Nodes
   * @returns List of INodeDTO
   */
  findIdByName(name: string): Promise<Result<string>>;

  /**
   * Gets Node saved in the DataBase with the exact name received
   * @param name Node name
   * @returns INodeDTO
   */
  findByExactName(name: string): Promise<Result<INodeDTO>>; 
}