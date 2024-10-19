import { Request, Response, NextFunction } from 'express';
import { Inject } from 'typedi';
import config from "../../config";

import ILineController from "./IControllers/ILineController";


import { Result } from "../core/logic/Result";
import ILineService from '../services/IServices/ILineService';
import ILineDTO from '../dto/ILineDTO';

export default class LineController implements ILineController {
    /**
     * Creates LineController
     * @param lineServiceInstance Injected LineService into the LineController 
     */
    constructor(
        @Inject(config.services.line.name) private lineServiceInstance: ILineService
    ) { }

    /**
     * Creates and adds to the DataBase the received Line
     * @param req Request with a JSON body with the Line info
     * @param res Status code and JSON body with the added Line
     * @param next 
     * @returns Response
     */
    public async createLine(req: Request, res: Response, next: NextFunction) {
        try {
            const lineOrError = await this.lineServiceInstance.createLine(req.body as ILineDTO) as Result<ILineDTO>;
            if (lineOrError === null) {
                return res.status(500).json({
                    "errors": {
                        "message": "Invalid attributes"
                    }
                });
            }
            if (lineOrError.isFailure) {
                return res.status(402).send();
            }
            const lineDTO = lineOrError.getValue();
            return res.status(201).json(lineDTO);
        }
        catch (e) {
            return next(e);
        }
    };

    /**
     * Lists all Lines with the received order
     * @param req Request JSON body with the list order
     * @param res Status code and JSON body with the list of all Lines in order
     * @param next 
     * @returns Response
     */
    public async findAll(req: Request, res: Response, next: NextFunction) {
        try {
            const nodeList = await this.lineServiceInstance.findAll(req.query.order.toString()) as Result<ILineDTO[]>;
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
     * Lists Lines with the received name
     * @param req Request JSON body with the name of the Lines
     * @param res Status code and JSON body with the list of resulting Lines
     * @param next 
     * @returns Response
     */
    public async findByName(req: Request, res: Response, next: NextFunction) {
        try {
            const nodeList = await this.lineServiceInstance.findByName(req.query.name.toString(), req.query.order.toString()) as Result<ILineDTO[]>;
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
     * Gets Lines from Id
     * @param req Request JSON body with the id of a Lines
     * @param res Status code and JSON body with the resulting Lines
     * @param next 
     * @returns Response
     */
    public async findById(req: Request, res: Response, next: NextFunction) {
        try {
            const nodeList = await this.lineServiceInstance.findById(req.query.id.toString()) as Result<ILineDTO>;
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
     * Lists Paths from Line with the received name
     * @param req Request JSON body with the name of the Line
     * @param res Status code and JSON body with the list of resulting Paths from the Line
     * @param next 
     * @returns Response
     */
    public async findPathsFromLineName(req: Request, res: Response, next: NextFunction) {
        try {
            const lineList = await this.lineServiceInstance.findPathsByLineId(req.query.id.toString()) as Result<any>;
            if (lineList.isFailure) {
                return res.status(402).send();
            }
            const lineListDTO = lineList.getValue();
            return res.status(201).json(lineListDTO);
        }
        catch (e) {
            return next(e);
        }
    };

    /**
     * Lists coordinates from all Nodes in a Line with the received name
     * @param req Request JSON body with the name of the Line
     * @param res Status code and JSON body with the list of resulting coordinates 
     * from all Nodes in a Line
     * @param next 
     * @returns Response
     */
    public async findCoordinatesByLineId(req: Request, res: Response, next: NextFunction) {
        try {
            const lineList = await this.lineServiceInstance.findCoordinatesByLineId(req.query.id.toString()) as Result<any>;
            if (lineList.isFailure) {
                return res.status(402).send();
            }
            const lineListDTO = lineList.getValue();
            return res.status(201).json(lineListDTO);
        }
        catch (e) {
            return next(e);
        }
    };

    /**
     * Lists Paths from Line with the received name
     * @param req Request JSON body with the name of the Line
     * @param res Status code and JSON body with the list of resulting Paths from the Line,
     * showing names instead of ids
     * @param next 
     * @returns Response
     */
    public async findPathsFromLineWithNames(req: Request, res: Response, next: NextFunction) {
        try {
            const lineList = await this.lineServiceInstance.findPathsFromLineWithName(req.query.name.toString()) as Result<any>;
            if (lineList.isFailure) {
                return res.status(402).send();
            }
            const lineListDTO = lineList.getValue();
            return res.status(201).json(lineListDTO);
        }
        catch (e) {
            return next(e);
        }
    };

    /**
     * Lists all Lines without Ids with the received order
     * @param req Request JSON body with the list order
     * @param res Status code and JSON body with the list of all Lines in order
     * @param next 
     * @returns Response
     */
    public async findAllWithoutId(req: Request, res: Response, next: NextFunction) {
        try {
            const lineList = await this.lineServiceInstance.findAllWithoutId(req.query.order.toString()) as Result<ILineDTO[]>;
            if (lineList.isFailure) {
                return res.status(402).send();
            }
            const lineListDTO = lineList.getValue();
            return res.status(201).json(lineListDTO);
        }
        catch (e) {
            return next(e);
        }
    };

    /**
     * Lists Lines without Ids with the received name
     * @param req Request JSON body with the name of the Lines
     * @param res Status code and JSON body with the list of resulting Lines
     * @param next 
     * @returns Response
     */
    public async findByNameWithoutId(req: Request, res: Response, next: NextFunction) {
        try {
            const nodeList = await this.lineServiceInstance.findByNameWithoutId(req.query.name.toString(), req.query.order.toString()) as Result<ILineDTO[]>;
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
     * Gets Lines from Id
     * @param req Request JSON body with the id of a Lines
     * @param res Status code and JSON body with the resulting Lines
     * @param next 
     * @returns Response
     */
    public async findByIdWithoutId(req: Request, res: Response, next: NextFunction) {
        try {
            const nodeList = await this.lineServiceInstance.findByIdWithoutId(req.query.id.toString()) as Result<ILineDTO>;
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
}