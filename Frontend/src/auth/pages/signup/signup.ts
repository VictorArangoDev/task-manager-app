import { Component, inject, signal, DestroyRef } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators, AbstractControl, ValidationErrors } from '@angular/forms';
// FormBuilder → construye el formulario
// ReactiveFormsModule → permite usar [formGroup] en el HTML
// Validators → reglas de validación (required, email, minLength)
import { Router, RouterLink } from '@angular/router';
// Router → para navegar entre páginas por código
// RouterLink → para los links en el HTML
import { AuthService } from '../../services/auth.service';

import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-signup',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './signup.html',
})
export class SignupPage {

  fb = inject(FormBuilder);// construye el formulario
  router = inject(Router);// navega entre páginas
  authService = inject(AuthService);// llama al backend
  hasError = signal(false); // controla si muestra el mensaje de error
  isPosting = signal(false); // controla si el botón está deshabilitado
  destroyRef = inject(DestroyRef);

  // Estados del username
  usernameAvailable = signal<boolean | null>(null); // null=sin verificar, true=disponible, false=ocupado
  checkingUsername = signal(false); // muestra spinner mientras consulta

  registerForm = this.fb.group({
    name:            ['', [Validators.required]],// obligatorio
    document:        ['', [Validators.required]],// obligatorio
    email:           ['', [Validators.required, Validators.email]], // obligatorio + formato email
    username:        ['', [Validators.required]], // obligatorio
    password:        ['', [Validators.required, Validators.minLength(6)]], // mínimo 6 caracteres
    confirmPassword: ['', [Validators.required]], // obligatorio
  }, { validators: this.passwordsMatch });

   constructor() {
    // Escucha cambios en el campo username
    this.registerForm.get('username')!.valueChanges.pipe(
      debounceTime(500),            // espera 500ms después del último teclazo
      distinctUntilChanged(),        // solo si cambió el valor
      takeUntilDestroyed(this.destroyRef) // limpia la suscripción al destruir Evita memory leaks.
    ).subscribe(username => {
      if (!username || username.length < 3) {
        this.usernameAvailable.set(null); // resetea si está vacío
        return;
      }
      this.checkingUsername.set(true);
      this.authService.checkUsername(username).subscribe(available => {
        this.usernameAvailable.set(available);
        this.checkingUsername.set(false);
      });
    });
  }


  // Valida que password y confirmPassword coincidan
  passwordsMatch(control: AbstractControl): ValidationErrors | null {
    const password = control.get('password')?.value;
    const confirm  = control.get('confirmPassword')?.value;
    return password === confirm ? null : { passwordsMismatch: true };
  }

  get password() {
    return this.registerForm.get('password')?.value || '';
  }

  get hasUppercase()  { return /[A-Z]/.test(this.password); }
  get hasLowercase()  { return /[a-z]/.test(this.password); }
  get hasNumber()     { return /[0-9]/.test(this.password); }
  get hasMinLength()  { return this.password.length >= 6; }
  get passwordsCoincide() {
    return this.registerForm.get('password')?.value ===
           this.registerForm.get('confirmPassword')?.value;
  }


  onSubmit() {
    // 1. Si el formulario tiene errores, muestra el mensaje y para
    if (this.registerForm.invalid) {
      this.hasError.set(true);
      setTimeout(() => this.hasError.set(false), 2000); // oculta después de 2 segundos
      return;
    }

    // 2. Extrae los valores del formulario
    const { name, document, email, username, password } = this.registerForm.value;


// 3. Activa el estado "enviando"
    this.isPosting.set(true);

    // 4. Llama al AuthService que hace el POST al backend
    this.authService.register({
      name:     name!,
      document: document!,
      email:    email!,
      username: username!,
      password: password!,
      role:     { id: 3 }
    }).subscribe({
      next: () => this.router.navigateByUrl('/auth/login'), // éxito → va al login
      error: () => {
        this.hasError.set(true);//  error → muestra mensaje
        this.isPosting.set(false); // reactiva el botón
        setTimeout(() => this.hasError.set(false), 2000);
      }
    });
  }
}
