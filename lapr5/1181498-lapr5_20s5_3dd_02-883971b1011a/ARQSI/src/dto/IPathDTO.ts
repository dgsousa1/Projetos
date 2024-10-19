import { PathNode } from "../domain/pathNode";

export default interface IPathDTO {
    key: number;
    pathNodes: PathNode[];
  }