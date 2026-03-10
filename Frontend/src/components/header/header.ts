import { Component, inject } from '@angular/core';
import { AuthService } from '../../auth/services/auth.service';
import { NameRoute } from "../name-route/name-route";

@Component({
  selector: 'app-header',
  imports: [NameRoute],
  templateUrl: './header.html',
})
export class Header {
  authService = inject(AuthService);
 }
