import { Component, OnInit } from '@angular/core';
import { DriverDutyModel } from 'src/app/models/driver-duty.model';
import { VehicleDutyModel } from 'src/app/models/vehicle-duty-model';
import { ServiceDutyService } from 'src/app/services/service-duty.service';
import { ServicoTripulanteService } from 'src/app/services/servico-tripulante.service';

@Component({
  selector: 'app-listar-servico-tripulante',
  templateUrl: './listar-servico-tripulante.component.html',
  styleUrls: ['./listar-servico-tripulante.component.css']
})
export class ListarServicoTripulanteComponent implements OnInit {

  driverDuties: DriverDutyModel[] = [];

  constructor(
    private driverDutyService: ServicoTripulanteService
  ) { }

  ngOnInit() {
    this.getDriverDuties();
  }

  getDriverDuties(): void {
    this.driverDutyService.getDriverDuties()
      .subscribe(data => {
        this.driverDuties = data;
      })
  }

}
