import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { catchError, Observable, of, tap, throwError } from 'rxjs';
import { StudentProfileResponse } from '../../../models/student-profile-response';

const PROFILE_KEY       = 'sms_student_profile';
const PROFILE_IMAGE_KEY = 'sms_student_profile_image';

@Injectable({
  providedIn: 'root',
})
export class Profileservice {
  private readonly http    = inject(HttpClient);
  private readonly baseUrl = 'http://localhost:8080/api/v1';

  // ── Hydrate signals from localStorage on service construction ─────────────
  profile      = signal<StudentProfileResponse | null>(this.loadFromStorage());
  profileImage = signal<string | null>(this.loadImageFromStorage());

  // ── Private helpers ────────────────────────────────────────────────────────
  private loadFromStorage(): StudentProfileResponse | null {
    try {
      const raw = localStorage.getItem(PROFILE_KEY);
      return raw ? (JSON.parse(raw) as StudentProfileResponse) : null;
    } catch {
      return null;
    }
  }

  private loadImageFromStorage(): string | null {
    return localStorage.getItem(PROFILE_IMAGE_KEY);
  }

  private saveToStorage(data: StudentProfileResponse): void {
    try {
      localStorage.setItem(PROFILE_KEY, JSON.stringify(data));
    } catch {
      // storage full or private browsing — fail silently
    }
  }

  // ── Image API (frontend-only, never sent to backend) ──────────────────────
  saveProfileImage(base64: string): void {
    try {
      localStorage.setItem(PROFILE_IMAGE_KEY, base64);
      this.profileImage.set(base64);
    } catch {
      // storage full — fail silently
    }
  }

  clearProfileImage(): void {
    localStorage.removeItem(PROFILE_IMAGE_KEY);
    this.profileImage.set(null);
  }

  // ── Clear everything (call on logout AND on login) ─────────────────────────
  clearStorage(): void {
    localStorage.removeItem(PROFILE_KEY);
    localStorage.removeItem(PROFILE_IMAGE_KEY);
    this.profile.set(null);
    this.profileImage.set(null);
  }

  // ── Public API ─────────────────────────────────────────────────────────────
  getProfile(): Observable<StudentProfileResponse> {
    if (this.profile() !== null) return of(this.profile()!);

    return this.http.get<StudentProfileResponse>(`${this.baseUrl}/profile`).pipe(
      tap(data => {
        this.profile.set(data);
        this.saveToStorage(data);
      }),
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