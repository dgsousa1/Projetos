import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { DriverService } from 'src/app/services/driver.service';
import { FlashMessagesService } from 'angular2-flash-messages';
import { DriverTypeModel } from 'src/app/models/driver-type.model';
import { DriverTypeService } from 'src/app/services/driver-type.service';

@Component({
  selector: 'app-driver',
  templateUrl: './driver.component.html',
  styleUrls: ['./driver.component.css']
})
export class DriverComponent implements OnInit {
  
  public driverForm !: FormGroup;
  public driverTypes: DriverTypeModel[] = [];

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private driverService: DriverService,
    private flashMessageService: FlashMessagesService,
    private driverTypeService : DriverTypeService
  ) { }

  ngOnInit(): void {
    this.getDriverTypes();
    this.driverForm = this.fb.group({
      mecNumber: ['', Validators.required],
      name: ['', Validators.required],
      birthDate: ['', Validators.required],
      cc: ['', Validators.required],
      nif: ['', Validators.required],
      driverType: ['', Validators.required],
      startDateCompany: ['', Validators.required],
      finalDateCompany: ['', Validators.required],
      licenseNumber: ['', Validators.required],
      licenseDate: ['', Validators.required],
    })
  }

  getDriverTypes() : void{
    this.driverTypeService.getDriverTypes()
      .subscribe(data => this.driverTypes = data);
  }

  onSubmit(): void {
    this.driverService.addDriver(this.driverForm.value)
      .subscribe((response: any) => {
        this.flashMessageService.show('Tripulante criado com sucesso!', { cssClass: 'alert-success', timeout: 3000 });
        this.router.navigate(['/dashboard']);
      },
        (error) => {
          this.flashMessageService.show('Ocorreu um erro! Tente novamente', { cssClass: 'alert-danger', timeout: 3000 });
          this.driverForm.reset();
          this.router.navigate(['/tripulante']);
        });
  }

}
