import { Request, Response, NextFunction } from 'express';
import { Inject } from 'typedi';
import config from "../../config";

import IDriverTypeController from "./IControllers/IDriverTypeController";


import { Result } from "../core/logic/Result";
import IDriverTypeService from '../services/IServices/IDriverTypeService';
import IDriverTypeDTO from '../dto/IDriverTypeDTO';

export default class DriverTypeController implements IDriverTypeController {
    /**
     * Creates a driver type controller with the injected parameters
     * @param driverTypeServiceInstance driverTypeService injected into the controller
     */
    constructor(
        @Inject(config.services.driverType.name) private driverTypeServiceInstance: IDriverTypeService
    ) { }

    /**
     *  Controller to create a driver type
     * @param req Request with a JSON body with the Driver Type info 
     * @param res Status code and JSON body with the added driver type
     * @param next 
     */
    public async createDriverType(req: Request, res: Response, next: NextFunction) {
        try {
            const driverTypeOrError = await this.driverTypeServiceInstance.createDriverType(req.body as IDriverTypeDTO) as Result<IDriverTypeDTO>;
            if (driverTypeOrError.isFailure) {
                return res.status(402).send();
            }
            const driverTypeDTO = driverTypeOrError.getValue();
            return res.status(201).json(driverTypeDTO);
        }
        catch (e) {
            return next(e);
        }
    };

    /**
     *  Controller to get all driver types
     * @param req 
     * @param res Status code and JSON body with all the driver types
     * @param next 
     */
    public async findAll(req: Request, res: Response, next: NextFunction) {
        try {
            const driverTypeList = await this.driverTypeServiceInstance.findAll() as Result<IDriverTypeDTO[]>;
            if (driverTypeList.isFailure) {
                return res.status(402).send();
            }
            const driverTypeListDTO = driverTypeList.getValue();
            return res.status(201).json(driverTypeListDTO);
        }
        catch (e) {
            return next(e);
        }
    };

    /**
     *  Controller to get a driver type by its ID
     * @param req Request with a query with the driver type ID
     * @param res Status code and a JSON body with the found driver type
     * @param next 
     */
    public async findById(req: Request, res: Response, next: NextFunction) {
        try {
            const driverTypeList = await this.driverTypeServiceInstance.findById(req.query.id.toString()) as Result<IDriverTypeDTO>;
            if (driverTypeList.isFailure) {
                return res.status(402).send();
            }
            const driverTypeListDTO = driverTypeList.getValue();
            return res.status(201).json(driverTypeListDTO);
        }
        catch (e) {
            return next(e);
        }
    };
    
    /**
        *  Controller to get a driver type by its exact name
        * @param req Request with a query with the driver type name
        * @param res Status code and a JSON body with the found driver type
        * @param next 
        */
    public async findByExactName(req: Request, res: Response, next: NextFunction) {
        try {
            const dtList = await this.driverTypeServiceInstance.findByExactName(req.query.name.toString()) as Result<IDriverTypeDTO>;
            if (dtList.isFailure) {
                return res.status(402).send();
            }
            const dtListDTO = dtList.getValue();
            return res.status(201).json(dtListDTO);
        }
        catch (e) {
            return next(e);
        }
    };

}