import { Component, OnInit } from '@angular/core';
import { FormGroup, FormArray, FormBuilder, FormControl, Validators } from '@angular/forms';
import { DriverTypeService } from '../../services/driver-type.service';
import { LineService } from '../../services/line.service';

@Component({
  selector: 'app-create-driver-type',
  templateUrl: './create-driver-type.component.html',
  styleUrls: ['./create-driver-type.component.css']
})
export class CreateDriverTypeComponent implements OnInit {
  public driverTypeForm !: FormGroup;
  public message!: string;
  public errorOccur: boolean = false;

  constructor(private fb : FormBuilder, 
    private driverTypeService : DriverTypeService) { }

  ngOnInit(): void {
    this.driverTypeForm = this.fb.group({
      name :  ['', Validators.required],
      description :  ['', Validators.required]
    })
  }
  addDriverType(){
    this.driverTypeService.addDriverType(this.driverTypeForm.value).subscribe((response : any) =>{
      console.log(response);
    },
      (error) => {
        this.message = error;
        this.errorOccur = true;
      },
      () => {
        this.message = 'Congratulations! Driver type successfully created';
        this.errorOccur = false;
      });
    this.resetUserForm();
  }

  resetUserForm(): void {
    setTimeout(() => {
      if (this.message != '') this.message = '';
    }, 2000);

    this.driverTypeForm.reset();
  }
}