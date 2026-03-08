import { Component, inject, input, output,  } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from "@angular/router";
import { AuthService } from '../../auth/services/auth.service';

@Component({
  selector: 'app-side-bar',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './sideBar.html',

})
export class SideBar {

  authService = inject(AuthService);


  isCollapsed = input<boolean>(false);
  toggle = output<void>();

  toggleSidebar() {
    this.toggle.emit();
  }
}
