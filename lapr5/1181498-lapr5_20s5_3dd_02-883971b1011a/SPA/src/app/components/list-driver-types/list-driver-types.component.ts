import { Component, OnInit } from '@angular/core';
import { DriverTypeModel } from '../../models/driver-type.model';
import { DriverTypeService } from '../../services/driver-type.service';

@Component({
  selector: 'app-list-driver-types',
  templateUrl: './list-driver-types.component.html',
  styleUrls: ['./list-driver-types.component.css']
})
export class ListDriverTypesComponent implements OnInit {

  driverTypes: DriverTypeModel[] = [];

  constructor(private driverTypeService : DriverTypeService) { }

  ngOnInit(): void {
    this.getDriverTypes();
  }

  getDriverTypes() : void{
    this.driverTypeService.getDriverTypes()
      .subscribe(driverTypes => this.driverTypes = driverTypes);
  }

}
