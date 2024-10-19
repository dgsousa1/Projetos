import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FlashMessagesService } from 'angular2-flash-messages';
import { UserModel } from 'src/app/models/user-model';
import { AuthService } from 'src/app/services/auth.service';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { TemplateRef } from '@angular/core';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  user_id !: string;
  userTemp !: any;
  us !: any;
  user !: UserModel;
  modalRef!: BsModalRef;

  constructor(
    private authService: AuthService,
    private flashMessageService: FlashMessagesService,
    private router: Router,
    private modalService: BsModalService
  ) { }

  ngOnInit(): void {
    this.us = this.authService.loadUser();
    this.userTemp = JSON.parse(this.us)
    console.log(this.userTemp);

    this.getUser();
  }

  openModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template);
  }

  getUser(): void {
    this.authService.getUserById(this.userTemp.id)
      .subscribe(data => {
        this.user = data;
        console.log('this.user:' + JSON.stringify(this.user));
      });
  }

  decline(): void {
    this.modalRef.hide();
  }

  deleteUser(): void {
    this.authService.deleteUser(this.user.username)
      .subscribe(response => {
        this.modalRef.hide();
        console.log(response);
        this.flashMessageService.show('Utilizador apagado com sucesso!',
          { cssClass: 'alert-success', timeout: 5000 });
        this.authService.logout();
        this.router.navigate(['/login']);
      })
  }
}
