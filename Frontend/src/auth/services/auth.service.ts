import { computed, inject, Injectable, signal } from '@angular/core';
import { User } from '../interfaces/user.interface';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { AuthResponse } from '../interfaces/response-interface';

import { catchError, map, Observable, of, tap } from 'rxjs';
import { RegisterRequest } from '../interfaces/register-request.interface';
import { rxResource } from '@angular/core/rxjs-interop';
import { Router } from '@angular/router';

type AuthStatus = 'checking' | 'authenticated' | 'not-authenticated';
const baseUrl = environment.baseUrl;

@Injectable({ providedIn: 'root' })
export class AuthService {

  private readonly _authStatus = signal<AuthStatus>('checking')
  private readonly _user = signal<User | null>(null);
  private readonly _token = signal<string | null>(localStorage.getItem('token'));


  private readonly http = inject(HttpClient);
  router = inject(Router);


  checkStatusResource = rxResource({
    stream: () => this.checkStatus(),
  });


  authStatus = computed<AuthStatus>(() => {
    if (this._authStatus() === 'checking') return 'checking';

    if (this._user()) {
      return 'authenticated';
    }
    return 'not-authenticated';
  })

  user = computed(() => this._user());
  token = computed(() => this._token());



  login(email: string, password: string): Observable<boolean> {
    return this.http.post<AuthResponse>(`${baseUrl}/auth/login`, {
      email: email,
      password: password
    }).pipe(
      map(resp => this.handleAuthSuccess(resp)),
      catchError((error: any) => this.handleAuthError(error))
    )
  }

  register(data: RegisterRequest) {
    return this.http.post(`${baseUrl}/users`, data).pipe(
      tap(() => {

      })
    )
  }


  checkStatus(): Observable<boolean> {
    const token = localStorage.getItem('token');


    if (!token) {
      this.logout();

      return of(false);
    }

    return this.http.get<AuthResponse>(`${baseUrl}/auth/check-status`, {
      // headers:{
      //   Authorization: `Bearer ${token}`
      // },
    }).pipe(
      map(resp => this.handleAuthSuccess(resp)),
      catchError((error: any) => this.handleAuthError(error))
    )

  }

  isAdmin(): Observable<boolean> {
    const role = this._user()?.role;

    if (role === 'ADMIN') {
      return of(true);
    } else {
      return of(false);
    }

  }


  logout() {
    this._user.set(null);
    this._token.set(null);
    this._authStatus.set('not-authenticated');
    localStorage.removeItem('token');
  }

  private handleAuthSuccess({ data }: AuthResponse) {
    this._user.set(data.user);
    this._authStatus.set('authenticated');
    this._token.set(data.token);

    localStorage.setItem('token', data.token);
    return true;
  }

  private handleAuthError(Error: any) {
    this.logout();
    return of(false);
  }


    checkUsername(username: string): Observable<boolean> {
  return this.http.get<any>(`${baseUrl}/auth/check-username?username=${username}`).pipe(
    map(resp => resp.data as boolean), // true = disponible, false = ocupado
    catchError(() => of(false))
  );
}






}
