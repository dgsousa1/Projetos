import { Request, Response, NextFunction } from 'express';

export default interface IDriverTypeController {
  /**
   *  Controller to create a driver type
   * @param req Request with a JSON body with the Driver Type info 
   * @param res Status code and JSON body with the added driver type
   * @param next 
   */
  createDriverType(req: Request, res: Response, next: NextFunction);
  /**
    *  Controller to get all driver types
    * @param req 
    * @param res Status code and JSON body with all the driver types
    * @param next 
    */
  findAll(req: Request, res: Response, next: NextFunction);
  /**
   *  Controller to get a driver type by its ID
   * @param req Request with a query with the driver type ID
   * @param res Status code and a JSON body with the found driver type
   * @param next 
   */
  findById(req: Request, res: Response, next: NextFunction);
  /**
       *  Controller to get a driver type by its exact name
       * @param req Request with a query with the driver type name
       * @param res Status code and a JSON body with the found driver type
       * @param next 
       */
  findByExactName(req: Request, res: Response, next: NextFunction);
}