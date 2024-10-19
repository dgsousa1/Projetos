import { NextFunction, Request, Response } from 'express';
import { Inject } from 'typedi';
import config from "../../config";
import IImportController from "./IControllers/IImportController"
import IImportService from '../services/IServices/IImportService';
import INodeService from '../services/IServices/INodeService';
import IDriverTypeService from '../services/IServices/IDriverTypeService';
import IVehicleTypeService from '../services/IServices/IVehicleTypeService';
import IPathService from '../services/IServices/IPathService';
import INodeDTO from '../dto/INodeDTO';
import ILineService from '../services/IServices/ILineService';

export default class ImportController implements IImportController {

    constructor(
        @Inject(config.services.import.name) private importServiceInstance: IImportService,
        @Inject(config.services.node.name) private nodeServiceInstance: INodeService,
        @Inject(config.services.driverType.name) private driverTypeServiceInstance: IDriverTypeService,
        @Inject(config.services.vehicleType.name) private vehiclesTypeServiceInstance: IVehicleTypeService,
        @Inject(config.services.path.name) private pathServiceInstance: IPathService,
        @Inject(config.services.line.name) private lineServiceInstance: ILineService,
    ) { }

    public async importFile(req: Request, res: Response, next: NextFunction) {
        var counter = 0;
        try {
            /* Import Nodes */
            var receivedNodes = await this.importServiceInstance.importNodes();
            for (let index = 0; index < receivedNodes.length; index++) {
                try {
                    const createImportedNodeOrError = await this.nodeServiceInstance.createNode(receivedNodes[index] as INodeDTO);
                } catch (e) {
                    this.importServiceInstance.insertIntoLogger(JSON.stringify(receivedNodes[index]));
                    counter++;
                }
            }
            /* Import Vehicles */
            var receivedVehicles = await this.importServiceInstance.importVehicleTypes();
            for (let index = 0; index < receivedVehicles.length; index++) {
                try {
                    const createImportedNodeOrError = await this.vehiclesTypeServiceInstance.createVehicleType(receivedVehicles[index]);
                } catch (e) {
                    this.importServiceInstance.insertIntoLogger(JSON.stringify(receivedVehicles[index]));
                    counter++;
                }
            }
            /* Import Drivers */
            var receivedDrivers = await this.importServiceInstance.importDriverTypes();
            for (let index = 0; index < receivedDrivers.length; index++) {
                try {
                    const createImportedNodeOrError = await this.driverTypeServiceInstance.createDriverType(receivedDrivers[index]);
                } catch (e) {
                    this.importServiceInstance.insertIntoLogger(JSON.stringify(receivedDrivers[index]));
                    counter++;
                }
            }
            /* Import Paths */
            var receivedPaths = await this.importServiceInstance.importPaths();
            for (let index = 0; index < receivedPaths.length; index++) {
                try {
                    const createImportedNodeOrError = await this.pathServiceInstance.createPath(receivedPaths[index]);
                } catch (e) {
                    this.importServiceInstance.insertIntoLogger(JSON.stringify(receivedPaths[index]));
                    counter++;
                }
            }

            /* Import Lines */
            var receivedLines = await this.importServiceInstance.importLines();
            for (let index = 0; index < receivedLines.length; index++) {
                try {
                    const createImportedNodeOrError = await this.lineServiceInstance.createLine(receivedLines[index]);

                    if (createImportedNodeOrError === null) {
                        this.importServiceInstance.insertIntoLogger(JSON.stringify(receivedLines[index]));
                    }

                } catch (e) {
                    this.importServiceInstance.insertIntoLogger(JSON.stringify(receivedLines[index]));
                    counter++;
                }
            }

            if (counter > 0) {
                res.status(402).send('Errors founded while importing data, check logger file!');
            } else {
                res.status(201).json('Data Successfull imported with no errors!')
            }

        } catch (e) {
            return next(e);
        }
    }
}