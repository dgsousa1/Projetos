import { Component, OnInit } from '@angular/core';
import { FormGroup, FormArray, FormBuilder, FormControl, Validators } from '@angular/forms';
import { DriverTypeModel } from '../../models/driver-type.model';
import { DriverTypeService } from '../../services/driver-type.service';
import { LineModel } from '../../models/line.model';
import { LineService } from '../../services/line.service';
import { PathModel } from '../../models/path.model';
import { PathService } from '../../services/path.service';
import { VehicleTypeService } from '../../services/vehicle-type.service';
import { VehicleTypeModel } from '../../models/vehicle-types.model';
import { NodeService } from 'src/app/services/node.service';
import { NodeModel } from 'src/app/models/node.model';
import { Router } from '@angular/router';
import { FlashMessagesService } from 'angular2-flash-messages';

@Component({
  selector: 'app-create-line',
  templateUrl: './create-line.component.html',
  styleUrls: ['./create-line.component.css']
})
export class CreateLineComponent implements OnInit {
  public lineForm !: FormGroup;

  gos = new FormArray([]);
  returns = new FormArray([]);

  public pathsList !: PathModel[];
  public vehicleTypeList !: VehicleTypeModel[];
  public driverTypeList !: DriverTypeModel[];
  public line !: LineModel;
  public exactPath!: PathModel;
  public nodes!: NodeModel[];

  public input!: string;
  public inputkey!: string;

  public message!: string;
  public errorOccur: boolean = false;

  constructor(private fb: FormBuilder,
    private lineService: LineService,
    private pathService: PathService,
    private vehicleTypeService: VehicleTypeService,
    private driverTypeService: DriverTypeService,
    private nodeService: NodeService,
    private flashMessageService: FlashMessagesService,
    private router: Router) { }

  addPaths() {
    this.gos.push(new FormControl(''));
    this.returns.push(new FormControl(''));
  }

  removePaths() {
    let currentLength = this.gos.length;
    if (this.gos.length > 1) {
      this.gos.removeAt(currentLength - 1);
      this.returns.removeAt(currentLength - 1);
    }
  }

  ngOnInit(): void {
    this.getNodes();
    this.getPaths();
    this.addPaths();
    this.getVehicleTypes();
    this.getDriverTypes();

    this.lineForm = this.fb.group({
      name: ['', Validators.required],
      color: ['#00000', Validators.required],
      pathGo: this.gos,
      pathReturn: this.returns,
      vehicleType: [''],
      driverType: ['']
    })
  }

  getNodes(): void {
    this.nodeService.getNodes('name')
      .subscribe(nodes => {
        this.nodes = nodes;
      })
  }

  getPaths(): void {
    this.pathService.getPaths()
      .subscribe(paths => {
        this.pathsList = paths;
        //
        for (let i = 0; i < this.pathsList.length; i++) {
          for (let j = 0; j < this.pathsList[i].pathNodes.length; j++) {
            for (let k = 0; k < this.nodes.length; k++) {
              if (this.pathsList[i].pathNodes[j].inicialNode == this.nodes[k].nodeId) {
                this.pathsList[i].pathNodes[j].inicialNode = this.nodes[k].shortName;
              }

              if (this.pathsList[i].pathNodes[j].finalNode == this.nodes[k].nodeId) {
                this.pathsList[i].pathNodes[j].finalNode = this.nodes[k].shortName;
              }
            }
          }
        }
      });
  }

  getExactPath(): void {
    this.pathService.getPathByKeyWithNodesName(this.inputkey)
      .subscribe(res => {
        this.exactPath = res;
      })
  }

  getVehicleTypes(): void {
    this.vehicleTypeService.getVehicleTypes()
      .subscribe(vehicleTypes => this.vehicleTypeList = vehicleTypes);
  }

  getDriverTypes(): void {
    this.driverTypeService.getDriverTypes()
      .subscribe(driverTypes => this.driverTypeList = driverTypes);
  }

  convertHexToRbg(hexColor: string): string {
    var parsedColor = hexColor.split('#')[1];
    var result = parsedColor.match(/.{1,2}/g);

    if (result) {
      var r = parseInt(result[0], 16);
      var g = parseInt(result[1], 16);
      var b = parseInt(result[2], 16);
      return "RGB(" + r + "," + g + "," + b + ")";
    }
    return "";
  }

  onSubmit() {
    this.line = this.lineForm.value;

    var colorConverted = this.convertHexToRbg(this.line.color);
    this.line.color = colorConverted;
    var pathsGo: string[];
    var pathsRet: string[];
    pathsGo = this.gos.value;
    pathsRet = this.returns.value;
    //paths go
    for (let i = 0; i < pathsGo.length; i++) {
      for (let x = 0; x < this.pathsList.length; x++) {
        if (pathsGo[i].split(':')[0] == this.pathsList[x].key) {
          pathsGo[i] = this.pathsList[x].pathId;
        }
      }
    }
    //paths return
    for (let j = 0; j < pathsRet.length; j++) {
      for (let y = 0; y < this.pathsList.length; y++) {
        if (pathsRet[j].split(':')[0] == this.pathsList[y].key) {
          pathsRet[j] = this.pathsList[y].pathId;
        }
      }
    }
    //vehicle    
    //console.log(this.vehicleTypeList.length);
    for (let k = 0; k < this.vehicleTypeList.length; k++) {
      if (this.line.vehicleType == this.vehicleTypeList[k].name) {
        this.line.vehicleType = this.vehicleTypeList[k].vehicleTypeId;
      }
    }

    //driver
    for (let p = 0; p < this.driverTypeList.length; p++) {
      if (this.line.driverType == this.driverTypeList[p].name) {
        this.line.driverType = this.driverTypeList[p].driverTypeId;
      }
    }

    //console.log(this.line);

    this.lineService.addLine(this.line).subscribe((response: any) => {
      console.log(response);
      this.flashMessageService.show('Linha criado com sucesso!', { cssClass: 'alert-success', timeout: 3000 });
      this.router.navigate(['/dashboard']);
    },
      (error) => {
        this.flashMessageService.show('Erro! Linha já existe. Tente novamente', { cssClass: 'alert-danger', timeout: 3000 });
        this.lineForm.reset();
        this.lineForm.get('color')?.setValue("#00000");
        this.router.navigate(['/create-line']);
      });
  }


}
