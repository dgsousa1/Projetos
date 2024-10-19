import { Request, Response, NextFunction } from 'express';
import { Inject } from 'typedi';
import config from "../../config";
import INodeController from "./IControllers/INodeController";
import { Result } from "../core/logic/Result";
import INodeService from '../services/IServices/INodeService';
import INodeDTO from '../dto/INodeDTO';

export default class NodeController implements INodeController {
    /**
     * Creates NodeController
     * @param nodeServiceInstance Injected NodeService into the NodeController
     */
    constructor(
        @Inject(config.services.node.name) private nodeServiceInstance: INodeService
    ) { }

    /**
     * Creates and adds to the DataBase the received Node
     * @param req Resquest with a JSON body with the Node info
     * @param res Status code and JSON body with the added Node
     * @param next 
     * @returns Response
     */
    public async createNode(req: Request, res: Response, next: NextFunction) {
        try {
            const nodeOrError = await this.nodeServiceInstance.createNode(req.body as INodeDTO) as Result<INodeDTO>;
            if (nodeOrError.isFailure) {
                return res.status(402).send();
            }
            const nodeDTO = nodeOrError.getValue();
            return res.status(201).json(nodeDTO);
        }
        catch (e) {
            return next(e);
        }
    };

    /**
     * Lists all Nodes with the received order
     * @param req Request JSON body with the list order
     * @param res Status code and JSON body with the list of all Nodes in order
     * @param next 
     * @returns Response
     */
    public async findAll(req: Request, res: Response, next: NextFunction) {
        try {
            const nodeList = await this.nodeServiceInstance.findAll(req.query.order.toString()) as Result<INodeDTO[]>;
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
     * Lists nodes with the received name
     * @param req Request JSON body with the name of the Nodes
     * @param res Status code and JSON body with the list of resulting Nodes
     * @param next 
     * @returns Response
     */
    public async findByName(req: Request, res: Response, next: NextFunction) {
        try {
            const nodeList = await this.nodeServiceInstance.findByName(req.query.name.toString(), req.query.order.toString()) as Result<INodeDTO[]>;
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
     * Gets the Node with the received name
     * @param req Request JSON body with the name of a Node
     * @param res Status code and JSON body with the resulting Node
     * @param next 
     * @returns Response
     */
    public async findByExactName(req: Request, res: Response, next: NextFunction) {
        try {
            const nodeList = await this.nodeServiceInstance.findByExactName(req.query.name.toString()) as Result<INodeDTO>;
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
     * Gets Id from the Node with the received name
     * @param req Request JSON body with the name of a Node
     * @param res Status code and JSON body with the resulting NodeId
     * @param next 
     * @returns Response
     */
    public async findIdByName(req: Request, res: Response, next: NextFunction) {
        try {
            const nodeIdOrError = await this.nodeServiceInstance.findIdByName(req.query.name.toString()) as Result<string>;
            if (nodeIdOrError.isFailure) {
                return res.status(402).send();
            }
            const nodeString = nodeIdOrError.getValue();
            return res.status(201).json(nodeString);
        }
        catch (e) {
            return next(e);
        }
    };

    /**
     * Gets Nodes from Id
     * @param req Request JSON body with the id of a Node
     * @param res Status code and JSON body with the resulting Node
     * @param next 
     * @returns Response
     */
    public async findById(req: Request, res: Response, next: NextFunction) {
        try {
            const nodeList = await this.nodeServiceInstance.findById(req.query.id.toString()) as Result<INodeDTO>;
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
     * List all Nodes that are depots
     * @param req Request JSON body with the list order
     * @param res Status code and JSON body with the list of all Nodes that are depots in order
     * @param next 
     * @returns Response
     */
    public async findDepots(req: Request, res: Response, next: NextFunction) {
        try {
            const nodeList = await this.nodeServiceInstance.findDepots(req.query.order.toString()) as Result<INodeDTO[]>;
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
     * List all Nodes that are relief points
     * @param req Request JSON body with the list order
     * @param res Status code and JSON body with the list of all Nodes that are 
     * relief points in order
     * @param next 
     * @returns Response
     */
    public async findReliefPoints(req: Request, res: Response, next: NextFunction) {
        try {
            const nodeList = await this.nodeServiceInstance.findReliefPoints(req.query.order.toString()) as Result<INodeDTO[]>;
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
     * List all Nodes with the received name that are depots/relief points
     * @param req Request JSON body with the name of the node
     * @param res Status code and JSON body with the list of all Nodes with the 
     * received name that are depots/relief points
     * @param next 
     * @returns Response
     */
    public async findByNameAndDepotsOrReliefPoint(req: Request, res: Response, next: NextFunction) {
        try {
            const nodeList = await this.nodeServiceInstance.findByNameAndDepotsOrReliefPoint(req.query.name.toString(), JSON.parse(req.query.isDepot.toString()), JSON.parse(req.query.isReliefPoint.toString()), req.query.order.toString()) as Result<INodeDTO[]>;

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
