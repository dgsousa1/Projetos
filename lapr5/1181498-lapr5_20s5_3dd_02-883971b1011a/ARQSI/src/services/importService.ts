import { Service, Inject } from 'typedi';
import config from "../../config";

import IImportService from './IServices/IImportService';

import convert from 'xml-js';
import fs from 'fs';
import INodeRepo from './IRepos/INodeRepo';
import { UniqueEntityID } from '../core/domain/UniqueEntityID';
import IPathRepo from './IRepos/IPathRepo';
import { PathMap } from '../mappers/PathMap';
import { Path } from '../domain/path';
import IPathDTO from '../dto/IPathDTO';

@Service()
export default class ImportService implements IImportService {
    constructor(
        @Inject(config.repos.node.name) private nodeRepo: INodeRepo,
        @Inject(config.repos.path.name) private pathRepo: IPathRepo
    ) { }

    public async importNodes(): Promise<any[]> {
        try {
            var data = fileSetup();
            var nodes = data.GlDocumentNetwork.Network.Nodes.Node;
            var props = [nodes.length];
            for (let index = 0; index < nodes.length; index++) {
                var v = nodes[index];
                var prop = {
                    name: v._attributes.Name,
                    shortName: v._attributes.ShortName,
                    latitude: parseFloat(v._attributes.Latitude),
                    longitude: parseFloat(v._attributes.Longitude),
                    isDepot: (v._attributes.IsDepot === 'True'),
                    isReliefPoint: (v._attributes.IsReliefPoint === 'True')
                };
                props[index] = prop;
            }
            //console.log(props)
            return props;

        } catch (e) {
            throw e;
        }
    }

    public async importVehicleTypes(): Promise<any[]> {
        try {
            var data = fileSetup();
            var vehicles = data.GlDocumentNetwork.Network.VehicleTypes.VehicleType;
            var props = [vehicles.length];
            for (let index = 0; index < vehicles.length; index++) {
                var v = vehicles[index];
                var prop = {
                    name: v._attributes.Name,
                    fuelType: v._attributes.EnergySource,
                    autonomy: parseInt(v._attributes.Autonomy),
                    costPerKilometer: parseFloat(v._attributes.Cost),
                    consumption: parseFloat(v._attributes.Consumption),
                    averageSpeed: parseInt(v._attributes.AverageSpeed),
                    description: v._attributes.Name,
                    emission: parseFloat(v._attributes.Emissions)
                };
                props[index] = prop;
            }
            return props;

        } catch (e) {
            throw e;
        }
    }

    public async importDriverTypes(): Promise<any[]> {
        try {
            var data = fileSetup();
            var drivers = data.GlDocumentSchedule.Schedule.DriverTypes.DriverType;
            var props = [drivers.length];
            for (let index = 0; index < drivers.length; index++) {
                var v = drivers[index];
                var prop = {
                    name: v._attributes.Name,
                    description: v._attributes.Description
                };
                props[index] = prop;
            }
            return props;

        } catch (e) {
            throw e;
        }
    }

    public async importPaths(): Promise<any[]> {
        try {
            var data = fileSetup();
            var paths = data.GlDocumentNetwork.Network.Paths.Path;

            var i = 0;
            var x = 1;

            var pathProps = [paths.length];

            for (let index = 0; index < paths.length; index++) {
                const path = paths[index];

                var pathNode = path.PathNodes.PathNode;
                var pathItems = [];
                do {
                    var inicialN = pathNode[i];
                    var finalN = pathNode[i + 1];

                    var inicialNodeId = await this.searchByXMLKeyToReturnBDid(inicialN._attributes.Node);
                    var finalNodeId = await this.searchByXMLKeyToReturnBDid(finalN._attributes.Node);

                    var propsItem = {
                        duracao: parseInt(finalN._attributes.Duration),
                        distancia: parseInt(finalN._attributes.Distance),
                        inicialNode: inicialNodeId.toValue(),//'Node:X'
                        finalNode: finalNodeId.toValue()//Node:Y
                    }

                    pathItems[i] = propsItem;

                    x++;
                    i++;
                } while (i + 1 < pathNode.length);

                var pathKey = (paths[index]._attributes.key).split(':')[1];

                pathProps[index] = {
                    key: pathKey,
                    pathNodes: pathItems
                }

                pathItems = [];

                i = 0;
            }

            return pathProps;

        } catch (e) {
            throw e;
        }
    }

    public async importLines(): Promise<any[]> {
        try {
            var data = fileSetup();
            var lines = data.GlDocumentNetwork.Network.Lines.Line;

            var i = 0;
            var x = 1;

            var lineProps = [lines.length];

            for (let index = 0; index < lines.length; index++) {
                var pathsGo = [];
                var pathsReturn = [];
                const line = lines[index];

                var linePaths = line.LinePaths.LinePath;

                var jGo = 0;
                var jRet = 0;
                for (let j = 0; j < linePaths.length; j++) {

                    if (linePaths[j]._attributes.Orientation == "Return") {
                        var pathKeyRet = linePaths[j]._attributes.Path;
                        var pathRetID = await this.pathRepo.findByKey(parseInt(pathKeyRet.split(':')[1]));
                        pathsReturn[jRet] = pathRetID.id.toValue();
                        jRet++;
                    } else if (linePaths[j]._attributes.Orientation == "Go") {
                        var pathKeyGo = linePaths[j]._attributes.Path;
                        var pathGoID = await this.pathRepo.findByKey(parseInt(pathKeyGo.split(':')[1]));
                        pathsGo[jGo] = pathGoID.id.toValue();
                        jGo++;
                    }
                }

                lineProps[index] = {
                    name: line._attributes.Name,
                    color: line._attributes.Color,
                    pathGo: pathsGo,
                    pathReturn: pathsReturn,
                    vehicleType: '',
                    driverType: ''
                }
            }

            return lineProps;

        } catch (e) {
            throw e;
        }
    }

    public async searchByXMLKeyToReturnBDid(NodeXmlKey: string): Promise<UniqueEntityID> {
        var data = fileSetup();
        var nodes = data.GlDocumentNetwork.Network.Nodes.Node;

        for (let index = 0; index < nodes.length; index++) {
            var v = nodes[index];
            if (v._attributes.key == NodeXmlKey) {
                var NodeName = v._attributes.Name;
                var exactNodeId = await this.nodeRepo.findByExactName(NodeName);

                //return nodeId;
                return exactNodeId.id;
            }
        }

    }

    public insertIntoLogger(content: string) {
        var date = Date();
        var lineFormat = date.toString() + ": [" + content + "]\n";
        fs.appendFile('./logger.txt', lineFormat, function (err) {
            if (err) return console.log(err);
        });
    }
}

function fileSetup() {
    var xml = fs.readFileSync('./demo-lapr5.glx.xml', { encoding: 'utf8' });
    var res = convert.xml2json(xml, { compact: true, spaces: 4 });
    var jsonData = JSON.parse(res);
    return jsonData.GlDocumentInfo.world.GlDocument;
}
