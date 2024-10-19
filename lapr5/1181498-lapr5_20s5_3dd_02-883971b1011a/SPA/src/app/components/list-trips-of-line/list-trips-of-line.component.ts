import { Component, OnInit, TemplateRef } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { PathModel } from 'src/app/models/path.model';
import { TripForListModel } from 'src/app/models/trip-for-list-model';
import { PathService } from 'src/app/services/path.service';
import { TripService } from 'src/app/services/trip.service';

@Component({
  selector: 'app-list-trips-of-line',
  templateUrl: './list-trips-of-line.component.html',
  styleUrls: ['./list-trips-of-line.component.css']
})
export class ListTripsOfLineComponent implements OnInit {

  tripsForList: TripForListModel[] = [];
  storingTripTemp: any[] = [];

  selectedPath!: PathModel;
  x !: string;
  modalRef!: BsModalRef;
  template!: TemplateRef<any>;

  constructor(private fb: FormBuilder,
    private tripService: TripService,
    private pathService: PathService,
    private modalService: BsModalService) { }

  ngOnInit(): void {
  }

  private convertSecondsIntoFormatedDate(totalSeconds: number): string {
    let hours = Math.floor(totalSeconds / 3600);
    totalSeconds %= 3600;
    let minutes = Math.floor(totalSeconds / 60);
    return hours + ':' + ('0' + minutes).slice(-2);
  }

  /*getPathInformationFromEachPath(keyPath: number): void {
    this.pathService.getPathByKeyWithNodesName(keyPath.toString())
      .subscribe(data => {
        this.selectedPath = data;
        this.x = JSON.stringify(this.selectedPath);
        this.modalRef = this.modalService.show(this.template);
        console.log(JSON.stringify(data));
      });
  }*/

  getTripsOfLine(pickedLine: string): void {
    this.tripService.getTrips()
      .subscribe(data => {

        console.log(data);

        data.forEach(trip => {
          if (trip.line == pickedLine) {
            //construir o JSON
            var tempoViagem = 0;

            trip.passingTimes.sort((a, b) => (b.time) - (a.time));

            tempoViagem = trip.passingTimes[0].time - trip.passingTimes[trip.passingTimes.length - 1].time;

            const t = {
              tripNumber: trip.tripNumber,
              orientation: trip.orientation,
              line: trip.line,
              path: trip.path,
              initialNode: trip.passingTimes[trip.passingTimes.length - 1].nodeName,
              finalNode: trip.passingTimes[0].nodeName,
              horaPartida: this.convertSecondsIntoFormatedDate(trip.passingTimes[trip.passingTimes.length - 1].time),
              horaChegada: this.convertSecondsIntoFormatedDate(trip.passingTimes[0].time),
              tempoViagem: this.convertSecondsIntoFormatedDate(tempoViagem)
            }


            console.log(JSON.stringify(t));
            this.tripsForList.push(t);
          }
        });

        this.tripsForList.sort(function (a, b) {

          let x = parseInt(a.horaPartida.substr(0, a.horaPartida.indexOf(':'))) * 3600 + parseInt(a.horaPartida.substring(a.horaPartida.indexOf(':') + 1, a.horaPartida.length)) * 60;

          let y = parseInt(b.horaPartida.substr(0, a.horaPartida.indexOf(':'))) * 3600 + parseInt(b.horaPartida.substring(b.horaPartida.indexOf(':') + 1, b.horaPartida.length)) * 60;

          return x - y;
        });

      });
  }

  clicar(pickedLine: string) {
    console.log('entrou');
    this.getTripsOfLine(pickedLine);
    this.tripsForList = [];
  }
}
