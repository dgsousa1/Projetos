import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import { DriverModel } from '../models/driver-model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DriverService {

  private driverUrl = environment.apiMDV +'/api/Drivers';  // URL to web api

  constructor(private http: HttpClient) { }

  addDriver(data: any) {
    return this.http.post(this.driverUrl, data)
      .pipe(catchError(this.handleError));
  }

  getDrivers(): Observable<DriverModel[]> {
    return this.http.get<DriverModel[]>(this.driverUrl)
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