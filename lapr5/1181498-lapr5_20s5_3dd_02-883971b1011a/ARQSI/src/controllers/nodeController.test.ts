import { expect } from 'chai';
import * as sinon from 'sinon';
import { Response, Request, NextFunction } from 'express';
import { Container } from 'typedi';
import config from "../../config";
import INodeService from "../services/IServices/INodeService";
import NodeController from "./nodeController";
import INodeDTO from "../dto/INodeDTO";
import { Result } from '../core/logic/Result';
import NodeService from "../services/nodeService";
import INodeController from './IControllers/INodeController';

describe('node controller', function () {
    beforeEach(function () {

    })

    afterEach(function () {

    })

    it('should create a node and return a dto', async function () {

        const body = {
            "name": 'node1',
            "shortName": 'node1',
            "latitude": 2,
            "longitude": 3,
            "isDepot": true,
            "isReliefPoint": true
        };

        let req: Partial<Request> = {};
        req.body = body;

        let res: Partial<Response> = {
            json: sinon.spy(),
            status: function () { this.statusCode = 201; return this; }
        };

        let next: Partial<NextFunction> = () => { };

        let nodeServiceClass = require(config.services.node.path).default;
        let nodeServiceInstance: INodeService = Container.get(nodeServiceClass)

        Container.set(config.services.node.name, nodeServiceInstance);
        nodeServiceInstance = Container.get(config.services.node.name);



        let teste = Result.ok<INodeDTO>(body as INodeDTO);
        var serv = sinon.stub(nodeServiceInstance, 'createNode');
        serv.withArgs(req.body as INodeDTO).returns(teste);


        const ctrl = new NodeController(nodeServiceInstance as INodeService);
        //const ctrl = Container.get(NodeController);


        await ctrl.createNode(<Request>req, <Response>res, <NextFunction>next);

        //sinon.assert.calledOnce(res.json);
        sinon.assert.calledWith(res.json, sinon.match(body));
        serv.restore();
    })

    it('should attempt to create a node and fail', async function () {

        const body = {
            "name": 'node1',
            "shortName": 'node1',
            "latitude": 2,
            "isDepot": true,
            "isReliefPoint": true
        };

        let req: Partial<Request> = {};
        req.body = body;

        let res: Partial<Response> = {
            json: sinon.spy(),
            status: function () { this.statusCode = 402; return this; }
        };

        let next: Partial<NextFunction> = () => { };

        let nodeServiceClass = require(config.services.node.path).default;
        let nodeServiceInstance: INodeService = Container.get(nodeServiceClass)

        Container.set(config.services.node.name, nodeServiceInstance);
        nodeServiceInstance = Container.get(config.services.node.name);

        const ctrl = new NodeController(nodeServiceInstance as INodeService);


        await ctrl.createNode(<Request>req, <Response>res, <NextFunction>next);

        sinon.assert.failException;
    })

    it('should try to get all nodes and return a dto', async function () {

        const result = [{
            "name": 'node1',
            "shortName": 'node1',
            "latitude": 2,
            "longitude": 3,
            "isDepot": true,
            "isReliefPoint": true
        },
        {
            "name": 'node2',
            "shortName": 'node1',
            "latitude": 2,
            "longitude": 3,
            "isDepot": true,
            "isReliefPoint": true
        }]



        let req: Partial<Request> = {};
        req.query = { order: 'name' };

        let res: Partial<Response> = {
            json: sinon.spy(),
            status: function () { this.statusCode = 201; return this; }
        };


        let next: Partial<NextFunction> = () => { };

        let nodeServiceClass = require(config.services.node.path).default;
        let nodeServiceInstance: INodeService = Container.get(nodeServiceClass)

        Container.set(config.services.node.name, nodeServiceInstance);
        nodeServiceInstance = Container.get(config.services.node.name);



        let teste = Result.ok<INodeDTO[]>(result)
        var serv = sinon.stub(nodeServiceInstance, 'findAll');
        serv.withArgs(sinon.match.string).returns(teste);

        const ctrl = new NodeController(nodeServiceInstance as INodeService);
        //const ctrl = Container.get(NodeController);


        await ctrl.findAll(<Request>req, <Response>res, <NextFunction>next);

        //sinon.assert.calledOnce(res.json);
        sinon.assert.calledWith(res.json, sinon.match(result));
        //sinon.assert.match(res,result)
        serv.restore();
    })


    it('should find a nome by name and return it in dto form', async function () {

        const result = {
            "name": 'node1',
            "shortName": 'node1',
            "latitude": 2,
            "longitude": 3,
            "isDepot": true,
            "isReliefPoint": true
        }

        let req: Partial<Request> = {};
        req.query = {
            name: 'name',
            order: 'name'
        };

        let res: Partial<Response> = {
            json: sinon.spy(),
            status: function () { this.statusCode = 201; return this; }
        };

        let next: Partial<NextFunction> = () => { };

        let nodeServiceClass = require(config.services.node.path).default;
        let nodeServiceInstance: INodeService = Container.get(nodeServiceClass)

        Container.set(config.services.node.name, nodeServiceInstance);
        nodeServiceInstance = Container.get(config.services.node.name);


        let teste = Result.ok<INodeDTO>(result);
        var serv = sinon.stub(nodeServiceInstance, 'findByName');
        serv.withArgs(sinon.match.string, sinon.match.string).returns(teste);


        const ctrl = new NodeController(nodeServiceInstance as INodeService);
        //const ctrl = Container.get(NodeController);


        await ctrl.findByName(<Request>req, <Response>res, <NextFunction>next);

        //sinon.assert.calledOnce(res.json);
        sinon.assert.calledWith(res.json, sinon.match(result));
        serv.restore();
    })


    it('should find a nome by id and return it in dto form', async function () {

        const result = {
            "nodeId": "fccf38f9-dc67-46e5-9c50-5a6d1e1e5268",
            "name": 'node1',
            "shortName": 'node1',
            "latitude": 2,
            "longitude": 3,
            "isDepot": true,
            "isReliefPoint": true
        }

        let req: Partial<Request> = {};
        req.query = { id: 'fccf38f9-dc67-46e5-9c50-5a6d1e1e5268' };

        let res: Partial<Response> = {
            json: sinon.spy(),
            status: function () { this.statusCode = 201; return this; }
        };

        let next: Partial<NextFunction> = () => { };

        let nodeServiceClass = require(config.services.node.path).default;
        let nodeServiceInstance: INodeService = Container.get(nodeServiceClass)

        Container.set(config.services.node.name, nodeServiceInstance);
        nodeServiceInstance = Container.get(config.services.node.name);


        let teste = Result.ok<INodeDTO>(result);
        var serv = sinon.stub(nodeServiceInstance, 'findById');
        serv.withArgs(sinon.match.string).returns(teste);


        const ctrl = new NodeController(nodeServiceInstance as INodeService);
        //const ctrl = Container.get(NodeController);


        await ctrl.findById(<Request>req, <Response>res, <NextFunction>next);

        //sinon.assert.calledOnce(res.json);
        sinon.assert.calledWith(res.json, sinon.match(result));
        serv.restore();
    })


    it('should find the depots and return them in dto form', async function () {

        const result = [{
            "name": 'node1',
            "shortName": 'node1',
            "latitude": 2,
            "longitude": 3,
            "isDepot": true,
            "isReliefPoint": true
        }, {
            "name": 'node2',
            "shortName": 'node1',
            "latitude": 2,
            "longitude": 3,
            "isDepot": true,
            "isReliefPoint": true
        },
        {
            "name": 'node3',
            "shortName": 'node1',
            "latitude": 2,
            "longitude": 3,
            "isDepot": true,
            "isReliefPoint": true
        }]

        let req: Partial<Request> = {};
        req.query = { order: 'name' };

        let res: Partial<Response> = {
            json: sinon.spy(),
            status: function () { this.statusCode = 201; return this; }
        };

        let next: Partial<NextFunction> = () => { };

        let nodeServiceClass = require(config.services.node.path).default;
        let nodeServiceInstance: INodeService = Container.get(nodeServiceClass)

        Container.set(config.services.node.name, nodeServiceInstance);
        nodeServiceInstance = Container.get(config.services.node.name);


        let teste = Result.ok<INodeDTO[]>(result);
        var serv = sinon.stub(nodeServiceInstance, 'findDepots');
        serv.withArgs(sinon.match.string).returns(teste);


        const ctrl = new NodeController(nodeServiceInstance as INodeService);
        //const ctrl = Container.get(NodeController);


        await ctrl.findDepots(<Request>req, <Response>res, <NextFunction>next);

        //sinon.assert.calledOnce(res.json);
        sinon.assert.calledWith(res.json, sinon.match(result));
        serv.restore();
    })

    it('should find the relief points and return them in dto form', async function () {

        const result = [{
            "name": 'node1',
            "shortName": 'node1',
            "latitude": 2,
            "longitude": 3,
            "isDepot": false,
            "isReliefPoint": true
        }, {
            "name": 'node2',
            "shortName": 'node1',
            "latitude": 2,
            "longitude": 3,
            "isDepot": false,
            "isReliefPoint": true
        },
        {
            "name": 'node3',
            "shortName": 'node1',
            "latitude": 2,
            "longitude": 3,
            "isDepot": false,
            "isReliefPoint": true
        }]

        let req: Partial<Request> = {};
        req.query = { order: 'name' };

        let res: Partial<Response> = {
            json: sinon.spy(),
            status: function () { this.statusCode = 201; return this; }
        };

        let next: Partial<NextFunction> = () => { };

        let nodeServiceClass = require(config.services.node.path).default;
        let nodeServiceInstance: INodeService = Container.get(nodeServiceClass)

        Container.set(config.services.node.name, nodeServiceInstance);
        nodeServiceInstance = Container.get(config.services.node.name);


        let teste = Result.ok<INodeDTO[]>(result);
        var serv = sinon.stub(nodeServiceInstance, 'findReliefPoints');
        serv.withArgs(sinon.match.string).returns(teste);


        const ctrl = new NodeController(nodeServiceInstance as INodeService);
        //const ctrl = Container.get(NodeController);


        await ctrl.findReliefPoints(<Request>req, <Response>res, <NextFunction>next);

        //sinon.assert.calledOnce(res.json);
        sinon.assert.calledWith(res.json, sinon.match(result));
        serv.restore();
    })


});