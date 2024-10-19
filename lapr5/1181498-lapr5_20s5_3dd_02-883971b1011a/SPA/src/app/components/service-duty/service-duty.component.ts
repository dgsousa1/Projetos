import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { FlashMessagesService } from 'angular2-flash-messages';
import { WorkblockModel } from 'src/app/models/workblock-model';
import { ServiceDutyService } from 'src/app/services/service-duty.service';
import { WorkBlockService } from 'src/app/services/work-block.service';

@Component({
  selector: 'app-service-duty',
  templateUrl: './service-duty.component.html',
  styleUrls: ['./service-duty.component.css']
})
export class ServiceDutyComponent implements OnInit {

  public serviceDutyForm !: FormGroup;
  public workblocks: WorkblockModel[] = [];

  constructor(private fb: FormBuilder,
    private router: Router,
    private workBlockService: WorkBlockService,
    private serviceDutyService: ServiceDutyService,
    private flashMessageService: FlashMessagesService) { }

  ngOnInit(): void {
    this.getWorkBlocks();
    this.serviceDutyForm = this.fb.group({
      name: ['', Validators.required],
      code: ['', Validators.required],
      workblocks: ['', Validators.required],
      validDate: ['', Validators.required],
      color: ['#00000', Validators.required]
    });
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
    console.log('sd:' + this.serviceDutyForm.value);

    let validDate = this.serviceDutyForm.get('validDate')?.value;
    let a = validDate.replace(/T/g, " ");
    let formatedvalidDate = a.replace(/-/g, "/").concat(':00');

    let sd = this.serviceDutyForm.value;
    var colorConverted = this.convertHexToRbg(sd.color);

    let wb: string;
    console.log('b:  ' + this.serviceDutyForm.get('workblocks')?.value);

    wb = this.serviceDutyForm.get('workblocks')?.value;
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

    const serviceDuty = {
      name: this.serviceDutyForm.get('name')?.value,
      code: (this.serviceDutyForm.get('code')?.value).toString(),
      workBlocks: wbCode,
      duration: 0,
      validDate: formatedvalidDate,
      color: colorConverted
    }

    console.log('serviceDuty: ' + JSON.stringify(serviceDuty));

    this.serviceDutyService.addServiceDuty(serviceDuty)
      .subscribe((response: any) => {
        console.log('response:' + response);
        this.flashMessageService.show('Serviço de viatura criado com sucesso!', { cssClass: 'alert-success', timeout: 3000 });
        this.router.navigate(['/dashboard']);
      },
        (error) => {
          console.log('error:' + error);
          this.flashMessageService.show('Ocorreu um erro! Tente novamente', { cssClass: 'alert-danger', timeout: 3000 });
          this.serviceDutyForm.reset();
          this.router.navigate(['/service-duty']);
        });
  }

}