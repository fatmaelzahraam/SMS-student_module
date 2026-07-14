import { HttpClient } from '@angular/common/http';
import { Injectable, inject, signal, computed } from '@angular/core';
import { catchError, forkJoin, of, tap, throwError } from 'rxjs';
import { AssignmentResponse, DashboardResponse, MarkResponse } from '../../../models/dashboard-interface';

@Injectable({
  providedIn: 'root',
})
export class Dashboardservice {
  private readonly http    = inject(HttpClient);
  private readonly baseUrl = 'http://localhost:8080/api/v1';

  // ── Signals ───────────────────────────────────────────────────────────────
  dashboard   = signal<DashboardResponse | null>(null);
  assignments = signal<AssignmentResponse[]>([]);
  marks       = signal<MarkResponse[]>([]);
  isLoading   = signal(false);
  error       = signal<string | null>(null);

  // ── Computed ──────────────────────────────────────────────────────────────
  upcomingAssignments = computed(() =>
    this.assignments()
      .filter(a => new Date(a.deadline) >= new Date())
      .sort((a, b) => new Date(a.deadline).getTime() - new Date(b.deadline).getTime())
      .slice(0, 5)
  );

  recentMarks = computed(() =>
    this.marks()
      .sort((a, b) => new Date(b.feedbackDate).getTime() - new Date(a.feedbackDate).getTime())
      .slice(0, 8)
  );

  // ── Load all dashboard data ───────────────────────────────────────────────
  loadAll(studentId: number): void {
    this.isLoading.set(true);
    this.error.set(null);

    forkJoin({
      dashboard: this.getDashboard().pipe(
        catchError(err => {
          console.error('Dashboard fetch failed:', err.status, err.message);
          return of(null);
        })
      ),
      assignments: this.http.get<AssignmentResponse[]>(`${this.baseUrl}/assignments`).pipe(
        catchError(err => {
          console.error('Assignments fetch failed:', err.status, err.message);
          return of([] as AssignmentResponse[]);
        })
      ),
      marks: this.http.get<MarkResponse[]>(`${this.baseUrl}/marks/student/${studentId}`).pipe(
        catchError(err => {
          console.error('Marks fetch failed:', err.status, err.message);
          return of([] as MarkResponse[]);
        })
      ),
    }).pipe(
      tap(({ dashboard, assignments, marks }) => {
        if (dashboard)   this.dashboard.set(dashboard);
        if (assignments) this.assignments.set(assignments);
        if (marks)       this.marks.set(marks);
        this.isLoading.set(false);
      }),
      catchError(err => {
        this.error.set('Failed to load dashboard');
        this.isLoading.set(false);
        return throwError(() => err);
      })
    ).subscribe();
  }

  getDashboard() {
    return this.http.get<DashboardResponse>(`${this.baseUrl}/dashboard/`);
  }

  clearError(): void {
    this.error.set(null);
  }
}