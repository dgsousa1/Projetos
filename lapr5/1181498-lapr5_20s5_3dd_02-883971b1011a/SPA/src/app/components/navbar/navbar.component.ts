import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { FlashMessagesService } from 'angular2-flash-messages';
import { JwtHelperService } from '@auth0/angular-jwt';
import { AuthGuard } from 'src/app/guard/auth.guard';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  label !: any;
  userTemp !: any;
  us !: any;

  constructor(private router: Router,
    private authService: AuthService,
    private flashMessageService: FlashMessagesService,
    public authGuard: AuthGuard
  ) { }

  ngOnInit(): void {
    this.label = 'Sua Área'
  }

  //para o mapa pois tanto cliente como admin têm acesso
  baseLoggedIn(): boolean {
    if (localStorage.getItem('id token') != null)
      return true;
    else
      return false;
  }

  //apenas para routes de admin, aquando cliente estao hide
  adminLoggedIn(): boolean {
    if (localStorage.getItem('id token') != null) { //if logged in
      if (localStorage.getItem('role') == 'Adminstrador') { //and role in admin
        return true;
      } else {
        return false;
      }
    } else {//if admin not logged in
      return false;
    }
  }

  //apenas para routes de admin, aquando cliente estao hide
  userLoggedIn(): boolean {
    if (localStorage.getItem('id token') != null) { //if logged in
      if (localStorage.getItem('role') == 'Cliente' && localStorage.getItem('role') != 'Adminstrador') { //and role in admin
        return true;
      } else {
        return false;
      }
    } else {//if admin not logged in
      return false;
    }
  }



  onLogoutClick() {
    this.authService.logout();
    this.flashMessageService.show('Logout com sucesso', { cssClass: 'alert-success', timeout: 3000 });
    this.router.navigate(['/login']);
    return false;
  }

}
