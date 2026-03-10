import {
  Component,
  ChangeDetectionStrategy,
  signal,
  inject
} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { AuthService } from '../../auth/services/auth.service';
import { SideBar } from "../sideBar/sideBar";
import { Header } from "../header/header";

@Component({
  selector: 'app-layout',
  imports: [RouterOutlet, SideBar, Header],
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './layout.html'
})
export class Layout {

  authService = inject(AuthService);

    isCollapsed = signal(false);

  toggleSidebar() {
    this.isCollapsed.update(v => !v);
  }
}


