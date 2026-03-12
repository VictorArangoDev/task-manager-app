import { Component, input } from '@angular/core';
import { ProjectResponse } from '../../../interfaces/project.interface';
import { DatePipe } from '@angular/common';
import { StatusClassesPipe } from '../../../../../../app/status-classes-pipe';

@Component({
  selector: 'project-card',
  imports: [DatePipe, StatusClassesPipe ],
  templateUrl: './projectCard.html',
})
export class ProjectCard {

  project = input.required<ProjectResponse>();
 }
