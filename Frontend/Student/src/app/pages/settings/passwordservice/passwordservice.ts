import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface ChangePasswordPayload {
  currentPassword: string;
  newPassword:     string;
  confirmPassword: string;
}

@Injectable({ providedIn: 'root' })
export class PasswordService {

  private readonly baseUrl = 'http://localhost:8080/api/v1/password/';

  private http = inject(HttpClient);


  changePassword(payload: ChangePasswordPayload): Observable<{ message: string }> {
    return this.http.patch<{ message: string }>(`${this.baseUrl}change`, payload);
  }
}