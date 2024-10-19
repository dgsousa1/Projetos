import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { WorkblockModel } from '../models/workblock-model';
import { environment } from 'src/environments/environment';
import { VehicleDutyModel } from '../models/vehicle-duty-model';
@Injectable({
  providedIn: 'root'
})
export class ServiceDutyService {
  private serviceDutyrl = environment.apiMDV + '/api/VehicleDuties';  // URL to web api

  constructor(private http: HttpClient) { }

  addServiceDuty(data: any) {
    return this.http.post(this.serviceDutyrl, data)
      .pipe(catchError(this.handleError));
  }

  getServiceDuty() : Observable<VehicleDutyModel[]> {
    return this.http.get<VehicleDutyModel[]>(this.serviceDutyrl)
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
