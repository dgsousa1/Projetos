import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';

import { Observable, of, throwError } from 'rxjs';
import { catchError, delay, tap } from 'rxjs/operators';

import { NodeModel } from '../models/node.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class NodeService {
  private nodesUrl = environment.apiUrl + '/api/nodes';  // URL to web api
  result!: NodeModel;

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient) { }

  /*Get all nodes with an order from web api*/
  getNodes(order: any): Observable<NodeModel[]> {
    return this.http.get<NodeModel[]>(this.nodesUrl + "/all" + "/?order=" + order)
      .pipe(catchError(this.handleError));
  }

  /*Get all nodes by name from web api */
  getNodesByName(nodeName: string, order: string): Observable<NodeModel[]> {
    return this.http.get<any>(this.nodesUrl + "/byname/?" + "name=" + nodeName + "&order=" + order);
  }

  /*Get exact node by id from web api */
  getNodesById(id: string): Observable<NodeModel> {
    return this.http.get<NodeModel>(this.nodesUrl + "/byid/?" + "id=" + id);
  }

  /*Get all nodes by depot from web api */
  getNodesByDepot(order: string): Observable<NodeModel[]> {
    return this.http.get<any>(this.nodesUrl + "/depots/?" + "order=" + order);
  }

  /*Get all nodes by relief point from web api */
  getNodesByReliefPoints(order: string): Observable<NodeModel[]> {
    return this.http.get<any>(this.nodesUrl + "/reliefPoints/?" + "order=" + order);
  }

  /*Get nodes with multiple filters from web api*/
  getMultipleFilters(nodeName: string, depot: boolean, reliefPoint: boolean, order: string): Observable<NodeModel[]> {
    return this.http.get<any>(this.nodesUrl + "/multipleFilters/?name=" + nodeName + "&isDepot=" + depot + "&isReliefPoint=" + reliefPoint + "&order=" + order);
  }

  //////// Save methods //////////

  /* POST: add a new node to the server */
  addNode(node: NodeModel): Observable<NodeModel> {
    return this.http.post<NodeModel>(this.nodesUrl, node, this.httpOptions)
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

