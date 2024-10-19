import { Service, Inject } from 'typedi';
import INodeRepo from "../services/IRepos/INodeRepo";
import { Node } from "../domain/node";
import { NodeId } from "../domain/nodeId";
import { NodeMap } from "../mappers/NodeMap";
import { Document, Model, MongooseFilterQuery } from 'mongoose';
import { INodePersistence } from '../dataschema/INodePersistence';

@Service()
export default class NodeRepo implements INodeRepo {
  /**
   * Creates NodeRepo
   * @param nodeSchema Injected INodePersistence into NodeRepo
   */
  constructor(
    @Inject('nodeSchema') private nodeSchema: Model<INodePersistence & Document>,
  ) { }

  /**
 * Executes received query
 * @param query Query to be executed
 * @param order Order of the list of Nodes
 * @returns Query result or null in case of error
 */
  private async executeQuery(query: any, order: string): Promise<Node[]> {
    const nodeList = await this.nodeSchema.find(query);
    const result = new Array();
    if (nodeList != null) {
      nodeList.forEach(element => {
        result.push(NodeMap.toDomain(element));
      });
      return await this.orderList(result, order);
    }
    else
      return null;
  }

  /**
   * Orders List
   * @param list List without order 
   * @param order Order of the list of Nodes
   * @returns List in order
   */
  private async orderList(list: Node[], order: string): Promise<Node[]> {
    //source: https://flaviocopes.com/how-to-sort-array-of-objects-by-property-javascript/
    switch (order) {
      case 'name':
        list.sort((a, b) => (a.name > b.name) ? 1 : -1);
        return list;
      case 'id':
        list.sort((a, b) => (a.id > b.id) ? 1 : -1);
        return list;
      default:
        return list;
    }
  }

  /**
   * Validates if a Node with the received Id exists
   * @param nodeId NodeId
   * @returns true if Node exists in the DataBase and false otherwise
   */
  public async exists(nodeId: NodeId | string): Promise<boolean> {
    const idX = nodeId instanceof NodeId ? (<NodeId>nodeId).id.toValue() : nodeId;
    const query = { domainId: idX };
    const nodeDocument = await this.nodeSchema.findOne(query);
    return !!nodeDocument === true;
  }

  /**
   * Saves the received Node into the DataBase
   * @param node Object Node
   * @returns Object Node
   */
  public async save(node: Node): Promise<Node> {
    const query = { domainId: node.nodeId };
    const nodeDocument = await this.nodeSchema.findOne(query);
    try {
      if (nodeDocument === null) {
        const rawNode: any = NodeMap.toPersistence(node);
        const nodeCreated = await this.nodeSchema.create(rawNode);
        return NodeMap.toDomain(nodeCreated);
      } else {
        nodeDocument.name = node.name;
        nodeDocument.shortName = node.shortName;
        await nodeDocument.save();
        return node;
      }
    } catch (err) {
      throw err;
    }
  }

  /**
   * Gets Node with the received Id
   * @param nodeId NodeId
   * @returns Object Node
   */
  public async findByDomainId(nodeId: NodeId | string): Promise<Node> {
    const query = { nodeId: nodeId.toString() };
    const nodeRecord = await this.nodeSchema.findOne(query);
    if (nodeRecord != null) {
      return NodeMap.toDomain(nodeRecord);
    }
    else
      return null;
  }

  /**
   * Gets all Nodes saved in the DataBase
   * @param order Order of the list of Node
   * @returns List of Node
   */
  public async findAll(order: string): Promise<Node[]> {
    const query = {};
    return this.executeQuery(query, order);
  }

  /**
   * Gets all Nodes saved in the DataBase that are depots
   * @param order Order of the list of Nodes
   * @returns List of Nodes
   */
  public async findDepots(order: string): Promise<Node[]> {
    const query = { isDepot: true };
    return this.executeQuery(query, order);
  }

  /**
   * Gets all Nodes saved in the DataBase with received name and that are depots/relief points
   * @param name Name of the Node
   * @param depot true if it is depot
   * @param reliefPoint true if it is relief point
   * @param order Order of the list of Nodes
   * @returns List of Nodes
   */
  public async findByNameAndDepotsOrReliefPoint(name: string, depot: boolean, reliefPoint: boolean, order: string): Promise<Node[]> {
    var exp = new RegExp("^" + name);
    console.log(depot + " " + reliefPoint);
    const query = {
      name: exp,
      isDepot: depot,
      isReliefPoint: reliefPoint
    };
    return this.executeQuery(query, order);
  }

  /**
   * Gets all Nodes saved in the DataBase that are relief points
   * @param order Order of the list of Nodes
   * @returns List of Nodes
   */
  public async findReliefPoints(order: string): Promise<Node[]> {
    const query = { isReliefPoint: true };
    return this.executeQuery(query, order);
  }

  /**
   * Gets Nodes saved in the DataBase with the received name
   * @param name Node name
   * @param order Order of the list of Nodes
   * @returns List of Nodes
   */
  public async findByName(name: string, order: string): Promise<Node[]> {
    var exp = new RegExp("^" + name);
    const query = { name: exp };
    return this.executeQuery(query, order);
  }

  /**
   * Gets Node saved in the DataBase with the exact name received
   * @param name Node name
   * @returns Object Node
   */
  public async findByExactName(name: string): Promise<Node> {
    const query = { name: name };
    const nodeRecord = await this.nodeSchema.findOne(query);
    if (nodeRecord != null) {
      return NodeMap.toDomain(nodeRecord);
    }
    else
      return null;
  }

  /**
   * Gets NodeId saved in the DataBase with the received name
   * @param name Node name
   * @returns Object Node
   */
  public async findIdByName(name: string): Promise<string> {
    const query = { name: name };
    const nodeRecord = await this.nodeSchema.findOne(query);
    if (nodeRecord != null) {
      return nodeRecord.nodeId;
    }
    else
      return null;
  }
}