import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { catchError } from 'rxjs/operators';
import { DriverDutyModel } from '../models/driver-duty.model';

@Injectable({
  providedIn: 'root'
})
export class ServicoTripulanteService {
  private driverDutyrl = environment.apiMDV + '/api/DriverDuties';  // URL to web api

  constructor(private http: HttpClient) { }

  addDriverDuty(data: any) {
    return this.http.post(this.driverDutyrl, data)
      .pipe(catchError(this.handleError));
  }

  getDriverDuties() : Observable<DriverDutyModel[]> {
    return this.http.get<DriverDutyModel[]>(this.driverDutyrl)
      .pipe(catchError(this.handleError));
  }

  handleError(error: HttpErrorResponse): Observable<never> {
    if (error.error instanceof ErrorEvent) {
      console.error('An error occurred:', error.error.message);
    } else {
      console.error(`Backend returned code ${error.error.message}, ` + `couldn't get response from server.`);
    }
    return throwError(`Error(${error.status}): couldn't get response from server.`);
  }

}
