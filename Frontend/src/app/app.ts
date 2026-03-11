import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Modal } from "../shared/Modal/Modal";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Modal],
  templateUrl: './app.html',
})
export class App {

}
