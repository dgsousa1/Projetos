import { Injectable } from '@angular/core';
import { CanActivate } from '@angular/router';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private router: Router) { }

  canActivate(): boolean {

    if (localStorage.getItem('id token') == null) {
      this.router.navigate(['/login']);
      return false;
    } else {
      return true;
    }
  }
}
