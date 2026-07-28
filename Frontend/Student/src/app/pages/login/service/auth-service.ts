import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { AuthRequest } from '../../../models/auth-request';
import { AuthResponse } from '../../../models/auth-response';
import { Profileservice } from '../../profile/service/profileservice';


@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private http = inject(HttpClient);
  private profileService = inject(Profileservice);

  private readonly url = 'http://localhost:8080/api/v1/auth/login';

  authenticate(request: AuthRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(this.url, request).pipe(
      tap(res => {
        this.profileService.clearStorage();
        this.profileService.profile.set(null);
        // save token & role first so the JWT interceptor is ready
        this.saveToken(res.token);
        this.saveRole(res.role);
        localStorage.setItem('studentId', String(res.studentId));

        // pre-fetch profile immediately after login
        this.profileService.getProfile().subscribe();
      })
    );
  }

  getCurrentUserId(): number {
    const id = localStorage.getItem('studentId');
    if (!id || id === 'null') {
      throw new Error('No studentId in session — user not logged in');
    }
    return Number(id);
  }

  saveToken(token: string): void {
    localStorage.setItem('token', token);
  }

  saveRole(role: string): void {
    localStorage.setItem('role', role);
  }

  get token(): string | null {
    return localStorage.getItem('token');
  }

  changePassword(data: any): Observable<any> {
    return this.http.patch(`${this.url}/change-password`, data);
  }
}