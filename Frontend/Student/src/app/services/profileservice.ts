import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Studentresponse } from '../models/studentresponse';

@Injectable({
  providedIn: 'root',
})
export class Profileservice {
   private apiUrl = 'http://localhost:8080/api/v1/auth/students';

  constructor(private http: HttpClient) {}

  getMyProfile(): Observable<Studentresponse> {
    return this.http.get<Studentresponse>(`${this.apiUrl}/profile`);
  }
}
