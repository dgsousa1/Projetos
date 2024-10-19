import { expect } from 'chai';
import * as sinon from 'sinon';
import { Response, Request, NextFunction } from 'express';
import { Container } from 'typedi';
import config from "../../config";
import IVehicleTypeService from "../services/IServices/IVehicleTypeService";
import VehicleTypeController from "./vehicleTypeController";
import IVehicleTypeDTO from "../dto/IVehicleTypeDTO";
import { Result } from '../core/logic/Result';
import VehicleTypeService from "../services/vehicleTypeService";
import IVehicleTypeController from './IControllers/IVehicleTypeController';

describe('vehicle type controller', function () {
    beforeEach(function () {

    })

    afterEach(function () {

    })

    it('should create a vehicle  type and return a dto', async function () {

        const body = {
            "name": "VT001",
            "fuelType": "Diesel",
            "autonomy": 200,
            "costPerKilometer": 30,
            "consumption": 2,
            "averageSpeed": 5,
            "description": "Veiculo a Diesel",
            "emission": 3
        };

        let req: Partial<Request> = {};
        req.body = body;

        let res: Partial<Response> = {
            json: sinon.spy(),
            status: function () { this.statusCode = 201; return this; }
        };

        let next: Partial<NextFunction> = () => { };

        let vehicleTypeServiceClass = require(config.services.vehicleType.path).default;
        let vehicleTypeServiceInstance: IVehicleTypeService = Container.get(vehicleTypeServiceClass)

        Container.set(config.services.vehicleType.name, vehicleTypeServiceInstance);
        vehicleTypeServiceInstance = Container.get(config.services.vehicleType.name);



        let teste = Result.ok<IVehicleTypeDTO>(body as IVehicleTypeDTO);
        var serv = sinon.stub(vehicleTypeServiceInstance, 'createVehicleType');
        serv.withArgs(req.body as IVehicleTypeDTO).returns(teste);


        const ctrl = new VehicleTypeController(vehicleTypeServiceInstance as IVehicleTypeService);
        //const ctrl = Container.get(NodeController);


        await ctrl.createVehicleType(<Request>req, <Response>res, <NextFunction>next);

        //sinon.assert.calledOnce(res.json);
        sinon.assert.calledWith(res.json, sinon.match(body));
        serv.restore();
    })

    it('should attempt to create a vehicle type and fail', async function () {

        const body = {
            "name": "VT001",
            "fuelType": "Diesel",
            "autonomy": 200,
            "costPerKilometer": 30,
            "consumption": 2
        };

        let req: Partial<Request> = {};
        req.body = body;

        let res: Partial<Response> = {
            json: sinon.spy(),
            status: function () { this.statusCode = 402; return this; }
        };

        let next: Partial<NextFunction> = () => { };

        let vehicleTypeServiceClass = require(config.services.vehicleType.path).default;
        let vehicleTypeServiceInstance: IVehicleTypeService = Container.get(vehicleTypeServiceClass)

        Container.set(config.services.vehicleType.name, vehicleTypeServiceInstance);
        vehicleTypeServiceInstance = Container.get(config.services.vehicleType.name);


        let teste = Result.ok<IVehicleTypeDTO>(body as IVehicleTypeDTO);
        var serv = sinon.stub(vehicleTypeServiceInstance, 'createVehicleType');
        serv.withArgs(req.body as IVehicleTypeDTO).returns(teste);


        const ctrl = new VehicleTypeController(vehicleTypeServiceInstance as IVehicleTypeService);
        //const ctrl = Container.get(NodeController);


        await ctrl.createVehicleType(<Request>req, <Response>res, <NextFunction>next);

        //sinon.assert.calledOnce(res.json);
        sinon.assert.calledWith(res.json, sinon.match(body));
        serv.restore();
    })

    it('should try to get all driver types and return a dto', async function () {

        const result = [{
            "name": "VT001",
            "fuelType": "Diesel",
            "autonomy": 200,
            "costPerKilometer": 30,
            "consumption": 2,
            "averageSpeed": 5,
            "description": "Veiculo a Diesel",
            "emission": 3
        }, {
            "name": "VT002",
            "fuelType": "Elétrico",
            "autonomy": 200,
            "costPerKilometer": 30,
            "consumption": 2,
            "averageSpeed": 5,
            "description": "Veiculo Eletrico",
            "emission": 3
        }]

        let req: Partial<Request> = {};

        let res: Partial<Response> = {
            json: sinon.spy(),
            status: function () { this.statusCode = 201; return this; }
        };

        let next: Partial<NextFunction> = () => { };

        let vehicleTypeServiceClass = require(config.services.vehicleType.path).default;
        let vehicleTypeServiceInstance: IVehicleTypeService = Container.get(vehicleTypeServiceClass)

        Container.set(config.services.vehicleType.name, vehicleTypeServiceInstance);
        vehicleTypeServiceInstance = Container.get(config.services.vehicleType.name);


        let teste = Result.ok<IVehicleTypeDTO[]>(result);
        var serv = sinon.stub(vehicleTypeServiceInstance, 'findAll').returns(teste);


        const ctrl = new VehicleTypeController(vehicleTypeServiceInstance as IVehicleTypeService);
        //const ctrl = Container.get(NodeController);


        await ctrl.findAll(<Request>req, <Response>res, <NextFunction>next);

        sinon.assert.calledWith(res.json, sinon.match(result));
        serv.restore();
    })

    it('should find a driver type by id and return it in dto form', async function () {

        const result = {
            "name": "VT001",
            "fuelType": "Diesel",
            "autonomy": 200,
            "costPerKilometer": 30,
            "consumption": 2,
            "averageSpeed": 5,
            "description": "Veiculo a Diesel",
            "emission": 3
        };

        let req: Partial<Request> = {};
        req.query = { id: "c8130735-2e0a-4d39-8e99-0bcee1095a14" };


        let res: Partial<Response> = {
            json: sinon.spy(),
            status: function () { this.statusCode = 201; return this; }
        };

        let next: Partial<NextFunction> = () => { };

        let vehicleTypeServiceClass = require(config.services.vehicleType.path).default;
        let vehicleTypeServiceInstance: IVehicleTypeService = Container.get(vehicleTypeServiceClass)

        Container.set(config.services.vehicleType.name, vehicleTypeServiceInstance);
        vehicleTypeServiceInstance = Container.get(config.services.vehicleType.name);


        let teste = Result.ok<IVehicleTypeDTO>(result);
        var serv = sinon.stub(vehicleTypeServiceInstance, 'findById');
        serv.withArgs(sinon.match.string).returns(teste);


        const ctrl = new VehicleTypeController(vehicleTypeServiceInstance as IVehicleTypeService);
        //const ctrl = Container.get(NodeController);


        await ctrl.findById(<Request>req, <Response>res, <NextFunction>next);

        //sinon.assert.calledOnce(res.json);
        sinon.assert.calledWith(res.json, sinon.match(result));
        serv.restore();
    })

    it('should find a driver type by exact name and return it in dto form', async function () {

        const result = {
            "name": "VT001",
            "fuelType": "Diesel",
            "autonomy": 200,
            "costPerKilometer": 30,
            "consumption": 2,
            "averageSpeed": 5,
            "description": "Veiculo a Diesel",
            "emission": 3
        };

        let req: Partial<Request> = {};
        req.query = { name: "VT001" };


        let res: Partial<Response> = {
            json: sinon.spy(),
            status: function () { this.statusCode = 201; return this; }
        };

        let next: Partial<NextFunction> = () => { };

        let vehicleTypeServiceClass = require(config.services.vehicleType.path).default;
        let vehicleTypeServiceInstance: IVehicleTypeService = Container.get(vehicleTypeServiceClass)

        Container.set(config.services.vehicleType.name, vehicleTypeServiceInstance);
        vehicleTypeServiceInstance = Container.get(config.services.vehicleType.name);


        let teste = Result.ok<IVehicleTypeDTO>(result);
        var serv = sinon.stub(vehicleTypeServiceInstance, 'findByExactName');
        serv.withArgs(sinon.match.string).returns(teste);


        const ctrl = new VehicleTypeController(vehicleTypeServiceInstance as IVehicleTypeService);
        //const ctrl = Container.get(NodeController);


        await ctrl.findByExactName(<Request>req, <Response>res, <NextFunction>next);

        //sinon.assert.calledOnce(res.json);
        sinon.assert.calledWith(res.json, sinon.match(result));
        serv.restore();
    })


});




























