import { Component, OnInit } from '@angular/core';
import { VehicleTypeService } from '../../services/vehicle-type.service';
import { VehicleTypeModel } from '../../models/vehicle-types.model';

@Component({
  selector: 'app-list-vehicle-types',
  templateUrl: './list-vehicle-types.component.html',
  styleUrls: ['./list-vehicle-types.component.css']
})
export class ListVehicleTypesComponent implements OnInit {

  vehicleTypes : VehicleTypeModel[] = [];

  constructor(private vehicleTypeService : VehicleTypeService) { }

  ngOnInit(): void {
    this.getVehicleTypes();
  }

  getVehicleTypes() : void{
    this.vehicleTypeService.getVehicleTypes()
      .subscribe(vehicleTypes => this.vehicleTypes = vehicleTypes);
  }
  
}