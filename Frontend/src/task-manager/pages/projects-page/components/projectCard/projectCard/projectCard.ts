import { Component, input } from '@angular/core';
import { ProjectResponse } from '../../../interfaces/project.interface';

@Component({
  selector: 'project-card',
  imports: [],
  templateUrl: './projectCard.html',
})
export class ProjectCard {

  project = input.required<ProjectResponse>();
 }
