import { Component, OnInit } from '@angular/core';
import { GerarTodosServicosTripulanteService } from 'src/app/services/gerar-todos-servicos-tripulante.service';

@Component({
  selector: 'app-gerar-todos-servicos-tripulantes',
  templateUrl: './gerar-todos-servicos-tripulantes.component.html',
  styleUrls: ['./gerar-todos-servicos-tripulantes.component.css']
})
export class GerarTodosServicosTripulantesComponent implements OnInit {

  ar: { serviceDuty: string, drivers: string }[] = [];
  public loaded: boolean = false;
  constructor(private gerarTodosServicosTripulante: GerarTodosServicosTripulanteService) { }

  ngOnInit(): void {

  }

  gerarServicos(): void {
    this.gerarTodosServicosTripulante.getData()
      .subscribe(data => {
        console.log(data);
        let a = data.array.slice(2, -2);
        let b = a.split('),(');

        for (let i = 0; i < b.length; i++) {
          let c = {
            serviceDuty: b[i].split(',[')[0],
            drivers: b[i].split(',[')[1].slice(0, -1)
          }

          this.ar.push(c);
        }
        this.loaded = true;
      });
  }


}
