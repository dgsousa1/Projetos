import { Component, OnInit } from '@angular/core';
import { VehicleDutyModel } from 'src/app/models/vehicle-duty-model';
import { ServiceDutyService } from 'src/app/services/service-duty.service';

@Component({
  selector: 'app-listar-servico-viatura',
  templateUrl: './listar-servico-viatura.component.html',
  styleUrls: ['./listar-servico-viatura.component.css']
})
export class ListarServicoViaturaComponent implements OnInit {
  
  vehicleDuties: VehicleDutyModel[] = [];

  constructor(
    private servicoViatura: ServiceDutyService
  ) { }

  ngOnInit(): void {
    this.getVehicleDuties();
  }

  getVehicleDuties(): void {
    this.servicoViatura.getServiceDuty()
      .subscribe(data => {
        this.vehicleDuties = data;
      })
  }
}
