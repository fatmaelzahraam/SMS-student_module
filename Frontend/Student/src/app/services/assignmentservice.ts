import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class AssignmentService {

  private baseUrl = 'http://localhost:8080/api/v1/auth/assignments';

  constructor(private http: HttpClient) {}

getAssignments() {
  console.log("Calling:", this.baseUrl);
  return this.http.get<any[]>(this.baseUrl);
}
}

