import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import { TripModel } from '../models/trip-model';
import { environment } from 'src/environments/environment';
@Injectable({
  providedIn: 'root'
})
export class TripService {

  private tripTypeUrl = environment.apiMDV + '/api/Trips';  // URL to web api

  constructor(private http: HttpClient) { }

  addTrip(data: any) {
    return this.http.post(this.tripTypeUrl, data)
      .pipe(catchError(this.handleError));
  }

  addTripAdhoc(data: any) {
    return this.http.post(this.tripTypeUrl + '/Adhoc', data)
      .pipe(catchError(this.handleError));
  }
  addGeneratedTrips(data: any) {
    return this.http.post(this.tripTypeUrl + '/Gen', data)
      .pipe(catchError(this.handleError));
  }

  getTrips(): Observable<TripModel[]> {
    return this.http.get<TripModel[]>(this.tripTypeUrl)
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
