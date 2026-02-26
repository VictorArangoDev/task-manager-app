import { Component } from '@angular/core';
import { SideBar } from "../sideBar/sideBar";
import { RouterOutlet } from "@angular/router";

@Component({
  selector: 'app-layout',
  imports: [SideBar, RouterOutlet],
  templateUrl: './layout.html',
})
export class Layout { }
