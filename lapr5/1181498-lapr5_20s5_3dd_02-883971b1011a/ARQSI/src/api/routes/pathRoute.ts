import { Router } from 'express';
import { celebrate, Joi } from 'celebrate';
import { Container } from 'typedi';

import config from "../../../config";
import IPathController from '../../controllers/IControllers/IPathController';

const route = Router();

export default (app: Router) => {
    app.use('/paths', route);

    const ctrl = Container.get(config.controller.path.name) as IPathController;

    /**
     * Create path
     */
    route.post('',
        celebrate({
            body: Joi.object({
                key: Joi.number().required(),
                pathNodes: Joi.array().items({
                    duracao: Joi.number().required(),
                    distancia: Joi.number().required(),
                    inicialNode: Joi.string().required(),
                    finalNode: Joi.string().required()
                })
            })
        }),
        (req, res, next) => ctrl.createPath(req, res, next));

    /**
     * List all paths
    */    
    route.get('/all',
        (req, res, next) => ctrl.findAll(req, res, next));

    /**
     * Find a path by its key
    */    
    route.get('/bykey',
        (req, res, next) => ctrl.findByKey(req, res, next));

    /**
     * Find a path by key, returns with node names
    */    
    route.get('/bykeywithnodenames',
        (req, res, next) => ctrl.findByKeyWithNodeNames(req, res, next));

    /**
     * List all paths, return with node names
    */  
    route.get('/allwithnodenames',
        (req, res, next) => ctrl.findAllWithNodeNames(req, res, next));

}