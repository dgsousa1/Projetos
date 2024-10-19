import { Request, Response, NextFunction } from 'express';

export default interface IVehicleTypeController {
  /**
    * Controller to create a vehicle type
    * @param req Request with a JSON body with the Vehicle Type info 
    * @param res Status code and JSON body with the added vehicle type
    * @param next 
    */
  createVehicleType(req: Request, res: Response, next: NextFunction);
  /**
   *  Controller to get all vehicle types
   * @param req 
   * @param res Status code and JSON body with all the vehicle types
   * @param next 
   */

  findAll(req: Request, res: Response, next: NextFunction);
  /**
     *  Controller to get a vehicle type by its ID
     * @param req Request with a query with the vehicle type ID
     * @param res Status code and a JSON body with the found vehicle type
     * @param next 
     */
  findById(req: Request, res: Response, next: NextFunction);
  /**
      * Controller to get a vehicle type by its exact name
      * @param req Request with a query with the vehicle type name
      * @param res Status code and a JSON body with the found vehicle type
      * @param next 
      */
  findByExactName(req: Request, res: Response, next: NextFunction);
}