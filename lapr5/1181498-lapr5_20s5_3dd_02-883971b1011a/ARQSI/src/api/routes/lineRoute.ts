import { Router } from 'express';
import { celebrate, Joi } from 'celebrate';
import { Container } from 'typedi';

import config from "../../../config";
import ILineController from '../../controllers/IControllers/ILineController';

const route = Router();

export default (app: Router) => {
    app.use('/lines', route);

    const ctrl = Container.get(config.controller.line.name) as ILineController;

    /**
     * Create Line
     */
    route.post('',
        celebrate({
            body: Joi.object({
                name: Joi.string().required(),
                color: Joi.string().required(),
                pathGo: Joi.array().required(),
                pathReturn: Joi.array().required(),
                vehicleType: Joi.string().optional().allow('').default(''),
                driverType: Joi.string().optional().allow('').default('')
            })
        }),
        (req, res, next) => ctrl.createLine(req, res, next));

    /**
     * List all Lines
     */
    route.get('/all', (req, res, next) => ctrl.findAll(req, res, next));

    /**
     * List Lines from name
     */
    route.get('/byname', (req, res, next) => ctrl.findByName(req, res, next));

    /**
     * Get Line from id
     */
    route.get('/byid', (req, res, next) => ctrl.findById(req, res, next));

    /**
     * Get Paths from Line
     */
    route.get('/pathfromline', (req, res, next) => ctrl.findPathsFromLineName(req, res, next));

    /**
     * Get coordinates from all Nodes in a Line 
     */
    route.get('/coordinates', (req, res, next) => ctrl.findCoordinatesByLineId(req, res, next));

    /**
     * Get Paths from Line (with names)
     */
    route.get('/pfromlinename', (req, res, next) => ctrl.findPathsFromLineWithNames(req, res, next));

    /** 
     * List all Lines without Ids
     */
    route.get('/allwithoutids', (req, res, next) => ctrl.findAllWithoutId(req, res, next));

    /**
     * List Lines from name
     */
    route.get('/bynamewithoutids', (req, res, next) => ctrl.findByNameWithoutId(req, res, next));

    /**
     * Get Line from id
     */
    route.get('/byidwithoutids', (req, res, next) => ctrl.findByIdWithoutId(req, res, next));
}