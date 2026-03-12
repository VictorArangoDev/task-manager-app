import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { map, Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { TasksResponse } from '../task-list--page/interfaces/tasks.interfaces';
import { ApiResponse } from '../../../auth/interfaces/ApiResponse';
import { Tasks } from '../task-list--page/interfaces/tasks-response';

@Injectable({ providedIn: 'root' })
export class TasksService {

  private readonly http = inject(HttpClient);
  private readonly baseUrl = environment.baseUrl;

  getTasks(): Observable<Tasks[]> {
      return this.http.get<ApiResponse<Tasks[]>>(`${this.baseUrl}/tasks`)
        .pipe(
          map(resp => resp.data),
          tap(data => console.log(data))
        );
    }

  getTasksByProject(projectId: number): Observable<TasksResponse[]> {
    return this.http.get<TasksResponse[]>(`${this.baseUrl}/tasks`, {
      params: { projectId: projectId.toString() },
    });
  }

  createTaskForProject(projectId: number, task: any): Observable<any> {
    const payload = { ...task, projectId };
    return this.http.post<any>(`${this.baseUrl}/tasks`, payload);
  }
}

