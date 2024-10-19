import { Repo } from "../../core/infra/Repo";
import { Node } from "../../domain/node";
import { NodeId } from "../../domain/nodeId";

export default interface INodeRepo extends Repo<Node> {
  findByNameAndDepotsOrReliefPoint(name: string, depot: boolean, reliefPoint: boolean, order: string) : Promise<Node[]>;
  save(node: Node): Promise<Node>;
  findAll(order: string): Promise<Node[]>;
  findDepots(order: string): Promise<Node[]>;
  findReliefPoints(order: string): Promise<Node[]>;
  findByName(name: string, order: string): Promise<Node[]>;
  findByDomainId(nodeId: NodeId | string): Promise<Node>;
  findByExactName(name : string) : Promise<Node>;
  findIdByName(name: string): Promise<string>;
  //findByIds (rolesIds: RoleId[]): Promise<Role[]>;
  //saveCollection (roles: Role[]): Promise<Role[]>;
  //removeByRoleIds (roles: RoleId[]): Promise<any>
}
