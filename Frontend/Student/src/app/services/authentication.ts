import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthRequest } from '../models/auth-request';
import { AuthResponse } from '../models/auth-response';
import { environment } from '../../environments/environment';


@Injectable({
  providedIn: 'root',
})
export class Authentication {
  
  private apiUrl = "http://localhost:8080/api/v1/auth";

  constructor(private http: HttpClient) {};

  login(request: AuthRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/login`, request);
  }

 saveSession(response: AuthResponse): void {
    localStorage.setItem('token', response.token);
    localStorage.setItem('role', response.role);
    localStorage.setItem('expiresAt', response.expiresAt.toString());
  }

   getToken(): string | null {
    return localStorage.getItem('token');
  }

  getRole(): string | null {
    return localStorage.getItem('role');
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

   logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('expiresAt');
  }

  changePassword(data: any): Observable<any> {
    return this.http.patch(`${this.apiUrl}/change-password`, data);
  }

}
