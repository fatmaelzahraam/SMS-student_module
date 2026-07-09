// models/session-interface.ts

// ── Raw backend response (what /api/v1/schedule returns) ──────
export interface SessionResponse {
  id: number;
  className:   string;
  courseName:  string;
  teacherName: string;
  dayOfWeek:   number;   // 1 = Sunday … 5 = Thursday
  startAt:     string;   // "08:00:00"
  endAt:       string;   // "08:50:00"
}

// ── Raw session card (used in static/local data) ──────────────
export interface SessionInterface {
  subject: string;
  teacher: string;
  color?: string;
}

// ── Backend response DTOs ─────────────────────────────────────
export interface ClassSessionResponse {
  id: number;
  subject: string;      // course.courseName
  subjectCode: string;  // course.courseType
  teacher: string;      // firstName + lastName
}

export interface ScheduleSlot {
  timeSlot: string;               // "08:00 - 08:50"
  session: ClassSessionResponse | null;
}

export interface DaySchedule {
  day: string;                    // "Sunday" ... "Thursday"
  slots: ScheduleSlot[];
}

export interface ClassScheduleResponse {
  studentId: number;
  weeklySchedule: DaySchedule[];
}

export interface ExamEntry {
  id: number;
  subject: string;
  teacher: string;
  startTime: string;
  endTime: string;
  type: 'MONTH_EXAM' | 'FINAL_EXAM';
}

export interface ExamScheduleResponse {
  studentId: number;
  exams: ExamEntry[];
}

// ── Schedule grid constants ───────────────────────────────────
export const TIME_SLOTS = [
  '8:00 - 8:50',
  '8:50 - 9:40',
  '9:40 - 10:30',
  '10:30 - 11:00',  // Break
  '11:00 - 11:50',
  '11:50 - 12:40',
  '12:40 - 1:30',
  '1:30 - 1:50',    // Break
  '1:50 - 2:40',
  '2:40 - 3:30',
];

export const BREAK_SLOTS = ['10:30 - 11:00', '1:30 - 1:50'];