import { Router } from 'express';
import { celebrate, Joi } from 'celebrate';
import { Container } from 'typedi';
import config from "../../../config";
import INodeController from '../../controllers/IControllers/INodeController';

const route = Router();

export default (app: Router) => {
    app.use('/nodes', route);

    const ctrl = Container.get(config.controller.node.name) as INodeController;

    /**
     * Create node
     */
    route.post('',
        celebrate({
            body: Joi.object({
                name: Joi.string().required(),
                shortName: Joi.string().required(),
                latitude: Joi.number().required(),
                longitude: Joi.number().required(),
                isDepot: Joi.boolean().required(),
                isReliefPoint: Joi.boolean().required()
            })
        }),
        (req, res, next) => ctrl.createNode(req, res, next));
        
    /**
    * List all nodes with order
    */
    route.get('/all', (req, res, next) => ctrl.findAll(req, res, next));

    /**
     * List nodes from name
     */
    route.get('/byname', (req, res, next) => ctrl.findByName(req, res, next));

    /**
     * Get node with exact name
     */
    route.get('/exactname', (req, res, next) => ctrl.findByExactName(req, res, next));

    /**
     * Get node id from name
     */
    route.get('/nameforid', (req, res, next) => ctrl.findIdByName(req, res, next));

    /**
     * Get node from id
     */
    route.get('/byid', (req, res, next) => ctrl.findById(req, res, next));

    /**
     * List all depots
     */
    route.get('/depots', (req, res, next) => ctrl.findDepots(req, res, next));

    /**
     * List all relief points
     */
    route.get('/reliefPoints', (req, res, next) => ctrl.findReliefPoints(req, res, next));

    /**
     * List nodes from name that are depots/relief points
     */
    route.get('/multipleFilters', (req, res, next) => ctrl.findByNameAndDepotsOrReliefPoint(req, res, next));
}