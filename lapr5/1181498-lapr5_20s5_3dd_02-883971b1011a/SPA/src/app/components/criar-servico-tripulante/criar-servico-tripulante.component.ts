import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { DriverModel } from 'src/app/models/driver-model';
import { WorkblockModel } from 'src/app/models/workblock-model';
import { DriverService } from 'src/app/services/driver.service';
import { ServicoTripulanteService } from 'src/app/services/servico-tripulante.service';
import { WorkBlockService } from 'src/app/services/work-block.service';

@Component({
  selector: 'app-criar-servico-tripulante',
  templateUrl: './criar-servico-tripulante.component.html',
  styleUrls: ['./criar-servico-tripulante.component.css']
})
export class CriarServicoTripulanteComponent implements OnInit {

  drivers: DriverModel[] = [];
  public workblocks: WorkblockModel[] = [];
  public driverDutyForm !: FormGroup;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private workBlockService: WorkBlockService,
    private driverService: DriverService,
    private driverDutyService: ServicoTripulanteService) { }

  ngOnInit(): void {
    this.getDrivers();
    this.getWorkBlocks();
    this.driverDutyForm = this.fb.group({
      mecNumber: ['', Validators.required],
      driverName: ['', Validators.required],
      color: ['#00000', Validators.required],
      type: ['', Validators.required],
      workblocks: ['', Validators.required],
      duration: ['', Validators.required],
      validDate: ['', Validators.required],
    });
  }

  getDrivers(): void {
    this.driverService.getDrivers()
      .subscribe(data => {
        this.drivers = data;
      })
  }

  getWorkBlocks(): void {
    this.workBlockService.getWorkBlocks()
      .subscribe(data => {
        this.workblocks = data;
      })
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
    console.log(this.driverDutyForm.value);

    let validDate = this.driverDutyForm.get('validDate')?.value;
    let a = validDate.replace(/T/g, " ");
    let formatedvalidDate = a.replace(/-/g, "/").concat(':00');

    let sd = this.driverDutyForm.value;
    var colorConverted = this.convertHexToRbg(sd.color);

    let wb: string;
    console.log('b:  ' + this.driverDutyForm.get('workblocks')?.value);

    wb = this.driverDutyForm.get('workblocks')?.value;
    let wbCode: string[] = [];

    for (let i = 0; i < wb.length; i++) {
      console.log('wb: ' + wb[i].toString());
      let eachWBString = wb[i];
      let s = eachWBString.split(',')[0];
      let sub = s.substring(s.lastIndexOf(":") + 2);
      wbCode.push(sub);
    }

    for (let x = 0; x < wbCode.length; x++) {
      console.log('WB:' + wbCode[x]);
    }

    const driverDuty = {
      mecNumber: this.driverDutyForm.get('mecNumber')?.value,
      driverName: this.driverDutyForm.get('driverName')?.value,
      color: colorConverted,
      type: this.driverDutyForm.get('type')?.value,
      workBlocks: wbCode,
      duration: this.driverDutyForm.get('duration')?.value,
      validDate: formatedvalidDate,
    }

    console.log('AQUI:::: ' + JSON.stringify(driverDuty));

    this.driverDutyService.addDriverDuty(driverDuty)
      .subscribe(data => {
        console.log(data);
      },
        (error) => {
          console.log('error:' + error);
        });
  }

}
