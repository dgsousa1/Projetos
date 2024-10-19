import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import { VehicleModel } from '../models/vehicle-model';
import { environment } from 'src/environments/environment';
@Injectable({
  providedIn: 'root'
})
export class VehicleService {

  private vehicleUrl = environment.apiMDV + '/api/Vehicles';  // URL to web api

  httpOptions = {
    headers: new HttpHeaders(
      {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Credentials': 'true',
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Methods': 'GET, POST, PATCH, DELETE, PUT, OPTIONS'
      })
  };

  constructor(private http: HttpClient) { }

  addVehicle(data: any) {
    return this.http.post(this.vehicleUrl, data)
      .pipe(catchError(this.handleError));
  }

  getVehicles(): Observable<VehicleModel[]> {
    return this.http.get<VehicleModel[]>(this.vehicleUrl)
      .pipe(catchError(this.handleError));
  }

  handleError(error: HttpErrorResponse): Observable<never> {
    if (error.error instanceof ErrorEvent) {
      console.error('Ocorreu um erro:', error.error.message);
    } else {
      console.error(`Backend returned código: ${error.status}, ` + `
      não foi possível obter resposta do servidor.`);
    }
    return throwError(`Erro(${error.status}): ${error.error.message}`);
  }
}
