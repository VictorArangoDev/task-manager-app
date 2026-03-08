import { Component, inject } from '@angular/core';
import { RouterLink } from "@angular/router";
import { AuthService } from '../../auth/services/auth.service';

@Component({
  selector: 'app-header-principal',
  imports: [RouterLink],
  templateUrl: './header-principal.html',
})
export class HeaderPrincipal {

  authService = inject(AuthService);

 }
