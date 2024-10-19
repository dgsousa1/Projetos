import { Component, OnInit, TemplateRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { FlashMessagesService } from 'angular2-flash-messages';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  public registerForm !: FormGroup;
  modalRef!: BsModalRef;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthService,
    private modalService: BsModalService,
    private flashMessageService: FlashMessagesService
  ) { }

  ngOnInit(): void {
    this.registerForm = this.fb.group({
      username: ['', Validators.required],
      name: ['', Validators.required],
      password: ['', Validators.required],
      email: ['', Validators.required],
      nif: ['', Validators.required],
      telephone: ['', Validators.required],
      role: 'Cliente',
      termos: ['', Validators.required],
    })
  }

  openModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template);
  }
  //ola
  onSubmit(): void {
    const user = {
      username: this.registerForm.get('username')?.value,
      role: 'Cliente',
      name: this.registerForm.get('name')?.value,
      password: this.registerForm.get('password')?.value,
      email: this.registerForm.get('email')?.value,
      nif: this.registerForm.get('nif')?.value,
      telephone: this.registerForm.get('telephone')?.value,
    }

    console.log('submiting..  ' + JSON.stringify(user));

    this.authService.addUser(user)
      .subscribe((response: any) => {

        this.flashMessageService.show('Utilizador criado com sucesso! \nSe pretender apagar os dados, envie um email: protecao.dados@opt.pt.', { cssClass: 'alert-success', timeout: 3000 });
        this.router.navigate(['/login']);
      },
        (error) => {
          this.flashMessageService.show('Ocorreu um erro! Tente novamente' + error, { cssClass: 'alert-danger', timeout: 20000 });
          this.registerForm.reset();
          this.router.navigate(['/register']);
        }
      );


  }
}


