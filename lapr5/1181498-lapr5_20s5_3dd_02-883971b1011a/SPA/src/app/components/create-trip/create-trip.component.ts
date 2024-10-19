import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LineModel } from 'src/app/models/line.model';
import { NodeModel } from 'src/app/models/node.model';
import { PathModel } from 'src/app/models/path.model';
import { TripAdhocModel } from 'src/app/models/trip-adhoc-model';
import { TripLineModel } from 'src/app/models/trip-line-model';
import { TripPathsModel } from 'src/app/models/trip-paths-model';
import { LineService } from 'src/app/services/line.service';
import { NodeService } from 'src/app/services/node.service';
import { PathService } from 'src/app/services/path.service';
import { TripService } from 'src/app/services/trip.service';
import { FlashMessagesService } from 'angular2-flash-messages';


@Component({
  selector: 'app-create-trip',
  templateUrl: './create-trip.component.html',
  styleUrls: ['./create-trip.component.css']
})
export class CreateTripComponent implements OnInit {

  public tripForm !: FormGroup;
  public lines !: LineModel[];
  public pickedLine !: string;
  public pickedPath !: string;

  public nodes !: NodeModel[];
  public exactPath !: PathModel;

  public singleLine !: TripLineModel;
  public tripPaths: TripPathsModel[] = [];
  public tripAdhoc !: TripAdhocModel;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private lineService: LineService,
    private pathService: PathService,
    private nodeService: NodeService,
    private tripService: TripService,
    private flashMessageService: FlashMessagesService
  ) { }

  ngOnInit(): void {
    this.getLines();
    this.getNodes();
    if (this.pickedLine != undefined)
      this.getPathsFromLine();

    this.tripForm = this.fb.group({
      tripNumber: ['', Validators.required],
      exitTime: ['', Validators.required],
      orientation: ['', Validators.required],
      line: ['', Validators.required],
      path: ['', Validators.required],
    });

    this.tripForm.patchValue({ orientation: 'Go' });
  }

  // vai buscar percursos de uma determinada linha
  // transforma o json desses percursos em:
  // key: string,
  // orientation : string,
  // nodes : string (exemplo: Paredes > Cete > Parada de Todeia > Recarei > Aguiar de Sousa)
  // esta transformação é necessario para a user experience
  // no fim tripPaths vai ser um array the paths no formato acima descrito

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

            console.log('Caminhos da viagem carregados');
          }
        });
    }
  }

  onSubmit() {
    //converter o exitTime no formato dateTime correto
    //orientacao e consuante o path que ele escolher X
    //path : ir buscar a key a partir nodeString e com essa key
    // in buscar o objeto inteiro e aí sim mandar como post

    let orientacao = this.pickedPath.substr(0, this.pickedPath.indexOf(':'));
    orientacao == 'Ida' ? orientacao = 'Go' : orientacao = 'Return';
  
    let exitDateTime = this.tripForm.get('exitTime')?.value;
    let a = exitDateTime.replace(/T/g, " ");
    let formatedDateTime = a.replace(/-/g, "/").concat(':00');

    let exactedPath = this.pickedPath.substring(this.pickedPath.indexOf(':') + 2);
    let foundedKeyPath;

    for (let i = 0; i < this.tripPaths.length; i++) {
      if (exactedPath == this.tripPaths[i].nodes) {
        foundedKeyPath = this.tripPaths[i].key;
      }
    }
    //exact path preenchido e pronto para ser usado para construir o json
    if (foundedKeyPath != undefined) {
  
      this.pathService.getPathByKeyWithNodesName(foundedKeyPath)
        .subscribe(data => {
          this.exactPath = data;
          var tripAdhocJSON = {
            numeroViagem: this.tripForm.get('tripNumber')?.value,
            horaSaida: formatedDateTime,
            orientation: orientacao,
            nomeLinha: this.tripForm.get('line')?.value,
            path: this.exactPath
          }
          this.tripAdhoc = tripAdhocJSON;

          console.log('Submited Trip: ' + JSON.stringify(this.tripAdhoc));
          //subscribe
          this.tripService.addTripAdhoc(this.tripAdhoc)
            .subscribe((response: any) => {
              this.flashMessageService.show('Viagem criada com sucesso!', { cssClass: 'alert-success', timeout: 3000 });
              this.router.navigate(['/dashboard']);
            },
              (error) => {
                this.flashMessageService.show('Ocorreu um erro! Tente novamente : ' + error, { cssClass: 'alert-danger', timeout: 3000 });
                this.tripForm.reset();
                this.router.navigate(['/create-trip']);
              })
        })
    }
  }

  getLines(): void {
    this.lineService.getLines('name')
      .subscribe(data => {
        this.lines = data;
        console.log(data);
      })
  }

  getNodes(): void {
    this.nodeService.getNodes('name')
      .subscribe(nodes => {
        this.nodes = nodes;
      })
  }
}
