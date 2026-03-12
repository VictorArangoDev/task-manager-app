
import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { AuthService } from '../../../auth/services/auth.service';
import { map, Observable, tap } from 'rxjs';
import { ProjectResponse } from '../projects-page/interfaces/project.interface';
import { RoleResponse } from '../roles-page/interfaces/role.interface';
import { UserInterface } from '../users-page/user.interface';
import { ApiResponse } from '../../../auth/interfaces/ApiResponse';

@Injectable({ providedIn: 'root' })
export class UserService {

  authservice = inject(AuthService);
  baseUrl = environment.baseUrl;

  private readonly http = inject(HttpClient);


    getUsers(): Observable<UserInterface[]> {
      return this.http.get<ApiResponse<UserInterface[]>>(`${this.baseUrl}/users`)
        .pipe(
          map(resp => resp.data),
          tap(users => console.log(users))
        );
    }


}
