import { Router } from 'express';
import { celebrate, Joi } from 'celebrate';
import { Container } from 'typedi';


import config from "../../../config";
import IMockController from '../../controllers/IControllers/IMockController';

const route = Router();

export default (app: Router) => {
    app.use('/mock', route);

    const ctrl = Container.get(config.controller.mock.name) as IMockController;

    route.get('/all',
        (req, res, next) => ctrl.getTrip(req, res, next));


}