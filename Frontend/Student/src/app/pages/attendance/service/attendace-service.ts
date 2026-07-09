import { Injectable, inject } from '@angular/core';
import {
  HttpClient,
  HttpHeaders,
  HttpParams
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AttendanceOverview } from '../../../models/AttendanceOverview';
import { AttendanceDailyResponse } from '../../../models/AttendanceDailyResponse';
import { AttendanceMonthlyResponse } from '../../../models/AttendaceMonthlyResponse';


@Injectable({
  providedIn: 'root'
})
export class AttendanceService {

  private http = inject(HttpClient);

  private readonly api =
    'http://localhost:8080/api/v1/attendance';

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');

    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  }

  // ================= Overview =================

  getOverview(
    studentId: number
  ): Observable<AttendanceOverview> {

    const headers = this.getHeaders();

    return this.http.get<AttendanceOverview>(
      `${this.api}/overview`,
      {
        headers,
        params: {
          studentId: studentId
        }
      }
    );
  }

  // ================= Daily =================

  getDailyAttendance(
    studentId: number,
    date: string
  ): Observable<AttendanceDailyResponse> {

    const headers = this.getHeaders();

    const params = new HttpParams()
      .set('studentId', studentId)
      .set('date', date);

    return this.http.get<AttendanceDailyResponse>(
      `${this.api}/daily`,
      {
        headers,
        params
      }
    );
  }

  getMonthlyAttendance(
  studentId: number,
  month: number,
  year: number
): Observable<AttendanceMonthlyResponse> {

  const headers = this.getHeaders();

  const params = new HttpParams()
    .set('studentId', studentId)
    .set('month', month)
    .set('year', year);

  return this.http.get<AttendanceMonthlyResponse>(
    `${this.api}/monthly`,
    {
      headers,
      params
    }
  );
}

}
