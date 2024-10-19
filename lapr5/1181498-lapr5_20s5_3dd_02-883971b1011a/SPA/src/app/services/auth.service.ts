import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { UserModel } from '../models/user-model';
import { environment } from 'src/environments/environment';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private userUrl = environment.apiMDV + '/api/Users';  // URL to web api

  authToken: any;
  user: any;

  constructor(private http: HttpClient, public jwtHelper: JwtHelperService) { }

  addUser(data: any) {
    return this.http.post(this.userUrl, data)
      .pipe(catchError(this.handleError));
  }

  deleteUser(username: string) {
    return this.http.delete(this.userUrl + '/' + username + '/delete')
      .pipe(catchError(this.handleError));
  }

  getUsers(): Observable<UserModel[]> {
    return this.http.get<UserModel[]>(this.userUrl)
      .pipe(catchError(this.handleError));
  }

  getUserById(user_id: string): Observable<UserModel> {
    return this.http.get<UserModel>(this.userUrl + '/' + user_id)
      .pipe(catchError(this.handleError));
  }

  loginUser(data: any) {
    return this.http.post(this.userUrl + '/login', data)
      .pipe(catchError(this.handleError));
  }

  storeUserData(token: any, user: any) {
    localStorage.setItem('id token', token);
    localStorage.setItem('user', JSON.stringify(user));
    this.authToken = token;
    this.user = user;
  }

  loadToken(): string {
    if (localStorage.getItem('id token') != null) {
      const token = localStorage.getItem('id token');
      this.authToken = token;
    }

    return this.authToken;
  }

  loadUser(): UserModel {
    if (localStorage.getItem('user') != null) {
      const user = localStorage.getItem('user');
      this.user = user;
    }

    return this.user;
  }


  isAuthenticated() {
    if (this.authToken == null) {
      return false;
    } else {
      return true;
    }
  }

  logout() {
    this.authToken = null;
    this.user = null;
    localStorage.clear();
  }

  handleError(error: HttpErrorResponse): Observable<never> {
    if (error.error instanceof ErrorEvent) {
      console.error('An error occurred:', error.error.message);
    } else {
      console.error(`Backend returned code ${error.error.message}, ` + `couldn't get response from server.`);
    }
    return throwError(`Error(${error.error.message}): couldn't get response from server.`);
  }
}
