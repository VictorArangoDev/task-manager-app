import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderPrincipal } from "../../../components/header-principal/header-principal";

@Component({
  selector: 'app-auth-layout',
  imports: [RouterOutlet, HeaderPrincipal],
  templateUrl: './auth-layout.html',
})
export class AuthLayout { }
