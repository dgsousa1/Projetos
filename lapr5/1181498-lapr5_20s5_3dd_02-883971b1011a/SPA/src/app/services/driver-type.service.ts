import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { DriverTypeModel } from '../models/driver-type.model';

@Injectable({
  providedIn: 'root'
})
export class DriverTypeService {
  private driverTypeUrl = environment.apiUrl + '/api/driverTypes';  // URL to web api

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  /*Get all driverTypes from web api*/
  getDriverTypes(): Observable<DriverTypeModel[]> {
    return this.http.get<DriverTypeModel[]>(this.driverTypeUrl + "/all")
      .pipe(catchError(this.handleError));
  }

  /*Get all nodes by name from web api */
  getDriverTypeByName(driverTypeName: string, order: string): Observable<DriverTypeModel[]> {
    return this.http.get<any>(this.driverTypeUrl + "/byname/?" + "name=" + driverTypeName + "&order=" + order);
  }

  //////// Save methods //////////

  /* POST: add a new node to the server */
  addDriverType(data: any) {
    return this.http.post(this.driverTypeUrl, data)
      .pipe(catchError(this.handleError));
  }

  handleError(error: HttpErrorResponse): Observable<never> {
    if (error.error instanceof ErrorEvent) {
      console.error('An error occurred:', error.error.message);
    } else {
      console.error(`Backend returned code ${error.status}, ` + `couldn't get response from server.`);
    }
    return throwError(`Error(${error.status}): couldn't get response from server.`);
  }
}
