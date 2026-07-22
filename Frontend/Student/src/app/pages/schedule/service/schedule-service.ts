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

/**
 * Converts a 12-hour slot label start time to zero-padded 24-hour "HH:MM"
 * so it can be compared directly against the backend's "HH:MM:SS" substring.
 *
 * School day runs 8 AM – 3:30 PM, so any hour < 8 is unambiguously PM:
 *   "8:00"  → "08:00"
 *   "9:40"  → "09:40"
 *   "11:00" → "11:00"
 *   "12:40" → "12:40"
 *   "1:50"  → "13:50"   ← was the bug: 13:50 ≠ "1:50"
 *   "2:40"  → "14:40"   ← same bug
 */
function to24Hour(time12: string): string {
  const [hStr, mStr] = time12.split(':');
  const h = parseInt(hStr, 10);
  const hour24 = h < 8 ? h + 12 : h;
  return `${hour24.toString().padStart(2, '0')}:${mStr}`;
}

@Injectable({ providedIn: 'root' })
export class ScheduleService {
  private readonly http    = inject(HttpClient);
  private readonly baseUrl = 'http://localhost:8080/api/v1/schedule';

  classSchedule = signal<ClassScheduleResponse | null>(null);
  monthExams    = signal<ExamScheduleResponse   | null>(null);
  finalExams    = signal<ExamScheduleResponse   | null>(null);
  isLoading     = signal(false);
  error         = signal<string | null>(null);

  loadAll(studentId: number): void {
    this.isLoading.set(true);
    this.error.set(null);

    forkJoin({
      classSessions: this.http
        .get<SessionResponse[]>(`${this.baseUrl}/student/${studentId}`)
        .pipe(catchError(err => {
          console.error('Class schedule failed:', err.status, err.message);
          this.error.set(`Failed to load class schedule (${err.status})`);
          return of([] as SessionResponse[]);
        })),

      monthSessions: this.http
        .get<SessionResponse[]>(`${this.baseUrl}/student/${studentId}/exams/month`)
        .pipe(catchError(err => {
          console.error('Month exams failed:', err.status, err.message);
          return of([] as SessionResponse[]);
        })),

      finalSessions: this.http
        .get<SessionResponse[]>(`${this.baseUrl}/student/${studentId}/exams/final`)
        .pipe(catchError(err => {
          console.error('Final exams failed:', err.status, err.message);
          return of([] as SessionResponse[]);
        })),

    }).subscribe(({ classSessions, monthSessions, finalSessions }) => {
      this.classSchedule.set(this.buildWeeklySchedule(studentId, classSessions));
      this.monthExams.set(this.buildExamSchedule(studentId, monthSessions, 'MONTH_EXAM'));
      this.finalExams.set(this.buildExamSchedule(studentId, finalSessions, 'FINAL_EXAM'));
      this.isLoading.set(false);
    });
  }

  private buildWeeklySchedule(
    studentId: number,
    sessions: SessionResponse[]
  ): ClassScheduleResponse {

    const DAYS = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday'];
    const breakSet = new Set(BREAK_SLOTS);

    const weeklySchedule: DaySchedule[] = DAYS.map((day, dayIndex) => {

      // dayOfWeek from backend: 1=Sunday, 2=Monday … 5=Thursday
      const daySessions = sessions.filter(
        s => Number(s.dayOfWeek) === dayIndex + 1
      );

      const slots: ScheduleSlot[] = TIME_SLOTS.map(timeSlot => {
        if (breakSet.has(timeSlot)) {
          return { timeSlot, session: null };
        }

        // Convert the 12-hour slot label (e.g. "1:50") to 24-hour zero-padded
        // "HH:MM" (e.g. "13:50") before comparing with backend's "HH:MM:SS".
        const slotStart12 = timeSlot.split(' - ')[0].trim(); // "1:50"
        const slotStart24 = to24Hour(slotStart12);            // "13:50"

        const match = daySessions.find(s => {
          if (!s.startAt) return false;
          return s.startAt.substring(0, 5) === slotStart24;  // "13:50" === "13:50" ✓
        });

        const session: ClassSessionResponse | null = match
          ? {
              id:          match.id,
              subject:     match.courseName,
              subjectCode: '',
              teacher:     match.teacherName,
            }
          : null;

        return { timeSlot, session };
      });

      return { day, slots };
    });

    return { studentId, weeklySchedule };
  }

  private buildExamSchedule(
    studentId: number,
    sessions: SessionResponse[],
    type: 'MONTH_EXAM' | 'FINAL_EXAM'
  ): ExamScheduleResponse {
    const exams: ExamEntry[] = sessions.map(s => ({
      id:        s.id,
      subject:   s.courseName,
      teacher:   s.teacherName,
      startTime: s.startAt  ?? '',
      endTime:   s.endAt    ?? '',
      examDate:  s.examDate ?? '',
      type,
    }));
    return { studentId, exams };
  }

  clearError(): void {
    this.error.set(null);
  }
}