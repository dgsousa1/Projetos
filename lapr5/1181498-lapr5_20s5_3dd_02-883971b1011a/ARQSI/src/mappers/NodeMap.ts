import { Mapper } from "../core/infra/Mapper";
import { Document, Model } from 'mongoose';
import { INodePersistence } from "../dataschema/INodePersistence";
import INodeDTO from "../dto/INodeDTO";
import { Node } from "../domain/node";
import { UniqueEntityID } from "../core/domain/UniqueEntityID";
import { NodeName } from "../domain/nodeName";
import { ShortName } from "../domain/shortName";

export class NodeMap extends Mapper<Node> {

  /**
   * Converts Object Node to NodeDTO
   * @param node Object Node
   * @returns NodeDTO
   */
  public static toDTO(node: Node): INodeDTO {
    return {
      nodeId: node.id.toString(),
      name: node.name,
      shortName: node.shortName,
      latitude: node.latitude,
      longitude: node.longitude,
      isDepot: node.isDepot,
      isReliefPoint: node.isReliefPoint,
    } as INodeDTO;
  }

  /**
   * Creates Object Node from INodePersistence
   * @param node INodePersistence
   * @returns Object Node
   */
  public static toDomain(node: any | Model<INodePersistence & Document>): Node {
    const nodeNameOrError = NodeName.create(node.name);
    if (nodeNameOrError.isFailure) { return null }
    const shortNameOrError = ShortName.create(node.shortName);
    if (shortNameOrError.isFailure) { return null }
    const nodeOrError = Node.create(
      {
        name: nodeNameOrError.getValue(),
        shortName: shortNameOrError.getValue(),
        latitude: node.latitude,
        longitude: node.longitude,
        isDepot: node.isDepot,
        isReliefPoint: node.isReliefPoint
      },
      new UniqueEntityID(node.nodeId)
    );
    nodeOrError.isFailure ? console.log(nodeOrError.error) : '';
    return nodeOrError.isSuccess ? nodeOrError.getValue() : null;
  }

  /**
   * Converts Object Node to INodePersistence
   * @param node Object Node
   * @returns INodePersistence
   */
  public static toPersistence(node: Node): any {
    return {
      nodeId: node.id.toString(),
      name: node.name,
      shortName: node.shortName,
      latitude: node.latitude,
      longitude: node.longitude,
      isDepot: node.isDepot,
      isReliefPoint: node.isReliefPoint
    }
  }
}