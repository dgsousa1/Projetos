import { Request, Response, NextFunction } from 'express';

export default interface INodeController {
  /**
   * Creates and adds to the DataBase the received Node
   * @param req Resquest with a JSON body with the Node info
   * @param res Status code and JSON body with the added Node
   * @param next 
   * @returns Response
   */
  createNode(req: Request, res: Response, next: NextFunction);

  /**
   * Lists all Nodes with the received order
   * @param req Request JSON body with the list order
   * @param res Status code and JSON body with the list of all Nodes in order
   * @param next 
   * @returns Response
   */
  findAll(req: Request, res: Response, next: NextFunction);

  /**
   * Lists nodes with the received name
   * @param req Request JSON body with the name of the Nodes
   * @param res Status code and JSON body with the list of resulting Nodes
   * @param next 
   * @returns Response
   */
  findByName(req: Request, res: Response, next: NextFunction);

  /**
   * Gets the Node with the received name
   * @param req Request JSON body with the name of a Node
   * @param res Status code and JSON body with the resulting Node
   * @param next 
   * @returns Response
   */
  findByExactName(req: Request, res: Response, next: NextFunction);

  /**
   * Gets Id from the Node with the received name
   * @param req Request JSON body with the name of a Node
   * @param res Status code and JSON body with the resulting NodeId
   * @param next 
   * @returns Response
   */
  findIdByName(req: Request, res: Response, next: NextFunction);

  /**
   * Gets Nodes from Id
   * @param req Request JSON body with the id of a Node
   * @param res Status code and JSON body with the resulting Node
   * @param next 
   * @returns Response
   */
  findById(req: Request, res: Response, next: NextFunction);

  /**
   * List all Nodes that are depots
   * @param req Request JSON body with the list order
   * @param res Status code and JSON body with the list of all Nodes that are depots in order
   * @param next 
   * @returns Response
   */
  findDepots(req: Request, res: Response, next: NextFunction);

  /**
   * List all Nodes that are relief points
   * @param req Request JSON body with the list order
   * @param res Status code and JSON body with the list of all Nodes that are 
   * relief points in order
   * @param next 
   * @returns Response
   */
  findReliefPoints(req: Request, res: Response, next: NextFunction);


  /**
   * List all Nodes with the received name that are depots/relief points
   * @param req Request JSON body with the name of the node
   * @param res Status code and JSON body with the list of all Nodes with the 
   * received name that are depots/relief points
   * @param next 
   * @returns Response
   */
  findByNameAndDepotsOrReliefPoint(req: Request, res: Response, next: NextFunction);
}