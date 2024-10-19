import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { VehicleTypeModel } from '../models/vehicle-types.model';

@Injectable({
  providedIn: 'root'
})
export class VehicleTypeService {
  private vehicleTypeUrl = environment.apiUrl + '/api/vehicleTypes';  // URL to web api

  constructor(private http: HttpClient) { }
  /*Get all vehicleTypes from web api*/
  getVehicleTypes(): Observable<VehicleTypeModel[]> {
    return this.http.get<VehicleTypeModel[]>(this.vehicleTypeUrl + "/all").pipe(
      catchError(this.handleError)
    );
  }
  //////// Save methods //////////

  /* POST: add a new node to the server */
  addVehicleType(data: any) {
    return this.http.post(this.vehicleTypeUrl, data);
  }

  private handleError(err: HttpErrorResponse) {
    if (err.error instanceof ErrorEvent) {
      console.error('An error occurred: ', err.error.message);
    }
    else {
      console.error(
        `Web Api returned code ${err.status}, ` + ` Response body was: ${err.error}`
      );
    }
    return Observable.throw(err);
  }
}
