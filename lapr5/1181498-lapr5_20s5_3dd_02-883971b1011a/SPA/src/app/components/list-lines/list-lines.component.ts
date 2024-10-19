import { ThisReceiver } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { DriverTypeModel } from '../../models/driver-type.model';
import { DriverTypeService } from '../../services/driver-type.service';
import { LineModel } from '../../models/line.model';
import { LineService } from '../../services/line.service';
import { PathModel } from '../../models/path.model';
import { PathService } from '../../services/path.service';
import { VehicleTypeService } from '../../services/vehicle-type.service';
import { VehicleTypeModel } from '../../models/vehicle-types.model';
import { NodeModel } from 'src/app/models/node.model';
import { NodeService } from 'src/app/services/node.service';

@Component({
  selector: 'app-list-lines',
  templateUrl: './list-lines.component.html',
  styleUrls: ['./list-lines.component.css']
})
export class ListLinesComponent implements OnInit {

  public pathsList !: PathModel[];
  public vehicleTypeList !: VehicleTypeModel[];
  public driverTypeList !: DriverTypeModel[];
  public lines !: LineModel[];
  public finalLines !: LineModel[];
  public nodes!: NodeModel[];

  public exactLine!: LineModel;

  public input: string = '';
  public orderType: string = 'name'
  public nameOrID: string | undefined;

  constructor(private lineService: LineService,
    private pathService: PathService,
    private vehicleTypeService: VehicleTypeService,
    private driverTypeService: DriverTypeService,
    private nodeService : NodeService) { }

  ngOnInit(): void {
    this.getNodes();
    this.getPaths();
    this.getVehicleTypes();
    this.getDriverTypes();
  }

  getNodes() : void{
    this.nodeService.getNodes('name')
    .subscribe(nodes => {
      this.nodes = nodes;
    });
  }

  getPaths(): void {
    this.pathService.getPaths()
      .subscribe(paths => {
        this.pathsList = paths;
      });
  }

  getLines(order: any): void {
    this.lineService.getLines(order)
      .subscribe(lines => {
        this.lines = lines;
      });
  }

  getLinesByName(name: string, order: string): void {
    this.lineService.getLinesByName(name, order)
      .subscribe(lines => {
        this.lines = lines;
      });
  }

  getLineById(id: any): void {
    this.lines = [];
    this.lineService.getLinesById(id)
      .subscribe(exactLine => {
        this.lines[0] = exactLine;
        console.log(this.lines[0]);
      });
  }

  getVehicleTypes(): void {
    this.vehicleTypeService.getVehicleTypes()
      .subscribe(vehicleTypes => this.vehicleTypeList = vehicleTypes);
  }

  getDriverTypes(): void {
    this.driverTypeService.getDriverTypes()
      .subscribe((response) => {
        this.driverTypeList = response;
      });
  }

  getStuff() {
    if (this.nameOrID == undefined) {
      this.getLines(this.orderType);
    }

    if (this.nameOrID == 'name') {
      this.getLinesByName(this.input, this.orderType);
    }

    if (this.nameOrID == 'id') {
      this.getLineById(this.input);
    }
/*
    //percorrer line list
    for (let i = 0; i < this.lines.length; i++) {
      let pathsGo = this.lines[i].pathGo;
      let pathsRet = this.lines[i].pathReturn;

      for (let x = 0; x < pathsGo.length; x++) {
        for (let x1 = 0; x1 < this.pathsList.length; x1++) {

          if (pathsGo[x] == this.pathsList[x1].pathId) {
            this.lines[i].pathGo[x] = this.pathsList[x1].key.toString();
          }
        }
      }
      for (let y = 0; y < pathsRet.length; y++) {
        for (let y1 = 0; y1 < this.pathsList.length; y1++) {

          if (pathsRet[y] == this.pathsList[y1].pathId) {
            this.lines[i].pathReturn[y] = this.pathsList[y1].key.toString();
          }
        }
      }
      //substituir para drivers
      for (let j = 0; j < this.driverTypeList.length; j++) {
        if (this.lines[i].driverType == this.driverTypeList[j].driverTypeId) {
          this.lines[i].driverType = this.driverTypeList[j].name;
        }
      }
      for (let k = 0; k < this.vehicleTypeList.length; k++) {
        if (this.lines[i].vehicleType == this.vehicleTypeList[k].vehicleTypeId) {
          this.lines[i].vehicleType = this.vehicleTypeList[k].name;
        }
      }

      this.finalLines = this.lines;
    }*/
  }

  clearInputs(): void {
    this.orderType = 'name';
    this.nameOrID = undefined;
    this.input = '';
    this.finalLines = [];
  }
}

