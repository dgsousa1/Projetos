import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FastestPathService {
  private fastestPathUrl = 'http://localhost:5000/fastpath';
  
  constructor(private http: HttpClient) { }

  getData(inode : string, fnode : string, time : string): Observable<any> {
    return this.http.get<any>(this.fastestPathUrl + "?orig=" + inode + 
    "&dest=" + fnode + "&hora=" + time);
  }
}
