import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { LineModel } from '../models/line.model';
import { PathCoordsModel } from '../models/pathcoords.model';

@Injectable({
  providedIn: 'root'
})
export class LineService {
  private lineUrl = environment.apiUrl + '/api/lines';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  /*Get all lines with an order from web api*/
  getLines(order: any): Observable<LineModel[]> {
    return this.http.get<LineModel[]>(this.lineUrl + "/allwithoutids" + "/?order=" + order).pipe(
      catchError(this.handleError)
    );
  }
  
  getLinesID(order: any): Observable<LineModel[]> {
    return this.http.get<LineModel[]>(this.lineUrl + "/all" + "/?order=" + order).pipe(
      catchError(this.handleError)
    );
  }


  /*Get all nodes by name from web api */
  getLinesByName(nodeName: string, order: string): Observable<LineModel[]> {
    return this.http.get<LineModel[]>(this.lineUrl + "/bynamewithoutids/?" + "name=" + nodeName + "&order=" + order);
  }

  //#####################
  getPathsFromExactLineName(lineName: string): Observable<any> {
    return this.http.get<any>(this.lineUrl + "/pfromlinename/?name=" + lineName);
  }
  //#####################
  /*Get exact node by id from web api */
  getLinesById(id: string): Observable<LineModel> {
    return this.http.get<LineModel>(this.lineUrl + "/byidwithoutids/?" + "id=" + id);
  }

  getCoordsFromLine(id: any): Observable<PathCoordsModel> {
    return this.http.get<PathCoordsModel>(this.lineUrl + "/coordinates" + "/?id=" + id).pipe(
      catchError(this.handleError)
    )
  }
  //////// Save methods //////////

  /* POST: add a new line to the server */
  addLine(data: any) {
    return this.http.post(this.lineUrl, data)
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
