import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AssignmentResponse } from '../../../models/assignment-response';

@Injectable({ providedIn: 'root' })
export class AssignmentService {

 
  private baseUrl = 'http://localhost:8080/api/v1/assignments';
 
  constructor(private http: HttpClient) {}
 
  /** GET /api/v1/assignments  →  all assignments */
  getAssignments(): Observable<AssignmentResponse[]> {
    return this.http.get<AssignmentResponse[]>(this.baseUrl);
  }
 
  /** GET /api/v1/assignments/course/{courseId} */
  getAssignmentsByCourse(courseId: number): Observable<AssignmentResponse[]> {
    return this.http.get<AssignmentResponse[]>(`${this.baseUrl}/course/${courseId}`);
  }
 
  /** GET /api/v1/assignments/{id} */
  getAssignmentById(id: number): Observable<AssignmentResponse> {
    return this.http.get<AssignmentResponse>(`${this.baseUrl}/${id}`);
  }
}

