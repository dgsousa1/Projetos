import { Service, Inject } from 'typedi';
import config from "../../config";
import IPathDTO from '../dto/IPathDTO';
import { Path } from '../domain/path';
import IPathRepo from './IRepos/IPathRepo';
import IPathService from './IServices/IPathService';
import { Result } from "../core/logic/Result";
import { PathMap } from '../mappers/PathMap';
import INodeRepo from './IRepos/INodeRepo';

@Service()
export default class PathService implements IPathService {
  /**
   * Creates PathService with Injected Repos
   * @param pathRepo PathRepo injection
   * @param nodeRepo NodeRepo injection
   */
  constructor(
    @Inject(config.repos.path.name) private pathRepo: IPathRepo,
    @Inject(config.repos.node.name) private nodeRepo: INodeRepo
  ) { }

  /**
   * Creates and saves Path into the database
   * @param pathDTO DTO with the information to be created
   * @returns PathDTO of the created Path
   */
  public async createPath(pathDTO: IPathDTO): Promise<Result<IPathDTO>> {
    try {
      const driverTypeOrError = await Path.create(pathDTO);
      if (driverTypeOrError.isFailure) {
        return Result.fail<IPathDTO>(driverTypeOrError.errorValue());
      }
      const pathResult = driverTypeOrError.getValue();
      var pathError = await this.pathRepo.save(pathResult);
      if (pathError === null) {
        return null;
      }
      const driverTypeDTOResult = PathMap.toDTO(pathResult) as IPathDTO;
      return Result.ok<IPathDTO>(driverTypeDTOResult)
    } catch (e) {
      throw e;
    }
  }

  /**
   *  Gets all Paths saved in the database
   *  @returns array of PathDTO
   */
  public async findAll(): Promise<Result<IPathDTO[]>> {
    try {
      const resultDTO = new Array();
      const pathList = await this.pathRepo.findAll();
      for (let index = 0; index < pathList.length; index++) {
        const element = pathList[index];
        const pathDTOResult = PathMap.toDTO(element) as IPathDTO;
        resultDTO.push(pathDTOResult);
      }
      return Result.ok<IPathDTO[]>(resultDTO);
    } catch (e) {
      throw e;
    }
  }

  /**
   * Gets Path saved in the database with the received key
   * @param key path key
   * @returns PathDTO of the path found
   */
  public async findByKey(key: string): Promise<Result<IPathDTO>> {
    try {
      const path = await this.pathRepo.findByKey(parseInt(key));
      const pathDTOResult = PathMap.toDTO(path) as IPathDTO;
      if (path === null) {
        return Result.fail<IPathDTO>("Path not found");
      }
      else {
        return Result.ok<IPathDTO>(pathDTOResult);
      }
    } catch (e) {
      throw e;
    }
  }

  /**
   * Gets Path saved in the database with the received key
   * @param key path key
   * @returns PathDTO of the path found, and nodes have name
   */
  public async findByKeyWithNodeNames(key: string): Promise<Result<IPathDTO>> {
    try {
      const path = await this.pathRepo.findByKey(parseInt(key));
      const pathDTOResult = PathMap.toDTO(path) as IPathDTO;
      if (path === null) {
        return Result.fail<IPathDTO>("Path not found");
      }
      else {
        var pathNodesWithNodeNames = new Array();
        var inicialNode = null;
        var finalNode = null;
        var pathNode = null;
        for (var i = 0; i < pathDTOResult.pathNodes.length; i++) {
          pathNode = pathDTOResult.pathNodes[i];
          inicialNode = await this.nodeRepo.findByDomainId(pathNode.inicialNode);
          finalNode = await this.nodeRepo.findByDomainId(pathNode.finalNode);
          pathNodesWithNodeNames.push({
            duracao: pathDTOResult.pathNodes[i].duracao,
            distancia: pathDTOResult.pathNodes[i].distancia,
            inicialNode: inicialNode.name,
            finalNode: finalNode.name
          })
        }
        var bodyJSON = {
          key: pathDTOResult.key,
          pathNodes: pathNodesWithNodeNames
        }
        return Result.ok<any>(bodyJSON);
      }
    } catch (e) {
      throw e;
    }
  }

  /**
  * Gets Paths saved in the database
  * @returns PathDTO of all the paths and nodes have name
  */
  public async findAllWithNodeNames(): Promise<Result<IPathDTO[]>> {
    try {
      const resultDTO = new Array();
      const pathList = await this.pathRepo.findAll();
      for (let index = 0; index < pathList.length; index++) {
        const element = pathList[index];
        const pathDTOResult = PathMap.toDTO(element) as IPathDTO;
        var pathNodesWithNodeNames = new Array();
        var inicialNode = null;
        var finalNode = null;
        var pathNode = null;
        for (var i = 0; i < pathDTOResult.pathNodes.length; i++) {
          pathNode = pathDTOResult.pathNodes[i];
          inicialNode = await this.nodeRepo.findByDomainId(pathNode.inicialNode);
          finalNode = await this.nodeRepo.findByDomainId(pathNode.finalNode);
          pathNodesWithNodeNames.push({
            duracao: pathDTOResult.pathNodes[i].duracao,
            distancia: pathDTOResult.pathNodes[i].distancia,
            inicialNode: inicialNode.name,
            finalNode: finalNode.name
          })
        }
        var bodyJSON = {
          key: pathDTOResult.key,
          pathNodes: pathNodesWithNodeNames
        }
        resultDTO.push(bodyJSON);
      }
      return Result.ok<IPathDTO[]>(resultDTO);
    } catch (e) {
      throw e;
    }
  }
}