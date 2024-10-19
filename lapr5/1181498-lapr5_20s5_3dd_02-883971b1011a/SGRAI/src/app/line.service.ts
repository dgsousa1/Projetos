import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { LineModel } from './line.model';
import { LinePathModel } from './line.path.model';
import { PathModel } from './path.model';
import { PathCoordsModel } from './pathcoords.model';

@Injectable({
  providedIn: 'root'
})
export class LineService {
  private lineUrl = 'http://localhost:8080/api/lines';

  constructor(private http: HttpClient) { }

  /*Get all lines with an order from web api*/
  getLines(order: any): Observable<LineModel[]> {
    return this.http.get<LineModel[]>(this.lineUrl + "/all" + "/?order=" + order).pipe(
      catchError(this.handleError)
    );
  }

  getPathsFromLine(id: any): Observable<LinePathModel[]> {
    return this.http.get<LinePathModel[]>(this.lineUrl + "/pathfromline" + "/?id=" + id).pipe(
      catchError(this.handleError)
    )
  }

  getCoordsFromLine(id: any): Observable<PathCoordsModel> {
    return this.http.get<PathCoordsModel>(this.lineUrl + "/coordinates" + "/?id=" + id).pipe(
      catchError(this.handleError)
    )
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