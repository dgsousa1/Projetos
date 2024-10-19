import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GerarServicoTripulanteService {

  private gerarServicoTripulanteURL = 'http://localhost:5000/genetic';  // URL to web api

  constructor(private http: HttpClient) { }

  getData(
    gerac: number, popul: number,
    viat: number, cruz: number,
    mut: number, tempo: number,
    aval: number, esta: number): Observable<any> {
    return this.http.get<any>(this.gerarServicoTripulanteURL + "?gerac=" + gerac +
      "&popul=" + popul + "&viat=" + viat + "&cruz=" + cruz + "&mut=" + mut + "&tempo=" + tempo + "&aval=" + aval + "&esta=" + esta);
  }
}
