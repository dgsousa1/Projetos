import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { FlashMessagesService } from 'angular2-flash-messages';
import { LineModel } from 'src/app/models/line.model';
import { ListaParalelosModel } from 'src/app/models/lista-paralelos-model';
import { PathModel } from 'src/app/models/path.model';
import { TripGeneratedModel } from 'src/app/models/trip-generated-model';
import { TripLineModel } from 'src/app/models/trip-line-model';
import { TripPathsModel } from 'src/app/models/trip-paths-model';
import { LineService } from 'src/app/services/line.service';
import { PathService } from 'src/app/services/path.service';
import { TripService } from 'src/app/services/trip.service';

@Component({
  selector: 'app-generate-trips',
  templateUrl: './generate-trips.component.html',
  styleUrls: ['./generate-trips.component.css']
})
export class GenerateTripsComponent implements OnInit {
  public generateTripsForm !: FormGroup;

  public pickedLine !: string;
  public lines !: LineModel[];

  public tripPaths: TripPathsModel[] = [];
  public singleLine !: TripLineModel;



  public pickedPath !: string;
  public numViagens !: number;
  public tempoViagem: number = 0;
  public frequencia !: string;
  public haParalelos: boolean = false;
  public horaInicio !: string;

  public NParalelos: number = 0;
  public paralelosList: ListaParalelosModel[] = [];
  public pathGo !: PathModel;
  public pathReturn !: PathModel;

  public keyVolta !: string;
  public keyIda !: string;

  public tripGenerated!: TripGeneratedModel;

  public pathsGoAndReturn: PathModel[] = [];



  public paths: PathModel[] = [];
  public exactPath!: PathModel;


  public pathGos: TripPathsModel[] = [];
  public pathReturns: TripPathsModel[] = [];

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private lineService: LineService,
    private pathService: PathService,
    private tripService: TripService,
    private flashMessageService: FlashMessagesService
  ) { }

  ngOnInit(): void {
    this.getLines();

    this.generateTripsForm = this.fb.group({
      nomeLinha: ['', Validators.required],
      horaInicio: ['', Validators.required],
      frequencia: ['', Validators.required],
      numeroViagens: ['', Validators.required],
      path: ['', Validators.required],
      paralelos: [, Validators.required]
    });

    this.generateTripsForm.get('paralelos')?.setValue(0);


  }

  getLines(): void {
    this.lineService.getLines('name')
      .subscribe(data => {
        this.lines = data;
        console.log('Linhas carregadas...');

      })
  }

  //paths de uma determinada linha
  getPathsFromLine(): void {
    if (this.pickedLine != undefined) {
      this.tripPaths = [];

      this.lineService.getPathsFromExactLineName(this.pickedLine)
        .subscribe(data => {
          //apenas uma linha como o conteudo
          this.singleLine = data;
          let nodeString = [];

          for (let index = 0; index < this.singleLine.goPaths.length; index++) {
            nodeString = [];
            for (let y = 0; y < this.singleLine.goPaths[index].pathNodes.length; y++) {
              if (y == this.singleLine.goPaths[index].pathNodes.length - 1) {
                nodeString.push(this.singleLine.goPaths[index].pathNodes[y].inicialNode);
                nodeString.push(this.singleLine.goPaths[index].pathNodes[y].finalNode);
              } else {
                nodeString.push(this.singleLine.goPaths[index].pathNodes[y].inicialNode);
              }
            }
            var eachPath = {
              key: this.singleLine.goPaths[index].key.toString(),
              orientation: 'Ida',
              nodes: nodeString.join().replace(/,/g, " > ")
            }
            this.tripPaths.push(eachPath);
          }

          for (let index = 0; index < this.singleLine.returnPaths.length; index++) {
            nodeString = [];
            for (let y = 0; y < this.singleLine.returnPaths[index].pathNodes.length; y++) {
              if (y == this.singleLine.returnPaths[index].pathNodes.length - 1) {
                nodeString.push(this.singleLine.returnPaths[index].pathNodes[y].inicialNode);
                nodeString.push(this.singleLine.returnPaths[index].pathNodes[y].finalNode);
              } else {
                nodeString.push(this.singleLine.returnPaths[index].pathNodes[y].inicialNode);
              }
            }
            var eachPath = {
              key: this.singleLine.returnPaths[index].key.toString(),
              orientation: 'Volta',
              nodes: nodeString.join().replace(/,/g, " > ")
            }
            this.tripPaths.push(eachPath);
          }
          console.log('Caminhos da viagem carregados...');

        });
    }
  }

  //sempre que muda de percurso
  calculateTravelTimeAgainstFrequency(): void {
    this.tempoViagem = 0;
    //dado uma linha, hora de inicio, frequencia e numero de viagens
    //saber se a frequenciaSegundos >= tempoV * 2(ida e volta e se sim popup paralelos)
    //else paralelos -1
    if (this.pickedPath != undefined && this.numViagens != undefined &&
      this.frequencia != undefined && this.horaInicio != undefined) {

      this.tempoViagem = 0;
      let NViagens = this.numViagens;
      let ida = this.pickedPath;
      //converter para ir buscar o caminho de volta
      let b = ida.replace(/ > /g, ",");
      let c = b.split(',');
      let d = c.reverse();
      let e = d.toString();
      let volta = e.replace(/,/g, " > ");

      //ir buscar a key pelo caminho de nodes do ida e volta
      for (let z = 0; z < this.tripPaths.length; z++) {
        if (this.tripPaths[z].nodes == ida)
          this.keyIda = this.tripPaths[z].key;
        else if (this.tripPaths[z].nodes == volta)
          this.keyVolta = this.tripPaths[z].key;
      }

      console.log('key: ' + this.keyIda + ' Caminho(Ida): ' + ida);
      console.log('key: ' + this.keyVolta + ' Caminho(Volta): ' + volta);

      let frequenciaSegundos = (parseInt(this.frequencia.slice(0, 2)) * 3600) + (parseInt(this.frequencia.slice(3, 5)) * 60);

      //agora ja temos a key do path vamos buscar o path JSON object
      if (this.keyIda != undefined) {
        this.pathService.getPathsWithNodeNames()
          .subscribe(data => {
            this.tempoViagem = 0;
            this.pathsGoAndReturn = [];
            for (let i = 0; i < data.length; i++) {
              if (data[i].key == this.keyIda)
                this.pathsGoAndReturn.push(data[i]);
              else if (data[i].key == this.keyVolta)
                this.pathsGoAndReturn.push(data[i]);
            }

            for (let j = 0; j < this.pathsGoAndReturn[0].pathNodes.length; j++) {
              this.tempoViagem += this.pathsGoAndReturn[0].pathNodes[j].duracao;
            }

            console.log('Frequencia: ' + frequenciaSegundos);
            console.log('Tempo da viagem(ida/volta): ' + this.tempoViagem * 2);

            if (frequenciaSegundos >= this.tempoViagem * 2) {
              this.haParalelos = false;
              console.log('Não vai inserir paralelos');
              this.generateTripsForm.get('paralelos')?.setValue(-1);
            }
            else {
              this.haParalelos = true;
              console.log('Inserir paralelos');
              this.generateTripsForm.get('paralelos')?.setValue(this.NParalelos);
            }
          });
      }
    }
  }

  onSubmit() {
    console.log('NParalelos:' + this.generateTripsForm.get('paralelos')?.value)

    const x = {
      NViagens: this.generateTripsForm.get('numeroViagens')?.value,
      frequencia: (parseInt(this.frequencia.slice(0, 2)) * 3600) + (parseInt(this.frequencia.slice(3, 5)) * 60),
      NParalelos: this.generateTripsForm.get('paralelos')?.value,
      horaSaida: (parseInt(this.horaInicio.slice(0, 2)) * 3600) + (parseInt(this.horaInicio.slice(3, 5)) * 60),
      nomeLinha: this.pickedLine,
      path: this.pathsGoAndReturn
    }

    this.tripGenerated = x;

    console.log('Submit::' + JSON.stringify(this.tripGenerated));

    this.tripService.addGeneratedTrips(this.tripGenerated)
      .subscribe(data => {
        this.flashMessageService.show('Viagens geradas com sucesso!', { cssClass: 'alert-success', timeout: 3000 });
        this.router.navigate(['/dashboard']);
      },
        (error) => {
          this.flashMessageService.show('Ocorreu um erro! Tente novamente', { cssClass: 'alert-danger', timeout: 3000 });
          this.generateTripsForm.reset();
          this.router.navigate(['/generate-trips']);
        })


  }
}