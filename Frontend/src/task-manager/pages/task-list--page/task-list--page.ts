import { Component, inject } from '@angular/core';
import { rxResource } from '@angular/core/rxjs-interop';
import { TasksService } from '../services/tasks.service';
import { PriorityPipe } from '../../../app/priority-pipe';
import { StatusClassesPipe } from '../../../app/status-classes-pipe';

@Component({
  selector: 'app-task-list--page',
  imports: [PriorityPipe, StatusClassesPipe],
  templateUrl: './task-list--page.html',
})
export class TaskListPage {


  tasksService = inject(TasksService)

    tasksResource = rxResource({
    params: () => ({}),
    stream: () => {
      return this.tasksService.getTasks();
    },
  });

 }
