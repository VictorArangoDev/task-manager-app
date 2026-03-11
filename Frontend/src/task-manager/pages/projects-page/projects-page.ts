import { Component, inject } from '@angular/core';
import { ProjectsService } from '../services/projects.service';
import { rxResource } from '@angular/core/rxjs-interop';
import { ProjectCard } from "./components/projectCard/projectCard/projectCard";
import { AuthService } from '../../../auth/services/auth.service';
import { ModalService } from '../../../shared/Modal/modal.service';

@Component({
  selector: 'projects-page',
  imports: [ProjectCard],
  templateUrl: './projects-page.html',
})
export class ProjectsPage {

  projectsService = inject(ProjectsService);
  authservice = inject(AuthService);
  modalService = inject(ModalService)


  projectResource = rxResource({
    params: () => ({}),
    stream: () => {
      return this.projectsService.getProjects();
    },
  });

  

  openCreateProject() {
    this.modalService.open('createProject');
  }

  closeModal() {
    this.modalService.close();
  }


}
