import { Mapper } from "../core/infra/Mapper";
import { Document, Model } from 'mongoose';
import { IPathPersistence } from "../dataschema/IPathPersistence";
import IPathDTO from "../dto/IPathDTO";
import { Path } from "../domain/path";
import { UniqueEntityID } from "../core/domain/UniqueEntityID";
import { Container } from "typedi";
import NodeRepo from "../repos/nodeRepo";
import { PathNode } from "../domain/pathNode";

export class PathMap extends Mapper<Path> {

  /**
   * Converts Path object to PathDTO
   * @param path path object
   * @returns PathDTO
   */
  public static toDTO(path: Path): IPathDTO {
    return {
        pathId: path.id.toString(),
        key: path.key,
        pathNodes: path.pathNodes,
    } as IPathDTO;
  }

  /**
   * Creates Path object from IPathPersistence
   * @param path IPathPersistence
   * @returns Path object
   */
  public static async toDomain(path: any | Model<IPathPersistence & Document>): Promise<Path> {
    var validationError = false;
    const nodeRepo = Container.get(NodeRepo);
    const pathNodesList = new Array();
    var pathInicialNodeError = null;
    var pathFinalNodeError = null;    
    for(let index = 0; index < path.pathNodes.length; index++){
      pathInicialNodeError = await nodeRepo.findByDomainId(path.pathNodes[index].inicialNode);
      pathFinalNodeError = await nodeRepo.findByDomainId(path.pathNodes[index].finalNode);
      if((pathInicialNodeError != null) && (pathFinalNodeError != null)){
        pathNodesList.push(PathNode.create(
          {duracao: path.pathNodes[index].duracao,
          distancia: path.pathNodes[index].distancia,
          inicialNode: pathInicialNodeError.nodeId,
          finalNode: pathFinalNodeError.nodeId,
          }))
      }else{
        validationError = true;
        break;
      }
    }
    if(validationError){
      return null;
    }else{
      const pathOrError = Path.create(
        {
          key: path.key,
          pathNodes : path.pathNodes,
        },
        new UniqueEntityID(path.pathId)
      );
      pathOrError.  isFailure ? console.log(pathOrError.error) : '';
      return pathOrError.isSuccess ? pathOrError.getValue() : null;
    } 
  }

  /**
   * Converts Path object to IPathPersistence
   * @param path Path object
   * @returns IPathPersistence
   */
  public static toPersistence(path: Path): any {
    return {
      pathId: path.id.toString(),
      key: path.key,
      pathNodes: path.pathNodes
    }
  }
}