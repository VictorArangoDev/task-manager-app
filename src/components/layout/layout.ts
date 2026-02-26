import {
  Component,
  ChangeDetectionStrategy,
  signal
} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { SideBar } from '../sideBar/sideBar';

@Component({
  selector: 'app-layout',
  imports: [SideBar, RouterOutlet],
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './layout.html'
})
export class Layout {

 isCollapsed = signal(false);

  toggleSidebar() {
    this.isCollapsed.update(v => !v);
  }
}


