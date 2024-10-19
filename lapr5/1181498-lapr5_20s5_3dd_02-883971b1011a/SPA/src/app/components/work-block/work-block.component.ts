import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { FlashMessagesService } from 'angular2-flash-messages';
import { TripModel } from 'src/app/models/trip-model';
import { LineService } from 'src/app/services/line.service';
import { TripService } from 'src/app/services/trip.service';
import { WorkBlockService } from 'src/app/services/work-block.service';

@Component({
  selector: 'app-work-block',
  templateUrl: './work-block.component.html',
  styleUrls: ['./work-block.component.css']
})
export class WorkBlockComponent implements OnInit {

  public workBlockForm !: FormGroup;
  public trips: TripModel[] = [];

  constructor(private fb: FormBuilder,
    private router: Router,
    private tripService: TripService,
    private lineService: LineService,
    private workblockService: WorkBlockService,
    private flashMessageService: FlashMessagesService) { }

  ngOnInit(): void {
    this.getTrips();
    this.workBlockForm = this.fb.group({
      wbKey: ['', Validators.required],
      startTime: ['', Validators.required],
      endTime: ['', Validators.required],
      viagens: ['', Validators.required]
    });
  }

  getTrips(): void {
    this.tripService.getTrips()
      .subscribe(data => {
        this.trips = data;
      })
  }

  onSubmit() {

    let startTime = this.workBlockForm.get('startTime')?.value;
    let a = startTime.replace(/T/g, " ");
    let formatedstartTime = a.replace(/-/g, "/").concat(':00');

    let endTime = this.workBlockForm.get('endTime')?.value;
    let b = endTime.replace(/T/g, " ");
    let formatedendTime = b.replace(/-/g, "/").concat(':00');

    let viagens: string;
    console.log('b:  ' + this.workBlockForm.get('viagens')?.value);

    viagens = this.workBlockForm.get('viagens')?.value;
    let tripsNumber: number[] = [];

    for (let i = 0; i < viagens.length; i++) {
      console.log('viagens: ' + viagens[i].toString());
      let eachTripString = viagens[i];
      let eachTripStringNumberString = eachTripString.split(',')[0];
      tripsNumber.push(parseInt(eachTripStringNumberString.charAt(eachTripStringNumberString.length - 1)))
    }

    for (let x = 0; x < tripsNumber.length; x++) {
      console.log('t:' + tripsNumber[x]);
    }

    const workblock = {
      key: this.workBlockForm.get('wbKey')?.value,
      startInstant: formatedstartTime,
      endInstant: formatedendTime,
      trips: tripsNumber
    }

    console.log('wb: ' + JSON.stringify(workblock))

    this.workblockService.addWorkBlock(workblock)
      .subscribe((response: any) => {
        console.log('response:' + response);
        this.flashMessageService.show('Bloco de trabalho criado com sucesso!', { cssClass: 'alert-success', timeout: 3000 });
        this.router.navigate(['/dashboard']);
      },
        (error) => {
          console.log('error:' + error);

          this.flashMessageService.show('Ocorreu um erro! Tente novamente, Erro:' + error, { cssClass: 'alert-danger', timeout: 3000 });
          this.workBlockForm.reset();
          this.router.navigate(['/work-block']);
        });
  }
}
