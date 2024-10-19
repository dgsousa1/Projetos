import { Router } from 'express';
import { celebrate, Joi } from 'celebrate';
import { Container } from 'typedi';

import config from "../../../config";
import IDriverTypeController from '../../controllers/IControllers/IDriverTypeController';

const route = Router();

export default (app: Router) => {
    app.use('/driverTypes', route);

    const ctrl = Container.get(config.controller.driverType.name) as IDriverTypeController;

    /**
     *  Post route to create a driver type
     */
    route.post('',
        celebrate({
            body: Joi.object({
                name: Joi.string().required(),
                description: Joi.string().required()
            })
        }),
        (req, res, next) => ctrl.createDriverType(req, res, next));

    /**
     * Get route to get all driver types
     */
    route.get('/all',
        (req, res, next) => ctrl.findAll(req, res, next));

    /**
     *  Get route to get a driver type by its exact name
     */
    route.get('/exactname',
        (req, res, next) => ctrl.findByExactName(req, res, next));

    /**
     * Get route to get a driver type by its id
     */
    route.get('/byid',
        /*celebrate({
            body: Joi.object({
                id: Joi.string().required(),
            })
        }),*/
        (req, res, next) => ctrl.findById(req, res, next));

}