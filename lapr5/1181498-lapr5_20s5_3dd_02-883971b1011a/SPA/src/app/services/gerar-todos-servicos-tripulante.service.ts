import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GerarTodosServicosTripulanteService {

  private url = 'http://localhost:5000/allgenetic';

  constructor(private http: HttpClient) { }

  getData(): Observable<any> {
    return this.http.get<any>(this.url + "?num=" + 10);
  }

}
