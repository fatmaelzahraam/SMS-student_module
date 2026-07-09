import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { forkJoin, catchError, of } from 'rxjs';
import {
  ClassScheduleResponse,
  ClassSessionResponse,
  DaySchedule,
  ExamScheduleResponse,
  ExamEntry,
  ScheduleSlot,
  SessionResponse,
  TIME_SLOTS,
  BREAK_SLOTS,
} from '../../../models/session-inerface';

@Injectable({ providedIn: 'root' })
export class ScheduleService {
  private readonly http    = inject(HttpClient);
  private readonly baseUrl = 'http://localhost:8080/api/v1/schedule';

  // ── Signals ───────────────────────────────────────────────
  classSchedule = signal<ClassScheduleResponse | null>(null);
  monthExams    = signal<ExamScheduleResponse   | null>(null);
  finalExams    = signal<ExamScheduleResponse   | null>(null);
  isLoading     = signal(false);
  error         = signal<string | null>(null);

  // ── Load all three in parallel — no race condition ────────
  loadAll(studentId: number): void {
    this.isLoading.set(true);
    this.error.set(null);

    forkJoin({
      classSessions: this.http
        .get<SessionResponse[]>(`${this.baseUrl}/student/${studentId}`)
        .pipe(catchError(() => of([] as SessionResponse[]))),

      monthSessions: this.http
        .get<SessionResponse[]>(`${this.baseUrl}/student/${studentId}/exams/month`)
        .pipe(catchError(() => of([] as SessionResponse[]))),

      finalSessions: this.http
        .get<SessionResponse[]>(`${this.baseUrl}/student/${studentId}/exams/final`)
        .pipe(catchError(err => {
          this.error.set(err.error?.message ?? 'Failed to load schedule');
          return of([] as SessionResponse[]);
        })),
    }).subscribe(({ classSessions, monthSessions, finalSessions }) => {
      this.classSchedule.set(this.buildWeeklySchedule(studentId, classSessions));
      this.monthExams.set(this.buildExamSchedule(studentId, monthSessions, 'MONTH_EXAM'));
      this.finalExams.set(this.buildExamSchedule(studentId, finalSessions, 'FINAL_EXAM'));
      this.isLoading.set(false);
    });
  }

  // ── Build the weekly grid from flat session list ──────────
  private buildWeeklySchedule(
    studentId: number,
    sessions: SessionResponse[]
  ): ClassScheduleResponse {
    const DAYS = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday'];
    const breakSet = new Set(BREAK_SLOTS);

    const weeklySchedule: DaySchedule[] = DAYS.map((day, dayIndex) => {
      const daySessions = sessions.filter(s => Number(s.dayOfWeek) === dayIndex + 1);

      const slots: ScheduleSlot[] = TIME_SLOTS.map(timeSlot => {
        if (breakSet.has(timeSlot)) {
          return { timeSlot, session: null };
        }

        const match = daySessions.find(s => {
          const start = s.startAt?.substring(0, 5); // "08:00"
          return timeSlot.startsWith(start?.replace(':', ':').substring(0, 5) ?? '___');
        });

        const session: ClassSessionResponse | null = match
          ? { id: match.id, subject: match.courseName, subjectCode: '', teacher: match.teacherName }
          : null;

        return { timeSlot, session };
      });

      return { day, slots };
    });

    return { studentId, weeklySchedule };
  }

  // ── Build exam list from flat session list ────────────────
  private buildExamSchedule(
    studentId: number,
    sessions: SessionResponse[],
    type: 'MONTH_EXAM' | 'FINAL_EXAM'
  ): ExamScheduleResponse {
    const exams: ExamEntry[] = sessions.map(s => ({
      id:        s.id,
      subject:   s.courseName,
      teacher:   s.teacherName,
      startTime: s.startAt ?? '',
      endTime:   s.endAt   ?? '',
      type,
    }));
    return { studentId, exams };
  }

  clearError(): void {
    this.error.set(null);
  }
}