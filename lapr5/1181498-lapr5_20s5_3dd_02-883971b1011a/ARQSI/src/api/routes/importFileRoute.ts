import { Router } from 'express';
import { Container } from 'typedi';
import config from "../../../config";
import ImportFileController from "../../controllers/importController";
const route = Router();

export default (app: Router) => {    
    
    app.use('/import',route);

    const ctrl = Container.get(config.controller.import.name) as ImportFileController;

    route.post('',(req, res, next) => 
        ctrl.importFile(req, res, next));
}