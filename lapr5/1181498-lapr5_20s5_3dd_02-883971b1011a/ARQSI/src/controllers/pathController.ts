import { Request, Response, NextFunction } from 'express';
import { Inject } from 'typedi';
import config from "../../config";
import IPathController from "./IControllers/IPathController";
import { Result } from "../core/logic/Result";
import IPathService from '../services/IServices/IPathService';
import IPathDTO from '../dto/IPathDTO';

export default class PathController implements IPathController {
    /**
     * Creates PathController
     * @param pathServiceInstance Injected PathService into the PathController
     */
    constructor(
        @Inject(config.services.path.name) private pathServiceInstance: IPathService
    ) { }

    /**
     * Creates and adds a Path to the database
     * @param req Request with the Path info in a JSON body
     * @param res Status code and JSON body with the added Path
     * @returns Response
     */
    public async createPath(req: Request, res: Response, next: NextFunction) {
        try {
            const pathOrError = await this.pathServiceInstance.createPath(req.body as IPathDTO) as Result<IPathDTO>;
            if(pathOrError === null){
                return res.status(500).json({
                    "errors": {
                        "message": "Invalid attributes"
                    }
                });
            }
            if (pathOrError.isFailure) {
                return res.status(402).send();
            }
            const pathDTO = pathOrError.getValue();
            return res.status(201).json(pathDTO);
        }
        catch (e) {
            return next(e);
        }
    };

    /**
     * Lists all Paths in the database
     * @param res Status code and JSON body with all the Paths
     */
    public async findAll(req: Request, res: Response, next: NextFunction) {
        try {
            const pathList = await this.pathServiceInstance.findAll() as Result<IPathDTO[]>;
            if (pathList.isFailure) {
                return res.status(402).send();
            }
            const pathListDTO = pathList.getValue();
            return res.status(201).json(pathListDTO);
        }
        catch (e) {
            return next(e);
        }
    };

    /**
     *  Gets the Path with the received key
     * @param req Request with query containing a key
     * @param res Status code and a JSON object with the Path
     */
    public async findByKey(req: Request, res: Response, next: NextFunction) {
        try {
            const pathList = await this.pathServiceInstance.findByKey(req.query.key.toString()) as Result<IPathDTO>;
            if (pathList.isFailure) {
                return res.status(402).send();
            }
            const pathListDTO = pathList.getValue();
            return res.status(201).json(pathListDTO);
        }
        catch (e) {
            return next(e);
        }
    };

    /**
     *  Gets the Path with the received key
     * @param req Request with query containing a key
     * @param res Status code and a JSON object with the Path and named nodes
     */
    public async findByKeyWithNodeNames(req: Request, res: Response, next: NextFunction) {
        try {
            const pathList = await this.pathServiceInstance.findByKeyWithNodeNames(req.query.key.toString()) as Result<any>;
            if (pathList.isFailure) {
                return res.status(402).send();
            }
            const pathListDTO = pathList.getValue();
            return res.status(201).json(pathListDTO);
        }
        catch (e) {
            return next(e);
        }
    };

    /**
     * Lists all Paths in the database
     * @param res Status code and JSON body with all the Paths
     */
    public async findAllWithNodeNames(req: Request, res: Response, next: NextFunction) {
        try {
            const pathList = await this.pathServiceInstance.findAllWithNodeNames() as Result<any[]>;
            if (pathList.isFailure) {
                return res.status(402).send();
            }
            const pathListDTO = pathList.getValue();
            return res.status(201).json(pathListDTO);
        }
        catch (e) {
            return next(e);
        }
    };
}