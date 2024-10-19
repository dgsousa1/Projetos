import { expect } from 'chai';
import * as sinon from 'sinon';
import { Response, Request, NextFunction } from 'express';
import { Container } from 'typedi';
import config from "../../config";
import IDriverTypeService from "../services/IServices/IDriverTypeService";
import DriverTypeController from "./driverTypeController";
import IDriverTypeDTO from "../dto/IDriverTypeDTO";
import { Result } from '../core/logic/Result';
import DriverTypeService from "../services/driverTypeService";
import IDriverTypeController from './IControllers/IDriverTypeController';

describe('driver type controller', function () {
    beforeEach(function () {

    })

    afterEach(function () {

    })

    it('should create a driver type and return a dto', async function () {

        const body = {
            "name": 'Condutor de metro',
            "description": 'condutor habilitado a conduzir metros'
        };

        let req: Partial<Request> = {};
        req.body = body;

        let res: Partial<Response> = {
            json: sinon.spy(),
            status: function () { this.statusCode = 201; return this; }
        };

        let next: Partial<NextFunction> = () => { };

        let driverTypeServiceClass = require(config.services.driverType.path).default;
        let driverTypeServiceInstance: IDriverTypeService = Container.get(driverTypeServiceClass)

        Container.set(config.services.driverType.name, driverTypeServiceInstance);
        driverTypeServiceInstance = Container.get(config.services.driverType.name);



        let teste = Result.ok<IDriverTypeDTO>(body as IDriverTypeDTO);
        var serv = sinon.stub(driverTypeServiceInstance, 'createDriverType');
        serv.withArgs(req.body as IDriverTypeDTO).returns(teste);


        const ctrl = new DriverTypeController(driverTypeServiceInstance as IDriverTypeService);
        //const ctrl = Container.get(NodeController);


        await ctrl.createDriverType(<Request>req, <Response>res, <NextFunction>next);

        //sinon.assert.calledOnce(res.json);
        sinon.assert.calledWith(res.json, sinon.match(body));
        serv.restore();
    })

    it('should attempt to create a driver type and fail', async function () {

        const body = {
            "description": 'condutor habilitado a conduzir metros'
        };

        let req: Partial<Request> = {};
        req.body = body;

        let res: Partial<Response> = {
            json: sinon.spy(),
            status: function () { this.statusCode = 402; return this; }
        };

        let next: Partial<NextFunction> = () => { };

        let driverTypeServiceClass = require(config.services.driverType.path).default;
        let driverTypeServiceInstance: IDriverTypeService = Container.get(driverTypeServiceClass)

        Container.set(config.services.driverType.name, driverTypeServiceInstance);
        driverTypeServiceInstance = Container.get(config.services.driverType.name);


        let teste = Result.ok<IDriverTypeDTO>(body as IDriverTypeDTO);
        var serv = sinon.stub(driverTypeServiceInstance, 'createDriverType');
        serv.withArgs(req.body as IDriverTypeDTO).returns(teste);


        const ctrl = new DriverTypeController(driverTypeServiceInstance as IDriverTypeService);
        //const ctrl = Container.get(NodeController);


        await ctrl.createDriverType(<Request>req, <Response>res, <NextFunction>next);

        //sinon.assert.calledOnce(res.json);
        sinon.assert.calledWith(res.json, sinon.match(body));
        serv.restore();
    })

    it('should try to get all driver types and return a dto', async function () {

        const result = [{
            "name": 'Condutor de metro',
            "description": 'condutor habilitado a conduzir metros'
        },
        {
            "name": 'Condutor de autocarro',
            "description": 'condutor habilitado a conduzir autocarro'
        }]


        let req: Partial<Request> = {};

        let res: Partial<Response> = {
            json: sinon.spy(),
            status: function () { this.statusCode = 201; return this; }
        };

        let next: Partial<NextFunction> = () => { };

        let driverTypeServiceClass = require(config.services.driverType.path).default;
        let driverTypeServiceInstance: IDriverTypeService = Container.get(driverTypeServiceClass)

        Container.set(config.services.driverType.name, driverTypeServiceInstance);
        driverTypeServiceInstance = Container.get(config.services.driverType.name);


        let teste = Result.ok<IDriverTypeDTO[]>(result);
        var serv = sinon.stub(driverTypeServiceInstance, 'findAll').returns(teste);


        const ctrl = new DriverTypeController(driverTypeServiceInstance as IDriverTypeService);
        //const ctrl = Container.get(NodeController);


        await ctrl.findAll(<Request>req, <Response>res, <NextFunction>next);

        sinon.assert.calledWith(res.json, sinon.match(result));
        serv.restore();
    })

    it('should find a driver type by id and return it in dto form', async function () {

        const result = {
            "driverTypeId": "c8130735-2e0a-4d39-8e99-0bcee1095a14",
            "name": "autocarroDriver",
            "description": "condutor de autocarro"
        }

        let req: Partial<Request> = {};
        req.query = { id: "c8130735-2e0a-4d39-8e99-0bcee1095a14" };


        let res: Partial<Response> = {
            json: sinon.spy(),
            status: function () { this.statusCode = 201; return this; }
        };

        let next: Partial<NextFunction> = () => { };

        let driverTypeServiceClass = require(config.services.driverType.path).default;
        let driverTypeServiceInstance: IDriverTypeService = Container.get(driverTypeServiceClass)

        Container.set(config.services.driverType.name, driverTypeServiceInstance);
        driverTypeServiceInstance = Container.get(config.services.driverType.name);


        let teste = Result.ok<IDriverTypeDTO>(result);
        var serv = sinon.stub(driverTypeServiceInstance, 'findById');
        serv.withArgs(sinon.match.string).returns(teste);


        const ctrl = new DriverTypeController(driverTypeServiceInstance as IDriverTypeService);
        //const ctrl = Container.get(NodeController);


        await ctrl.findById(<Request>req, <Response>res, <NextFunction>next);

        //sinon.assert.calledOnce(res.json);
        sinon.assert.calledWith(res.json, sinon.match(result));
        serv.restore();
    })

    it('should find a driver type by exact name and return it in dto form', async function () {

        const result = {
            "name": "autocarroDriver",
            "description": "condutor de autocarro"
        }

        let req: Partial<Request> = {};
        req.query = { name: "autocarroDriver" };


        let res: Partial<Response> = {
            json: sinon.spy(),
            status: function () { this.statusCode = 201; return this; }
        };

        let next: Partial<NextFunction> = () => { };

        let driverTypeServiceClass = require(config.services.driverType.path).default;
        let driverTypeServiceInstance: IDriverTypeService = Container.get(driverTypeServiceClass)

        Container.set(config.services.driverType.name, driverTypeServiceInstance);
        driverTypeServiceInstance = Container.get(config.services.driverType.name);


        let teste = Result.ok<IDriverTypeDTO>(result);
        var serv = sinon.stub(driverTypeServiceInstance, 'findByExactName');
        serv.withArgs(sinon.match.string).returns(teste);


        const ctrl = new DriverTypeController(driverTypeServiceInstance as IDriverTypeService);
        //const ctrl = Container.get(NodeController);


        await ctrl.findByExactName(<Request>req, <Response>res, <NextFunction>next);

        //sinon.assert.calledOnce(res.json);
        sinon.assert.calledWith(res.json, sinon.match(result));
        serv.restore();
    })


});




























