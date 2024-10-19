import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { VehicleService } from 'src/app/services/vehicle.service';
import { FlashMessagesService } from 'angular2-flash-messages';
import { VehicleTypeService } from 'src/app/services/vehicle-type.service';
import { VehicleTypeModel } from 'src/app/models/vehicle-types.model';

@Component({
  selector: 'app-vehicle',
  templateUrl: './vehicle.component.html',
  styleUrls: ['./vehicle.component.css']
})
export class VehicleComponent implements OnInit {

  public vehicleForm !: FormGroup;
  vehicleTypes : VehicleTypeModel[] = [];

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private vehicleService : VehicleService,
    private flashMessageService: FlashMessagesService,
    private vehicleTypeService : VehicleTypeService
  ) { }

  ngOnInit(): void {
    this.getVehicleTypes();
    this.vehicleForm = this.fb.group({
      licensePlate: ['', Validators.required],
      VIN: ['', Validators.required],
      type: ['', Validators.required],
      startDateService: ['', Validators.required]
    })
  }

  getVehicleTypes() : void{
    this.vehicleTypeService.getVehicleTypes()
      .subscribe(data => {
        this.vehicleTypes = data;
        console.log(data);
      });
  }

  onSubmit(): void {
    this.vehicleService.addVehicle(this.vehicleForm.value)
      .subscribe((response : any) =>{
        this.flashMessageService.show('Viatura criada com sucesso!', 
        { cssClass: 'alert-success', timeout: 3000 });
        this.router.navigate(['/dashboard']);
      },
      (error) => {
        this.flashMessageService.show(error, 
        { cssClass: 'alert-danger', timeout: 3000 });
        this.vehicleForm.reset();
        this.router.navigate(['/veiculo']);
      })
  }

}
