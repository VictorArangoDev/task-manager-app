import { Component, inject } from '@angular/core';
import { ProjectsService } from '../services/projects.service';
import { rxResource } from '@angular/core/rxjs-interop';
import { ProjectCard } from "./components/projectCard/projectCard/projectCard";

@Component({
  selector: 'projects-page',
  imports: [ProjectCard],
  templateUrl: './projects-page.html',
})
export class ProjectsPage {

  projectsService = inject(ProjectsService);


  projectResource = rxResource({
    params: () => ({}),
    stream :({ params }) => {
      return this.projectsService.getProjects() ?? [];
    },
  })

 }
