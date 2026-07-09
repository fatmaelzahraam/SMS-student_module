import { inject, Injectable, Signal } from '@angular/core';
import { HttpClient, HttpParams, httpResource } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { Mark, StudentMarksDashboard } from '../../../models/marks-model';

@Injectable({
  providedIn: 'root',
})
export class MarksService {
   private http = inject(HttpClient);
 
private baseUrl = 'http://localhost:8080/api/v1/marks';

getDashboard(studentId: number): Observable<StudentMarksDashboard> {
  return this.http.get<StudentMarksDashboard>(`${this.baseUrl}/student/${studentId}/dashboard`);
}

getMarksByStudent(studentId: number): Observable<Mark[]> {
  return this.http.get<Mark[]>(`${this.baseUrl}/student/${studentId}`);
}

getMarksByStudentAndCourse(studentId: number, courseId: number): Observable<Mark[]> {
  return this.http.get<Mark[]>(`${this.baseUrl}/student/${studentId}/course/${courseId}`);
}
  
}
