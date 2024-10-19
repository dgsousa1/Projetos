import { Request, Response, NextFunction } from 'express';
import { Inject } from 'typedi';
import config from "../../config";



import { Result } from "../core/logic/Result";
import IVehicleTypeService from '../services/IServices/IVehicleTypeService';
import IVehicleTypeController from './IControllers/IVehicleTypeController';
import IVehicleTypeDTO from '../dto/IVehicleTypeDTO';


export default class VehicleTypeController implements IVehicleTypeController {
    /**
     * Creates a vehice type controller with the injected parameters
     * @param vehicleTypeServiceInstance vehicleTypeService injected into the controller
     */
    constructor(
        @Inject(config.services.vehicleType.name) private vehicleTypeServiceInstance: IVehicleTypeService
    ) { }

    /**
     * Controller to create a vehicle type
     * @param req Request with a JSON body with the Vehicle Type info 
     * @param res Status code and JSON body with the added vehicle type
     * @param next 
     */
    public async createVehicleType(req: Request, res: Response, next: NextFunction) {
        try {
            const typeOrError = await this.vehicleTypeServiceInstance.createVehicleType(req.body as IVehicleTypeDTO) as Result<IVehicleTypeDTO>;
            if (typeOrError.isFailure) {
                return res.status(402).send();
            }
            const vehicleTypeDTO = typeOrError.getValue();
            return res.status(201).json(vehicleTypeDTO);
        }
        catch (e) {
            return next(e);
        }
    };

    /**
    *  Controller to get all vehicle types
    * @param req 
    * @param res Status code and JSON body with all the vehicle types
    * @param next 
    */
    public async findAll(req: Request, res: Response, next: NextFunction) {
        try {
            const nodeList = await this.vehicleTypeServiceInstance.findAll() as Result<IVehicleTypeDTO[]>;
            if (nodeList.isFailure) {
                return res.status(402).send();
            }
            const nodeListDTO = nodeList.getValue();
            return res.status(201).json(nodeListDTO);
        }
        catch (e) {
            return next(e);
        }
    };

    /**
     *  Controller to get a vehicle type by its ID
     * @param req Request with a query with the vehicle type ID
     * @param res Status code and a JSON body with the found vehicle type
     * @param next 
     */
    public async findById(req: Request, res: Response, next: NextFunction) {
        try {
            const nodeList = await this.vehicleTypeServiceInstance.findById(req.query.id.toString()) as Result<IVehicleTypeDTO>;
            if (nodeList.isFailure) {
                return res.status(402).send();
            }
            const nodeListDTO = nodeList.getValue();
            return res.status(201).json(nodeListDTO);
        }
        catch (e) {
            return next(e);
        }
    };

    /**
     * Controller to get a vehicle type by its exact name
     * @param req Request with a query with the vehicle type name
     * @param res Status code and a JSON body with the found vehicle type
     * @param next 
     */
    public async findByExactName(req: Request, res: Response, next: NextFunction) {
        try {
            const vtList = await this.vehicleTypeServiceInstance.findByExactName(req.query.name.toString()) as Result<IVehicleTypeDTO>;
            if (vtList.isFailure) {
                return res.status(402).send();
            }
            const vtListDTO = vtList.getValue();
            return res.status(201).json(vtListDTO);
        }
        catch (e) {
            return next(e);
        }
    };
}