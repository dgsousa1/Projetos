import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { VehicleTypeService } from '../../services/vehicle-type.service';

@Component({
  selector: 'app-create-vehicle-type',
  templateUrl: './create-vehicle-type.component.html',
  styleUrls: ['./create-vehicle-type.component.css']
})
export class CreateVehicleTypeComponent implements OnInit {
  public vehicleTypeForm !: FormGroup;
  public message!: string;
  public errorOccur: boolean = false;

  constructor(private fb : FormBuilder, 
    private vehicleTypeService : VehicleTypeService) { }

  ngOnInit(): void {
    this.vehicleTypeForm = this.fb.group({
      name :  ['', Validators.required],      
      fuelType :  ['', Validators.required],
      autonomy :  ['', Validators.required],
      costPerKilometer :  ['', Validators.required],
      consumption :  ['', Validators.required],
      averageSpeed :  ['', Validators.required],  
      description :  ['', Validators.required],    
      emission :  ['', Validators.required]
    })
  }
  addVehicleType(){
    this.vehicleTypeService.addVehicleType(this.vehicleTypeForm.value).subscribe((response : any) =>{
      console.log(response);
    },
      (error) => {
        this.message = error;
        this.errorOccur = true;
      },
      () => {
        this.message = 'Congratulations! Vehicle type successfully created';
        this.errorOccur = false;
      });
      this.resetLineForm();
  }

  resetLineForm(): void {
    setTimeout(() => {
      if (this.message != '') this.message = '';
    }, 2000);

    this.vehicleTypeForm.reset();
  }
}
