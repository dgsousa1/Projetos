import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { VehicleDutyModel } from 'src/app/models/vehicle-duty-model';
import { GerarServicoTripulanteService } from 'src/app/services/gerar-servico-tripulante.service';
import { ServiceDutyService } from 'src/app/services/service-duty.service';

@Component({
  selector: 'app-algoritmo-algav',
  templateUrl: './algoritmo-algav.component.html',
  styleUrls: ['./algoritmo-algav.component.css']
})
export class AlgoritmoAlgavComponent implements OnInit {
  public algavForm !: FormGroup;
  public vehicleDuties: VehicleDutyModel[] = [];
  public str !: any;
  public loaded: boolean = false;

  public algavJson!: { drivers: string[], aval: number };

  constructor(private fb: FormBuilder,
    private servicoViatura: ServiceDutyService,
    private gerarServicoTripulanteService: GerarServicoTripulanteService) { }

  ngOnInit(): void {
    this.getServicosViatura();

    this.algavForm = this.fb.group({
      geracoes: [''],
      dimensaoPop: ['', Validators.required],
      idServicoViatura: ['', Validators.required],
      cruzamento: ['', Validators.required],
      mutacao: ['', Validators.required],
      tempo: [''],
      avaliacao: [''],
      estabilizacao: ['']
    });
  }

  getServicosViatura() {
    this.servicoViatura.getServiceDuty()
      .subscribe(data => {
        data.forEach(element => {
          const x = {
            code: element.code,
            name: element.name,
            workBlocks: element.workBlocks,
            duration: element.duration,
            validDate: element.validDate,
            color: element.color
          }
          this.vehicleDuties.push(x);
        });
      })
  }

  onSubmit() {

    if (this.algavForm.get('geracoes')?.value == "" && this.algavForm.get('tempo')?.value == "") {
      this.algavForm.get('geracoes')?.setValue(1000000);
      this.algavForm.get('tempo')?.setValue(300);
    }

    if (this.algavForm.get('geracoes')?.value != "" && this.algavForm.get('tempo')?.value == "") {
      this.algavForm.get('tempo')?.setValue(1000000);
    }

    if (this.algavForm.get('geracoes')?.value == "" && this.algavForm.get('tempo')?.value != "") {
      this.algavForm.get('geracoes')?.setValue(10000000);
    }

    if (this.algavForm.get('avaliacao')?.value == "")
      this.algavForm.get('avaliacao')?.setValue(-1);

    if (this.algavForm.get('estabilizacao')?.value == "")
      this.algavForm.get('estabilizacao')?.setValue(1000);


    //parse do code
    const form = {
      geracoes: this.algavForm.get('geracoes')?.value,
      dimensaoPop: this.algavForm.get('dimensaoPop')?.value,
      idServicoViatura: this.algavForm.get('idServicoViatura')?.value,
      cruzamento: this.algavForm.get('cruzamento')?.value / 100,
      mutacao: this.algavForm.get('mutacao')?.value / 100,
      tempo: this.algavForm.get('tempo')?.value,
      avaliacao: this.algavForm.get('avaliacao')?.value,
      estabilizacao: this.algavForm.get('estabilizacao')?.value
    }

    console.log(JSON.stringify(form));

    this.gerarServicoTripulanteService
      .getData(
        this.algavForm.get('geracoes')?.value,
        this.algavForm.get('dimensaoPop')?.value,
        12,
        this.algavForm.get('cruzamento')?.value / 100,
        this.algavForm.get('mutacao')?.value / 100,
        this.algavForm.get('tempo')?.value,
        this.algavForm.get('avaliacao')?.value,
        this.algavForm.get('estabilizacao')?.value
      ).subscribe(data => {
        //this.str = JSON.stringify(data);//string que recebemos de algav
        let a = data.array;
        let b = a.substring(1, a.length - 1);

        const x = {
          drivers: b,
          aval: data.aval
        }

        this.algavJson = x;

        console.log(JSON.stringify(this.algavJson));
        this.loaded = true;
      })
  }

}
