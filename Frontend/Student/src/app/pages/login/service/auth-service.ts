import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthRequest } from '../../../models/auth-request';
import { AuthResponse } from '../../../models/auth-response';


@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private http = inject(HttpClient);
  private readonly url = "http://localhost:8080/api/v1/auth/login";

  authenticate(request : AuthRequest): Observable<AuthResponse>{
    return this.http.post<AuthResponse>(this.url, request);
  }
  getCurrentUserId(): number {
  const id = localStorage.getItem('studentId');
  if (!id || id === 'null') {
    throw new Error('No studentId in session — user not logged in');
  }
  return Number(id);
}

  saveToken(token:string){
    localStorage.setItem("token", token)
  }
  saveRole(role:string){
    localStorage.setItem("role" , role)
  }

  get token(){
    return localStorage.getItem("token")
  }

  changePassword(data: any): Observable<any> {
    return this.http.patch(`${this.url}/change-password`, data);
  }

}
