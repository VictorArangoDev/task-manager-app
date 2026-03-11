import { Component, inject } from '@angular/core';
import { ModalService } from './modal.service';
import { CreateProject } from "./components/create-project/create-project";

@Component({
  selector: 'app-modal',
  imports: [CreateProject],
  templateUrl: './Modal.html',
})
export class Modal {

   modalService = inject(ModalService);



 }
