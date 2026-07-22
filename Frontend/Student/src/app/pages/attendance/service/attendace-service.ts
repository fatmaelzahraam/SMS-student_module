import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AttendanceOverview } from '../../../models/AttendanceOverview';
import { AttendanceDailyResponse } from '../../../models/AttendanceDailyResponse';
import { AttendanceMonthlyResponse } from '../../../models/AttendaceMonthlyResponse';


@Injectable({ providedIn: 'root' })
export class AttendanceService {

  private http = inject(HttpClient);

  private readonly api = 'http://localhost:8080/api/v1/attendance';

  // ── Overview ─────────────────────────────────────────────────────────────
  // Backend resolves the student from the JWT token — no studentId param.

  getOverview(): Observable<AttendanceOverview> {
    return this.http.get<AttendanceOverview>(`${this.api}/overview`);
  }

  // ── Daily ─────────────────────────────────────────────────────────────────
  // date format: 'YYYY-MM-DD'  e.g. '2026-07-06'

  getDailyAttendance(date: string): Observable<AttendanceDailyResponse> {
    const params = new HttpParams().set('date', date);
    return this.http.get<AttendanceDailyResponse>(`${this.api}/daily`, { params });
  }

  // ── Monthly ───────────────────────────────────────────────────────────────

  getMonthlyAttendance(
    month: number,
    year: number
  ): Observable<AttendanceMonthlyResponse> {
    const params = new HttpParams()
      .set('month', month)
      .set('year', year);
    return this.http.get<AttendanceMonthlyResponse>(`${this.api}/monthly`, { params });
  }
}