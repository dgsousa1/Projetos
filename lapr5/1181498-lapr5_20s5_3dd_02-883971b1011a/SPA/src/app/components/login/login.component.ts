import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { UserModel } from 'src/app/models/user-model';
import { FlashMessagesService } from 'angular2-flash-messages';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public loginForm !: FormGroup;
  public users !: UserModel[];

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthService,
    private flashMessageService: FlashMessagesService
  ) { }

  ngOnInit(): void {
    this.getUsers();
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
    })
  }

  getUsers(): void {
    this.authService.getUsers()
      .subscribe(users => {
        this.users = users;
        console.log(users);
      });
  }

  onSubmit(): void {
    const user = {
      username: this.loginForm.get('username')?.value,
      password: this.loginForm.get('password')?.value
    }

    this.authService.loginUser(user)
      .subscribe((response: any) => {
        localStorage.setItem('role', response.user.role);        
        this.authService.storeUserData(response.token, response.user);

        this.flashMessageService.show('Login efetuado com sucesso!', { cssClass: 'alert-success', timeout: 3000 });
        this.router.navigate(['/dashboard']);
      },
        (error) => {
          this.flashMessageService.show('Ocorreu um erro! Nome de utilizador ou palavra-passe errado', { cssClass: 'alert-danger', timeout: 3000 });
          this.loginForm.reset();
          this.router.navigate(['/login']);
        }
      );
  }
}
