import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';

import { Observable, of, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

import { NodeModel } from './node.model';

@Injectable({
  providedIn: 'root'
})
export class NodeService {
  private nodesUrl = 'http://localhost:8080/api/nodes';  // URL to web api
  result!: NodeModel;

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient) { }

  /*Get all nodes with an order from web api*/
  getNodes(order: any): Observable<NodeModel[]> {
    return this.http.get<NodeModel[]>(this.nodesUrl + "/all" + "/?order=" + order).pipe(
    );
  }


  getNodesById(id: string): Observable<NodeModel> {
    return this.http.get<NodeModel>(this.nodesUrl + "/byid/?" + "id=" + id);
  }
}