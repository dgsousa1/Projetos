import { Request, Response, NextFunction } from 'express';

export default interface IPathController {
  /**
   * Creates and adds a Path to the database
   * @param req Request with the Path info in a JSON body
   * @param res Status code and JSON body with the added Path
   * @returns Response
   */
  createPath(req: Request, res: Response, next: NextFunction);
  /**
     * Lists all Paths in the database
     * @param res Status code and JSON body with all the Paths
     */
  findAll(req: Request, res: Response, next: NextFunction);
  /**
    *  Gets the Path with the received key
    * @param req Request with query containing a key
    * @param res Status code and a JSON object with the Path
    */
  findByKey(req: Request, res: Response, next: NextFunction);
  /**
    *  Gets the Path with the received key
    * @param req Request with query containing a key
    * @param res Status code and a JSON object with the Path and named nodes
    */
  findByKeyWithNodeNames(req: Request, res: Response, next: NextFunction);
  /**
    * Lists all Paths in the database
    * @param res Status code and JSON body with all the Paths
    */
  findAllWithNodeNames(req: Request, res: Response, next: NextFunction);
}