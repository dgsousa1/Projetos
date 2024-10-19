import { Router } from 'express';
import { celebrate, Joi } from 'celebrate';
import { Container } from 'typedi';

import config from "../../../config";
import IVehicleTypeController from '../../controllers/IControllers/IVehicleTypeController';

const route = Router();


export default (app: Router) => {
    app.use('/vehicleTypes', route);

    const ctrl = Container.get(config.controller.vehicleType.name) as IVehicleTypeController;


    /**
     *  Post route to create a vehicle type
     */
    route.post('',
        celebrate({
            body: Joi.object({
                name: Joi.string().required(),
                fuelType: Joi.string().required(),
                autonomy: Joi.number().required(),
                costPerKilometer: Joi.number().required(),
                consumption: Joi.number().required(),
                averageSpeed: Joi.number().required(),
                description: Joi.string().required(),
                emission: Joi.number().required(),
            })
        }),
        (req, res, next) => ctrl.createVehicleType(req, res, next));

    /**
     * Get route to get all vehicle types 
     */
    route.get('/all',
        (req, res, next) => ctrl.findAll(req, res, next));

    /**
     * Get route to get a vehicle type by its exact name
    */
    route.get('/exactname',
        (req, res, next) => ctrl.findByExactName(req, res, next));


    
    /**
     * Get route to get a vehicle type by its id
     */    
    route.get('/byid',
        /*celebrate({
            body: Joi.object({
                id: Joi.string().required(),
            })
        }),*/
        (req, res, next) => ctrl.findById(req, res, next));

}
