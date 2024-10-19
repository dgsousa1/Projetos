import { Service, Inject } from 'typedi';
import config from "../../config";
import ILineDTO from '../dto/ILineDTO';
import { Line } from '../domain/line';
import ILineRepo from './IRepos/ILineRepo';
import ILineService from './IServices/ILineService';
import { Result } from "../core/logic/Result";
import { LineMap } from '../mappers/LineMap';
import IPathRepo from './IRepos/IPathRepo';
import IPathDTO from '../dto/IPathDTO';
import { PathMap } from '../mappers/PathMap';
import INodeRepo from './IRepos/INodeRepo';
import IVehicleTypeRepo from './IRepos/IVehicleTypeRepo';
import IDriverTypeRepo from './IRepos/IDriverTypeRepo';
import { result } from 'lodash';

@Service()
export default class LineService implements ILineService {

  /**
   * Create Line Service
   * @param lineRepo Injected ILineRepo into LineService
   * @param pathRepo Injected IPathRepo into LineService
   * @param nodeRepo Injected INodeRepo into LineService
   */
  constructor(
    @Inject(config.repos.line.name) private lineRepo: ILineRepo,
    @Inject(config.repos.path.name) private pathRepo: IPathRepo,
    @Inject(config.repos.node.name) private nodeRepo: INodeRepo,
    @Inject(config.repos.vehicleType.name) private vehicleTypeRepo: IVehicleTypeRepo,
    @Inject(config.repos.driverType.name) private driverTypeRepo: IDriverTypeRepo
  ) { }

  /**
  * Gets Line saved in the DataBase with the exact name received
   * @param name Line name
   * @returns ILineDTO
   */
  private async findByExactName(name: string): Promise<Result<ILineDTO>> {
    try {
      const line = await this.lineRepo.findByExactName(name);
      const lineDTOResult = LineMap.toDTO(line) as ILineDTO;
      if (line === null) {
        return Result.fail<ILineDTO>("Line not found");
      }
      else {
        return Result.ok<ILineDTO>(lineDTOResult);
      }
    } catch (e) {
      throw e;
    }
  }

  /**
   * Creates and saves Line into the DataBase
   * @param lineDTO ILineDTO to be created
   * @returns ILineDTO created
   */
  public async createLine(lineDTO: ILineDTO): Promise<Result<ILineDTO>> {
    try {
      const lineOrError = Line.create(lineDTO);
      if (lineOrError.isFailure) {
        return Result.fail<ILineDTO>(lineOrError.errorValue());
      }
      const lineResult = lineOrError.getValue();
      var lineError = await this.lineRepo.save(lineResult);
      if (lineError === null) {
        return null;
      }
      const lineDTOResult = LineMap.toDTO(lineResult) as ILineDTO;
      return Result.ok<ILineDTO>(lineDTOResult)
    } catch (e) {
      throw e;
    }
  }

  /**
  * Converts List of Lines to JSON body without ids
  * @param lineList Lines
  */
  private async getFormatWithoutIds(element: Line): Promise<any> {
    var vehicleTypeId: any = element.vehicleType;
    var driverTypeId: any = element.driverType;
    var vehicleType: any = await this.vehicleTypeRepo.findByDomainId(vehicleTypeId);
    var driverType: any = await this.driverTypeRepo.findByDomainId(driverTypeId);
    var vehicleTypeRet;
    var driverTypeRet;
    if (vehicleType === null) {
      vehicleTypeRet = '';
    } else {
      vehicleTypeRet = vehicleType.name;
    }

    if (driverType === null) {
      driverTypeRet = '';
    } else {
      driverTypeRet = driverType.name;
    }
    var pathGoKeys = new Array();
    for (let j = 0; j < element.pathGo.length; j++) {
      var pathGo = await this.pathRepo.findByDomainId(element.pathGo[j]);
      pathGoKeys.push(pathGo.key);
    }
    var pathReturnKeys = new Array();
    for (let j = 0; j < element.pathReturn.length; j++) {
      var pathReturn = await this.pathRepo.findByDomainId(element.pathReturn[j]);
      pathReturnKeys.push(pathReturn.key);
    }

    return {
      name: element.name,
      color: element.color,
      pathGo: pathGoKeys,
      pathReturn: pathReturnKeys,
      vehicleType: vehicleTypeRet,
      driverType: driverTypeRet
    };
  }

  /**
   * Gets all Lines saved in the DataBase
   * @param order Order of the list of Lines
   * @returns List of Lines
   */
  public async findAll(order: string): Promise<Result<ILineDTO[]>> {
    try {
      const resultDTO = new Array();
      const lineList = await this.lineRepo.findAll(order);
      for (let index = 0; index < lineList.length; index++) {
        const element = lineList[index];
        const lineDTOResult = LineMap.toDTO(element) as ILineDTO;
        resultDTO.push(lineDTOResult);
      }
      return Result.ok<ILineDTO[]>(resultDTO);
    } catch (e) {
      throw e;
    }
  }

  /**
   * Gets Lines saved in the DataBase with the received name
   * @param name Lines name
   * @param order Order of the list of Lines
   * @returns List of Lines
   */
  public async findByName(name: string, order: string): Promise<Result<ILineDTO[]>> {
    try {
      const result = new Array();
      const resultDTO = new Array();
      const lineList = await this.lineRepo.findByName(name, order);
      for (let index = 0; index < lineList.length; index++) {
        const element = lineList[index];
        const listDTOResult = LineMap.toDTO(element) as ILineDTO;
        resultDTO.push(listDTOResult);
      }
      return Result.ok<ILineDTO[]>(resultDTO);
    } catch (e) {
      throw e;
    }
  }

  /**
   * Gets Line with the received Id
   * @param lineId LineId
   * @returns Object Line
   */
  public async findById(id: string): Promise<Result<ILineDTO>> {
    try {
      const line = await this.lineRepo.findByDomainId(id);
      const lineDTOResult = LineMap.toDTO(line) as ILineDTO;
      if (line === null) {
        return Result.fail<ILineDTO>("Line not found");
      }
      else {
        return Result.ok<ILineDTO>(lineDTOResult);
      }
    } catch (e) {
      throw e;
    }
  }

  /**
   * Lists Paths from Line with the received id
   * @param id LineId
   * @returns Paths from Line
   */
  public async findPathsByLineId(id: string): Promise<Result<any>> {
    let lineDTO = (await this.findById(id)).getValue();
    var goList = lineDTO.pathGo;
    var returnList = lineDTO.pathReturn;
    var goListResult = new Array();
    var returnListResult = new Array();
    for (let i = 0; i < goList.length; i++) {
      var path = goList[i];
      var pathGo = await this.pathRepo.findByDomainId(path);
      var pathGoFomat = PathMap.toDTO(pathGo) as IPathDTO;
      goListResult.push(pathGoFomat);
    }
    for (let i = 0; i < returnList.length; i++) {
      var path = returnList[i];
      var pathReturn = await this.pathRepo.findByDomainId(path);
      var pathReturnFomat = PathMap.toDTO(pathReturn) as IPathDTO;
      returnListResult.push(pathReturnFomat);
    }
    var bodyJSON = {
      id: lineDTO.name,
      goPaths: goListResult,
      returnPaths: returnListResult
    }
    return Result.ok<any>(bodyJSON);
  }

  /**
   * Lists Paths from Line with the received name
   * @param name Name of the Line
   * @returns Paths from Line with names instead of Id
   */
  public async findPathsFromLineWithName(name: string): Promise<Result<any>> {
    console.log('INICIO');
    let lineDTO = (await this.findByExactName(name)).getValue();
    var goList = lineDTO.pathGo;
    var returnList = lineDTO.pathReturn;
    var goListResult = new Array();
    var returnListResult = new Array();
    for (let i = 0; i < goList.length; i++) {
      var path = goList[i];
      var pathGo = await this.pathRepo.findByDomainId(path);
      var pathGoFomat = PathMap.toDTO(pathGo) as IPathDTO;
      var goPathNodesJSON = new Array();
      for (var y = 0; y < pathGoFomat.pathNodes.length; y++) {
        var pathNode = pathGoFomat.pathNodes[y];
        var iN = await this.nodeRepo.findByDomainId(pathNode.inicialNode);
        var fN = await this.nodeRepo.findByDomainId(pathNode.finalNode);
        
        var pathNodeJSON = {
          id: pathNode.id,
          duracao: pathNode.duracao,
          distancia: pathNode.distancia,
          inicialNode: iN.name,
          finalNode: fN.name
        }
        goPathNodesJSON.push(pathNodeJSON);
      }
      var pathJSON = {
        key: pathGoFomat.key,
        pathNodes: goPathNodesJSON
      }
      goListResult.push(pathJSON);
    }
    for (let i = 0; i < returnList.length; i++) {
      var path = returnList[i];
      var pathReturn = await this.pathRepo.findByDomainId(path);
      var pathReturnFomat = PathMap.toDTO(pathReturn) as IPathDTO;
      var returnPathNodesJSON = new Array();
      for (var y = 0; y < pathReturnFomat.pathNodes.length; y++) {
        var pathNode2 = pathReturnFomat.pathNodes[y];
        var iN2 = await this.nodeRepo.findByDomainId(pathNode2.inicialNode);

        var fN2 = await this.nodeRepo.findByDomainId(pathNode2.finalNode);
        
        console.log(iN2.name);
        console.log(fN2.name);
        
        var pathNodeJSON2 = {
          id: pathNode2.id,
          duracao: pathNode2.duracao,
          distancia: pathNode2.distancia,
          inicialNode: iN2.name,
          finalNode: fN2.name
        }
        returnPathNodesJSON.push(pathNodeJSON2);
      }
      var pathJSON = {
        key: pathReturnFomat.key,
        pathNodes: returnPathNodesJSON
      }
      returnListResult.push(pathJSON);
    }
    var bodyJSON = {
      name: lineDTO.name,
      goPaths: goListResult,
      returnPaths: returnListResult
    }
    return Result.ok<any>(bodyJSON);
  }

  /**
   * Lists coordinates from all Nodes in a Line with the received name
   * @param id LineId
   * @returns List of resulting coordinates from all Nodes in a Line
   */
  public async findCoordinatesByLineId(id: string): Promise<Result<any>> {
    let lineDTO = (await this.findById(id)).getValue();
    var goList = lineDTO.pathGo;
    var returnList = lineDTO.pathReturn;
    var listOfGoPathDTO = new Array<IPathDTO>();
    var listOfReturnPathDTO = new Array<IPathDTO>();
    var listOfGoCoords = new Array();
    var listOfReturnCoords = new Array();
    for (var i = 0; i < goList.length; i++) {
      var pathIdGo = goList[i];
      var pathGo = await this.pathRepo.findByDomainId(pathIdGo);
      var pathGoDTO = PathMap.toDTO(pathGo) as IPathDTO;
      listOfGoPathDTO.push(pathGoDTO);
    }
    for (var a = 0; a < listOfGoPathDTO.length; a++) {
      var current = listOfGoPathDTO[a];
      for (var b = 0; b < current.pathNodes.length; b++) {
        var now = current.pathNodes[b];
        var iN = (await this.nodeRepo.findByDomainId(now.inicialNode));
        var fN = (await this.nodeRepo.findByDomainId(now.finalNode));
        var inLng = iN.longitude;
        var inLan = iN.latitude;
        var fLng = fN.longitude;
        var fLan = fN.latitude;
        var goJson = {
          key: current.key,
          lan1: inLan,
          lng1: inLng,
          lan2: fLan,
          lng2: fLng
        }
        listOfGoCoords.push(goJson);
      }
    }
    for (var i = 0; i < returnList.length; i++) {
      var pathIdReturn = returnList[i];
      var pathReturn = await this.pathRepo.findByDomainId(pathIdReturn);
      var pathReturnDTO = PathMap.toDTO(pathReturn) as IPathDTO;
      listOfReturnPathDTO.push(pathReturnDTO);
    }
    for (var a = 0; a < listOfReturnPathDTO.length; a++) {
      var current = listOfReturnPathDTO[a];
      for (var b = 0; b < current.pathNodes.length; b++) {
        var now = current.pathNodes[b];
        var iN = (await this.nodeRepo.findByDomainId(now.inicialNode));
        var fN = (await this.nodeRepo.findByDomainId(now.finalNode));
        var inLng = iN.longitude;
        var inLan = iN.latitude;
        var fLng = fN.longitude;
        var fLan = fN.latitude;
        var returnJson = {
          key: current.key,
          lan1: inLan,
          lng1: inLng,
          lan2: fLan,
          lng2: fLng
        }
        listOfReturnCoords.push(returnJson);
      }
    }
    var bodyJSON = {
      id: lineDTO.name,
      color: lineDTO.color,
      goPaths: listOfGoCoords,
      returnPaths: listOfReturnCoords
    }
    return Result.ok<any>(bodyJSON);
  }

  /**
   * Gets all Lines saved in the DataBase
   * @param order Order of the list of Lines
   * @returns List of Lines
   */
  public async findAllWithoutId(order: string): Promise<Result<any[]>> {
    try {
      const resultDTO = new Array();
      const lineList = await this.lineRepo.findAll(order);
      for (let index = 0; index < lineList.length; index++) {
        resultDTO.push(await this.getFormatWithoutIds(lineList[index]));
      }
      return Result.ok<any>(resultDTO);
    } catch (e) {
      throw e;
    }
  }

  /**
  * Gets Lines saved in the DataBase with the received name
  * @param name Lines name
  * @param order Order of the list of Lines
  * @returns List of Lines
  */
  public async findByNameWithoutId(name: string, order: string): Promise<Result<ILineDTO[]>> {
    try {
      const result = new Array();
      const resultDTO = new Array();
      const lineList = await this.lineRepo.findByName(name, order);
      for (let index = 0; index < lineList.length; index++) {
        resultDTO.push(await this.getFormatWithoutIds(lineList[index]));
      }
      return Result.ok<ILineDTO[]>(resultDTO);
    } catch (e) {
      throw e;
    }
  }

  /**
   * Gets Line with the received Id
   * @param lineId LineId
   * @returns Object Line
   */
  public async findByIdWithoutId(id: string): Promise<Result<ILineDTO>> {
    try {
      const line = await this.lineRepo.findByDomainId(id);
      const lineDTOResult = await this.getFormatWithoutIds(line);
      if (line === null) {
        return Result.fail<ILineDTO>("Line not found");
      }
      else {
        return Result.ok<ILineDTO>(lineDTOResult);
      }
    } catch (e) {
      throw e;
    }
  }
}