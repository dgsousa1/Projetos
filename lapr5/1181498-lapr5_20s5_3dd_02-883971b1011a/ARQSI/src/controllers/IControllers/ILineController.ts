import { Request, Response, NextFunction } from 'express';

export default interface ILineController  {
  /**
   * Creates and adds to the DataBase the received Line
   * @param req Request with a JSON body with the Line info
   * @param res Status code and JSON body with the added Line
   * @param next 
   * @returns Response
   */
  createLine(req: Request, res: Response, next: NextFunction);

  /**
   * Lists all Lines with the received order
   * @param req Request JSON body with the list order
   * @param res Status code and JSON body with the list of all Lines in order
   * @param next 
   * @returns Response
   */
  findAll(req: Request, res: Response, next: NextFunction);

  /**
   * Lists Lines with the received name
   * @param req Request JSON body with the name of the Lines
   * @param res Status code and JSON body with the list of resulting Lines
   * @param next 
   * @returns Response
   */
  findByName(req: Request, res: Response, next: NextFunction);

  /**
   * Gets Lines from Id
   * @param req Request JSON body with the id of a Lines
   * @param res Status code and JSON body with the resulting Lines
   * @param next 
   * @returns Response
   */
  findById(req: Request, res: Response, next: NextFunction);

  /**
   * Lists Paths from Line with the received name
   * @param req Request JSON body with the name of the Line
   * @param res Status code and JSON body with the list of resulting Paths from the Line
   * @param next 
   * @returns Response
   */
  findPathsFromLineName(req: Request, res: Response, next: NextFunction);

  /**
   * Lists coordinates from all Nodes in a Line with the received name
   * @param req Request JSON body with the name of the Line
   * @param res Status code and JSON body with the list of resulting coordinates 
   * from all Nodes in a Line
   * @param next 
   * @returns Response
   */
  findCoordinatesByLineId(req: Request, res: Response, next: NextFunction);

  /**
   * Lists Paths from Line with the received name
   * @param req Request JSON body with the name of the Line
   * @param res Status code and JSON body with the list of resulting Paths from the Line,
   * showing names instead of ids
   * @param next 
   * @returns Response
   */
  findPathsFromLineWithNames(req: Request, res: Response, next: NextFunction);

  /**
   * Lists all Lines without Ids with the received order
   * @param req Request JSON body with the list order
   * @param res Status code and JSON body with the list of all Lines in order
   * @param next 
   * @returns Response
   */
  findAllWithoutId(req: Request, res: Response, next: NextFunction);

  /**
   * Lists Lines without Ids with the received name
   * @param req Request JSON body with the name of the Lines
   * @param res Status code and JSON body with the list of resulting Lines
   * @param next 
   * @returns Response
   */
  findByNameWithoutId(req: Request, res: Response, next: NextFunction);

  /**
   * Gets Lines without Ids from Id
   * @param req Request JSON body with the id of a Lines
   * @param res Status code and JSON body with the resulting Lines
   * @param next 
   * @returns Response
   */
  findByIdWithoutId(req: Request, res: Response, next: NextFunction);
}