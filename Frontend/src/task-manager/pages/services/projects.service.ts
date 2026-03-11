
import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ProjectResponse } from '../projects-page/interfaces/project.interface';
import { AuthService } from '../../../auth/services/auth.service';

@Injectable({ providedIn: 'root' })
export class ProjectsService {

  authservice = inject(AuthService);

  baseUrl = environment.baseUrl;

  private http = inject(HttpClient);

  getProjects(): Observable<ProjectResponse[]> {
    return this.http.get<ProjectResponse[]>(`${this.baseUrl}/projects`)
      .pipe(
        tap(resp => console.log(resp))
      );
  }

  createProject(project: any) {
    return this.http.post(`${this.baseUrl}/projects`, project);
  }

}
