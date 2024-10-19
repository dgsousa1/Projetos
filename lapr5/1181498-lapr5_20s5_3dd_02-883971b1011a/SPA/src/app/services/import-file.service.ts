import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ImportFileService {
  private importUrl = environment.apiUrl + '/api/import';  // URL to web api

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  //Import file from web api
  importFile() {
    const body = JSON.stringify({ title: "data" });
    return this.http.post(this.importUrl, body, this.httpOptions)
      .pipe(catchError(this.handleError));
  }

  handleError(error: HttpErrorResponse): Observable<never> {
    if (error.error instanceof ErrorEvent) {
      console.error('An error occurred:', error.error.message);
    } else {
      console.error(`Backend returned code ${error.status}, ` + `couldn't get response from server.`);
    }
    let status = error.status;
    let mensagem;
    if(status = 402){
      //data ja tem o import feito
      mensagem = `Errors founded while importing data, check logger file!`
    }else if(status = 0){
      mensagem = `Server may be down. Please try again later.`
    }

    return throwError(mensagem);
  }
}
