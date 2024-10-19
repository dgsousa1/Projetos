import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { PathModel } from '../models/path.model';

@Injectable({
  providedIn: 'root'
})
export class PathService {
  private pathUrl = environment.apiUrl + '/api/paths';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }
  /*Get all paths with an order from web api*/
  getPaths(): Observable<PathModel[]> {
    return this.http.get<PathModel[]>(this.pathUrl + "/all");
  }
  getPathsWithNodeNames(): Observable<PathModel[]> {
    return this.http.get<PathModel[]>(this.pathUrl + "/allwithnodenames");
  }
  

  getPathByKey(key: string): Observable<PathModel> {
    return this.http.get<PathModel>(this.pathUrl + "/bykey/?key=" + key);
  }

  getPathByKeyWithNodesName(key: string): Observable<PathModel> {
    return this.http.get<PathModel>(this.pathUrl + "/bykeywithnodenames/?key=" + key);
  }

  //////// Save methods //////////

  /* POST: add a new node to the server */
  addPath(data: any) {
    return this.http.post(this.pathUrl, data, this.httpOptions)
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
