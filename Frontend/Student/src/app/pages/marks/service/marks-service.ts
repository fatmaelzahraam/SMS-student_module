import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Mark, StudentMarksDashboard } from '../../../models/marks-model';

@Injectable({
  providedIn: 'root',
})
export class MarksService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/v1/marks';

  getDashboard(studentId: number, typeName?: string): Observable<StudentMarksDashboard> {
    let params = new HttpParams();
    if (typeName) {
      params = params.set('typeName', typeName);
    }
    return this.http.get<StudentMarksDashboard>(
      `${this.baseUrl}/student/${studentId}/dashboard`,
      { params }
    );
  }

  getMarksByStudent(studentId: number): Observable<Mark[]> {
    return this.http.get<Mark[]>(`${this.baseUrl}/student/${studentId}`);
  }

  getMarksByStudentAndCourse(studentId: number, courseId: number): Observable<Mark[]> {
    return this.http.get<Mark[]>(`${this.baseUrl}/student/${studentId}/course/${courseId}`);
  }
}