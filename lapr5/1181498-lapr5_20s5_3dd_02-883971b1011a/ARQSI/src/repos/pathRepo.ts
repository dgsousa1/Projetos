import Container, { Service, Inject } from 'typedi';
import IPathRepo from "../services/IRepos/IPathRepo";
import { Path } from "../domain/path";
import { PathId } from "../domain/pathId";
import { PathMap } from "../mappers/PathMap";
import { Document, Model } from 'mongoose';
import { IPathPersistence } from '../dataschema/IPathPersistence';
import IPathDTO from '../dto/IPathDTO';
import NodeRepo from './nodeRepo';

@Service()
export default class PathRepo implements IPathRepo {
  private models: any;

  /**
   * Creates PathRepo
   * @param pathSchema Injected Path Schema into PathRepo
   */
  constructor(
    @Inject('pathSchema') private pathSchema: Model<IPathPersistence & Document>,
  ) { }

  private createBaseQuery(): any {
    return {
      where: {},
    }
  }

  /**
   * Validates if a Path with the received Id exists
   * @param pathId pathId
   * @returns true if exists, false if not
   */
  public async exists(pathId: PathId | string): Promise<boolean> {
    const idX = pathId instanceof PathId ? (<PathId>pathId).id.toValue() : pathId;
    const query = { domainId: idX };
    const nodeDocument = await this.pathSchema.findOne(query);
    return !!nodeDocument === true;
  }

  /**
   * Saves the received Path into the database
   * @param path Path object
   * @returns Path object
   */
  public async save(path: Path): Promise<Path> {
    const query = { domainId: path.pathId };
    const pathDocument = await this.pathSchema.findOne(query);
    try {
      if (pathDocument === null) {
        const rawPath: any = PathMap.toPersistence(path);
        if (await this.validatePath(rawPath)) {
          const pathCreated = await this.pathSchema.create(rawPath);
          return PathMap.toDomain(pathCreated);
        } else {
          return null;
        }
      } else {
        await pathDocument.save();
        return path;
      }
    } catch (err) {
      throw err;
    }
  }

  /**
   *  Validates the path by checking if the given nodes exist
   * @param path PathDTO
   * @returns true if valid, else false
   */
  private async validatePath(path: IPathDTO) {
    var validationError = true;
    const nodeRepo = Container.get(NodeRepo);
    const pathNodesList = new Array();
    var pathInicialNodeError = null;
    var pathFinalNodeError = null;
    for (let index = 0; index < path.pathNodes.length; index++) {
      var inicialId = path.pathNodes[index].inicialNode;
      pathInicialNodeError = await nodeRepo.findByDomainId(path.pathNodes[index].inicialNode);
      pathFinalNodeError = await nodeRepo.findByDomainId(path.pathNodes[index].finalNode);
      if (!((pathInicialNodeError != null) && (pathFinalNodeError != null))) {
        validationError = false;
        break;
      }
    }
    return validationError;
  }

  /**
   * Gets Path with the received Id
   * @param pathId pathId
   * @returns Path object
   */
  public async findByDomainId(pathId: PathId | string): Promise<Path> {
    const query = { pathId: pathId };
    const pathRecord = await this.pathSchema.findOne(query);
    if (pathRecord != null) {
      return PathMap.toDomain(pathRecord);
    }
    else
      return null;
  }

  /**
   * Gets Path with the received key
   * @param key key
   * @returns Path object
   */
  public async findByKey(key: number): Promise<Path> {
    const query = { key: key };
    const pathRecord = await this.pathSchema.findOne(query);
    if (pathRecord != null) {
      return PathMap.toDomain(pathRecord);
    }
    else
      return null;
  }

  /**
   * Gets all Paths saved in the database
   * @returns Array of Paths
   */
  public async findAll(): Promise<Path[]> {
    const query = {};
    return this.executeQuery(query);
  }

  /**
   * Executes received query
   * @param query Query to be executed
   * @returns Array of Paths or null if there's an error
   */
  private async executeQuery(query: any): Promise<Path[]> {
    const list = await this.pathSchema.find(query);
    const result = new Array();
    if (list != null) {
      for (let index = 0; index < list.length; index++) {
        var p = await PathMap.toDomain(list[index]);
        result.push(p);
      }
      return result;
    }
    else
      return null;
  }
}