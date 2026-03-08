import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from "@angular/router";
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  imports: [RouterLink, ReactiveFormsModule],
  templateUrl: './login.html',
})
export class LoginPage {

  fb = inject(FormBuilder)
  hasError = signal(false)
  isPosting = signal(false)

  authService = inject(AuthService);
  router = inject(Router);

  loginForm = this.fb.group({
    email: ['', [Validators.required, Validators.minLength(6)]],
    password: ['', [Validators.required, Validators.minLength(6)]],
  });


  onSubmit() {
    if (this.loginForm.invalid) {
      console.log(this.loginForm)
      this.hasError.set(true);
      setTimeout(() => {
        this.hasError.set(false);
      }, 2000);
      return;
    }

    const {email ='', password ='' } = this.loginForm.value;

    this.authService.login(email!, password!).subscribe((isAuthenticated) =>{

      if(isAuthenticated){
        this.router.navigateByUrl("/tasks");
        return;
      }
      this.hasError.set(true);
       setTimeout(() => {
        this.hasError.set(false);
      }, 2000);
    })

  }

}
