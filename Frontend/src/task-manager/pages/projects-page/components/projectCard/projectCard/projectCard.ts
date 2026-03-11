import { Component, input } from '@angular/core';
import { ProjectResponse } from '../../../interfaces/project.interface';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'project-card',
  imports: [DatePipe],
  templateUrl: './projectCard.html',
})
export class ProjectCard {

  project = input.required<ProjectResponse>();
 }
