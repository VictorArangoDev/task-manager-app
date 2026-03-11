import { Component, inject, input, output  } from '@angular/core';
import { RouterLink, RouterLinkActive } from "@angular/router";
import { AuthService } from '../../auth/services/auth.service';
import { Layout } from '../layout/layout';

@Component({
  selector: 'app-side-bar',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './sideBar.html',

})
export class SideBar {

  authService = inject(AuthService);
  layoutService =inject(Layout)

}
