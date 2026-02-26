import { Component, input, output,  } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from "@angular/router";

@Component({
  selector: 'app-side-bar',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './sideBar.html',

})
export class SideBar {
  isCollapsed = input<boolean>(false);
  toggle = output<void>();

  toggleSidebar() {
    this.toggle.emit();
  }
}
