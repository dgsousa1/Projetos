import { Service, Inject } from 'typedi';
import config from "../../config";
import INodeDTO from '../dto/INodeDTO';
import { Node } from '../domain/node';
import INodeRepo from './IRepos/INodeRepo';
import INodeService from './IServices/INodeService';
import { Result } from "../core/logic/Result";
import { NodeMap } from '../mappers/NodeMap';
import { NodeName } from '../domain/nodeName';
import { ShortName } from '../domain/shortName';

@Service()
export default class NodeService implements INodeService {

  /**
   * Create NodeService
   * @param nodeRepo Injected INodeRepo into NodeService
   */
  constructor(
    @Inject(config.repos.node.name) private nodeRepo: INodeRepo
  ) { }

  /**
   * Creates and saves Node into the DataBase
   * @param nodeDTO INodeDTO to be created
   * @returns INodeDTO created
   */
  public async createNode(nodeDTO: INodeDTO): Promise<Result<INodeDTO>> {
    try {
      const nodeNameOrError = NodeName.create(nodeDTO.name);
      if (nodeNameOrError.isFailure) { return Result.fail<INodeDTO>(nodeNameOrError.errorValue()) }
      const shortNameOrError = ShortName.create(nodeDTO.shortName);
      if (shortNameOrError.isFailure) { return Result.fail<INodeDTO>(shortNameOrError.errorValue()) }
      const nodeOrError = await Node.create(
        {
          name: nodeNameOrError.getValue(),
          shortName: shortNameOrError.getValue(),
          latitude: nodeDTO.latitude,
          longitude: nodeDTO.longitude,
          isDepot: nodeDTO.isDepot,
          isReliefPoint: nodeDTO.isReliefPoint
        }
      );
      if (nodeOrError.isFailure) {
        return Result.fail<INodeDTO>(nodeOrError.errorValue());
      }
      const nodeResult = nodeOrError.getValue();
      await this.nodeRepo.save(nodeResult);
      const nodeDTOResult = NodeMap.toDTO(nodeResult) as INodeDTO;
      return Result.ok<INodeDTO>(nodeDTOResult)
    } catch (e) {
      throw e;
    }
  }

  /**
   * Gets all Nodes saved in the DataBase
   * @param order Order of the list of Node
   * @returns List of INodeDTO
   */
  public async findAll(order: string): Promise<Result<INodeDTO[]>> {
    try {
      const resultDTO = new Array();
      const nodeList = await this.nodeRepo.findAll(order);
      for (let index = 0; index < nodeList.length; index++) {
        const element = nodeList[index];
        const nodeDTOResult = NodeMap.toDTO(element) as INodeDTO;
        resultDTO.push(nodeDTOResult);
      }
      return Result.ok<INodeDTO[]>(resultDTO);
    } catch (e) {
      throw e;
    }
  }

  /**
   * Gets all Nodes saved in the DataBase that are depots
   * @param order Order of the list of Nodes
   * @returns List of INodeDTO
   */
  public async findDepots(order: string): Promise<Result<INodeDTO[]>> {
    try {
      const resultDTO = new Array();
      const nodeList = await this.nodeRepo.findDepots(order);
      for (let index = 0; index < nodeList.length; index++) {
        const element = nodeList[index];
        const nodeDTOResult = NodeMap.toDTO(element) as INodeDTO;
        resultDTO.push(nodeDTOResult);
      }
      return Result.ok<INodeDTO[]>(resultDTO);
    } catch (e) {
      throw e;
    }
  }

  /**
   * Gets all Nodes saved in the DataBase with received name and that are depots/relief points
   * @param name Name of the Node
   * @param depot true if it is depot
   * @param reliefPoint true if it is relief point
   * @param order Order of the list of Nodes
   * @returns List of INodeDTO
   */
  public async findByNameAndDepotsOrReliefPoint(name: string, depot: boolean, reliefPoint: boolean, order: string): Promise<Result<INodeDTO[]>> {
    try {
      //const result = new Array();
      const resultDTO = new Array();
      const nodeList = await this.nodeRepo.findByNameAndDepotsOrReliefPoint(name, depot, reliefPoint, order);
      for (let index = 0; index < nodeList.length; index++) {
        const element = nodeList[index];
        const nodeDTOResult = NodeMap.toDTO(element) as INodeDTO;
        resultDTO.push(nodeDTOResult);
      }
      return Result.ok<INodeDTO[]>(resultDTO);
    } catch (e) {
      throw e;
    }
  }

  /**
   * Gets all Nodes saved in the DataBase that are relief points
   * @param order Order of the list of Nodes
   * @returns List of INodeDTO
   */
  public async findReliefPoints(order: string): Promise<Result<INodeDTO[]>> {
    try {
      const resultDTO = new Array();
      const nodeList = await this.nodeRepo.findReliefPoints(order);
      for (let index = 0; index < nodeList.length; index++) {
        const element = nodeList[index];
        const nodeDTOResult = NodeMap.toDTO(element) as INodeDTO;
        resultDTO.push(nodeDTOResult);
      }
      return Result.ok<INodeDTO[]>(resultDTO);
    } catch (e) {
      throw e;
    }
  }

  /**
   * Gets Nodes saved in the DataBase with the received name
   * @param name Node name
   * @param order Order of the list of Nodes
   * @returns List of INodeDTO
   */
  public async findByName(name: string, order: string): Promise<Result<INodeDTO[]>> {
    try {
      const result = new Array();
      const resultDTO = new Array();
      const nodeList = await this.nodeRepo.findByName(name, order);
      for (let index = 0; index < nodeList.length; index++) {
        const element = nodeList[index];
        const nodeDTOResult = NodeMap.toDTO(element) as INodeDTO;
        resultDTO.push(nodeDTOResult);
      }
      return Result.ok<INodeDTO[]>(resultDTO);
    } catch (e) {
      throw e;
    }
  }


  /**
   * Gets Node with the received Id
   * @param nodeId NodeId
   * @returns INodeDTO
   */
  public async findById(id: string): Promise<Result<INodeDTO>> {
    try {
      const node = await this.nodeRepo.findByDomainId(id);
      const nodeDTOResult = NodeMap.toDTO(node) as INodeDTO;
      if (node === null) {
        return Result.fail<INodeDTO>("Node not found");
      }
      else {
        return Result.ok<INodeDTO>(nodeDTOResult);
      }
    } catch (e) {
      throw e;
    }
  }

  /**
   * Gets Nodes saved in the DataBase with the received name
   * @param name Node name
   * @param order Order of the list of Nodes
   * @returns List of INodeDTO
   */
  public async findIdByName(name: string): Promise<Result<string>> {
    try {
      const node = await this.nodeRepo.findIdByName(name);
      if (node === null) {
        return Result.fail<string>("Node not found");
      }
      else {
        return Result.ok<string>(node);
      }
    } catch (e) {
      throw e;
    }
  }

  /**
   * Gets Node saved in the DataBase with the exact name received
   * @param name Node name
   * @returns INodeDTO
   */
  public async findByExactName(name: string): Promise<Result<INodeDTO>> {
    try {
      const node = await this.nodeRepo.findByExactName(name);
      const nodeDTOResult = NodeMap.toDTO(node) as INodeDTO;
      if (node === null) {
        return Result.fail<INodeDTO>("Node not found");
      }
      else {
        return Result.ok<INodeDTO>(nodeDTOResult);
      }
    } catch (e) {
      throw e;
    }
  }
}
