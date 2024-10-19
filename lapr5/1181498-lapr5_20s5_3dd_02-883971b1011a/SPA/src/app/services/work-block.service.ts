import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { WorkblockModel } from '../models/workblock-model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class WorkBlockService {
  private wbUrl = environment.apiMDV + '/api/WorkBlocks';  // URL to web api

  constructor(private http: HttpClient) { }

  addWorkBlock(data: any) {
    return this.http.post(this.wbUrl, data)
      .pipe(catchError(this.handleError));
  }

  getWorkBlocks(): Observable<WorkblockModel[]> {
    return this.http.get<WorkblockModel[]>(this.wbUrl)
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
