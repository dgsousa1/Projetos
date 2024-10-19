import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoggedInGuard implements CanActivate {

  constructor(private router: Router) { }

  canActivate(): Observable<boolean> | Promise<boolean> | boolean {
    if (localStorage.getItem('id token') != null) { //if token exists cant loggin or register
      this.router.navigate(['/dashboard']);
      return false;
    } else {
      return true;
    }
  }

}
