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
      console.log('RAW classSessions:', classSessions); // ← remove after confirming
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

        const slotStart = timeSlot.split(' - ')[0].trim(); // "8:00"

        const match = daySessions.find(s => {
          if (!s.startAt) return false;
  
          const raw = s.startAt.substring(0, 5);           // "08:00"
          const normalized = raw.replace(/^0/, '');        // "8:00"
          return normalized === slotStart;
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

  private buildExamSchedule(studentId: number, sessions: SessionResponse[], type: 'MONTH_EXAM' | 'FINAL_EXAM'): ExamScheduleResponse {
  const exams: ExamEntry[] = sessions.map(s => ({
    id:        s.id,
    subject:   s.courseName,
    teacher:   s.teacherName,
    startTime: s.startAt  ?? '',
    endTime:   s.endAt    ?? '',
    examDate:  s.examDate ?? '',   // ← from SessionResponse
    type,
  }));
  return { studentId, exams };
}

  clearError(): void {
    this.error.set(null);
  }
}