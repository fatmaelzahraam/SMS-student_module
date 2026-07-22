import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { catchError, Observable, of, tap, throwError } from 'rxjs';
import { StudentProfileResponse } from '../../../models/student-profile-response';


@Injectable({
  providedIn: 'root',
})
export class Profileservice {
  private readonly http    = inject(HttpClient);
  private readonly baseUrl = 'http://localhost:8080/api/v1';

  profile = signal<StudentProfileResponse | null>(null);

  getProfile(): Observable<StudentProfileResponse> {
    // ✅ cache hit — signal already populated, skip the HTTP call
    if (this.profile() !== null) return of(this.profile()!);

    return this.http.get<StudentProfileResponse>(`${this.baseUrl}/profile`).pipe(
      tap(data  => this.profile.set(data)),
      catchError(err => throwError(() => err))
    );
  }

  getInitials(): string {
    const fullName = this.profile()?.fullName?.trim();
    if (!fullName) return '??';
    const parts = fullName.split(' ');
    const first  = parts[0]?.[0]  ?? '';
    const last   = parts[parts.length - 1]?.[0] ?? '';
    return (first + last).toUpperCase();
  }
}