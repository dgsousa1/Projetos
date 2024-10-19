import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {

  constructor(private router: Router) { }

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    if (localStorage.getItem('id token') != null) { //if token exists
      //verifies if role is ADMIN
      if (next.data[0] == localStorage.getItem('role')) return true;
      else{
        this.router.navigate(['/dashboard']);
        return false;
      } 
    } else {
      this.router.navigate(['/login']);
      return false;
    }
  }
}