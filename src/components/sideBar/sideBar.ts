import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from "@angular/router";

@Component({
  selector: 'app-side-bar',
  imports: [RouterLink, RouterLinkActive, RouterOutlet],
  templateUrl: './sideBar.html',

})
export class SideBar {


  isCollapsed = false;
  isMobile = false;

  constructor() {
    this.checkScreen();
  }


  checkScreen() {
    this.isMobile = window.innerWidth < 768;

    // En móvil inicia colapsado
    if (this.isMobile) {
      this.isCollapsed = true;
    }
  }

  toggleSidebar() {
    this.isCollapsed = !this.isCollapsed;
  }

}

