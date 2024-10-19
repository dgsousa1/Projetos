import { expect } from 'chai';
import * as sinon from 'sinon';
import { Response, Request, NextFunction } from 'express';
import { Container } from 'typedi';
import config from "../../config";
import IPathService from "../services/IServices/IPathService";
import PathController from "./pathController";
import IPathDTO from "../dto/IPathDTO";
import { Result } from '../core/logic/Result';
import PathService from "../services/pathService";
import IPathController from './IControllers/IPathController';

describe('path controller', function () {
    beforeEach(function () {

    })

    afterEach(function () {

    })

    /* it('should create a path and return a dto', async function () {
 
         const body = {
             "key": 50,
             "pathNodes": [
                 {
                     "duracao": 0,
                     "distancia": 2,
                     "inicialNode": '0c936221-1f44-4b89-8e06-d725a15e3d60',
                     "finalNode": 'd2046e9d-def7-4059-a545-12dd748a00b6'
                 }
             ]
         };
 
         let req: Partial<Request> = {};
         req.body = body;
 
         let res: Partial<Response> = {
             json: sinon.spy(),
             status: function () { this.statusCode = 201; return this; }
         };
 
         let next: Partial<NextFunction> = () => { };
         let pathServiceClass = require(config.services.path.path).default;
         let pathServiceInstance: IPathService = Container.get(pathServiceClass)
 
         Container.set(config.services.path.name, pathServiceInstance);
         pathServiceInstance = Container.get(config.services.path.name);
 
         let teste = Result.ok<any>(body);
         var serv = sinon.stub(pathServiceInstance, 'createPath');
         serv.withArgs(sinon.match.any).returns(teste);
 
 
         const ctrl = new PathController(pathServiceInstance as IPathService);
 
         await ctrl.createPath(<Request>req, <Response>res, <NextFunction>next);
 
         sinon.assert.calledWith(res.json, sinon.match(body));
         serv.restore();
     })
 
    it('should attempt to create a path and fail', async function () {

        const body = {
            "key": 50,
        };

        let req: Partial<Request> = {};
        req.body = body;

        let res: Partial<Response> = {
            json: sinon.spy(),
            status: function () { this.statusCode = 402; return this; }
        };

        let next: Partial<NextFunction> = () => { };

        let pathServiceClass = require(config.services.path.path).default;
        let pathServiceInstance: IPathService = Container.get(pathServiceClass)

        Container.set(config.services.path.name, pathServiceInstance);
        pathServiceInstance = Container.get(config.services.path.name);

        const ctrl = new PathController(pathServiceInstance as IPathService);


        await ctrl.createPath(<Request>req, <Response>res, <NextFunction>next);

        sinon.assert.failException;
    })
*/
   /* it('should try to get all paths and return a dto', async function () {

        const result = [{
            "key": 50,
            "pathNodes": [
                {
                    "duracao": 0,
                    "distancia": 2,
                    "inicialNode": '0c936221-1f44-4b89-8e06-d725a15e3d60',
                    "finalNode": 'd2046e9d-def7-4059-a545-12dd748a00b6'
                }
            ]
        },
        {
            "key": 51,
            "pathNodes": [
                {
                    "duracao": 0,
                    "distancia": 2,
                    "inicialNode": 'd2046e9d-def7-4059-a545-12dd748a00b6',
                    "finalNode": '0c936221-1f44-4b89-8e06-d725a15e3d60'
                }
            ]
        }]

        let req: Partial<Request> = {};

        let res: Partial<Response> = {
            json: sinon.spy(),
            status: function () { this.statusCode = 201; return this; }
        };

        let next: Partial<NextFunction> = () => { };
        let pathServiceClass = require(config.services.path.path).default;
        let pathServiceInstance: IPathService = Container.get(pathServiceClass)

        Container.set(config.services.path.name, pathServiceInstance);
        pathServiceInstance = Container.get(config.services.path.name);

        let teste = Result.ok<any>(result)
        var serv = sinon.stub(pathServiceInstance, 'findAll').returns(teste);

        const ctrl = new PathController(pathServiceInstance as IPathService);
        //const ctrl = Container.get(PathController);

        await ctrl.findAll(<Request>req, <Response>res, <NextFunction>next);

        //sinon.assert.calledOnce(res.json);
        sinon.assert.calledWith(res.json, sinon.match(result));
        //sinon.assert.match(res,result)
        serv.restore();
    })

*/

   /* it('should find a path by key and return it in dto form', async function () {
        const result = {
            "key": 50,
            "pathNodes": [
                {
                    "duracao": 0,
                    "distancia": 2,
                    "inicialNode": '0c936221-1f44-4b89-8e06-d725a15e3d60',
                    "finalNode": 'd2046e9d-def7-4059-a545-12dd748a00b6'
                }
            ]
        }

        let req: Partial<Request> = {};
        req.query = { key: "50" };

        let res: Partial<Response> = {
            json: sinon.spy(),
            status: function () { this.statusCode = 201; return this; }
        };


        let next: Partial<NextFunction> = () => { };

        let pathServiceClass = require(config.services.path.path).default;
        let pathServiceInstance: IPathService = Container.get(pathServiceClass)

        Container.set(config.services.path.name, pathServiceInstance);
        pathServiceInstance = Container.get(config.services.path.name);

        let teste = Result.ok<any>(result)
        var serv = sinon.stub(pathServiceInstance, 'findByKey').returns(teste);

        const ctrl = new PathController(pathServiceInstance as IPathService);
        //const ctrl = Container.get(PathController);


        await ctrl.findByKey(<Request>req, <Response>res, <NextFunction>next);

        //sinon.assert.calledOnce(res.json);
        sinon.assert.calledWith(res.json, sinon.match(result));
        //sinon.assert.match(res,result)
        serv.restore();
    }) */
});