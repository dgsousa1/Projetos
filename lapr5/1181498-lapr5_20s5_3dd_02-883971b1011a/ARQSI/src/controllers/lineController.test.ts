import { expect } from 'chai';
import * as sinon from 'sinon';
import { Response, Request, NextFunction } from 'express';
import { Container } from 'typedi';
import config from "../../config";
import ILineService from "../services/IServices/ILineService";
import LineController from "./lineController";
import ILineDTO from "../dto/ILineDTO";
import { Result } from '../core/logic/Result';
import LineService from "../services/lineService";
import ILineController from './IControllers/ILineController';


describe('line controller', function () {
    beforeEach(function () {

    })

    afterEach(function () {

    })

   /* it('should create a line and return a dto', async function () {

        const body = {
            "name": "Line1",
            "color": "RGB(1,1,1)",
            "pathGo": ["71165e62-acf6-4e1e-bb25-b400f929da28"],
            "pathReturn": ["71165e62-acf6-4e1e-bb25-b400f929da28"],
            "vehicleType": "d10c8d6d-9faf-4300-b477-c9c572994289",
            " driverType": "ffdce0fa-16be-40bf-854c-369a99bd47bd"
        };

        let req: Partial<Request> = {};
        req.body = body;

        let res: Partial<Response> = {
            json: sinon.spy(),
            status: function () { this.statusCode = 201; return this; }
        };

        let next: Partial<NextFunction> = () => { };

        let lineServiceClass = require(config.services.line.path).default;
        let lineServiceInstance: ILineService = Container.get(lineServiceClass)

        Container.set(config.services.line.name, lineServiceInstance);
        lineServiceInstance = Container.get(config.services.line.name);



        let teste = Result.ok<any>(body);
        var serv = sinon.stub(lineServiceInstance, 'createLine');
        serv.withArgs(req.body as ILineDTO).returns(teste);


        const ctrl = new LineController(lineServiceInstance as ILineService);


        await ctrl.createLine(<Request>req, <Response>res, <NextFunction>next);

        sinon.assert.calledWith(res.json, sinon.match(body));
        serv.restore();
    })


*/


});