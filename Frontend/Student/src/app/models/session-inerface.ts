// models/session-interface.ts

export interface SessionResponse {
  id:          number;
  className:   string;
  courseName:  string;
  teacherName: string;
  dayOfWeek:   number;
  startAt:     string;   // "09:00:00"
  endAt:       string;   // "11:00:00"
  examDate:    string;   // "2026-04-24" ← maps to Session.updatedAt
}

export interface ClassSessionResponse {
  id:          number;
  subject:     string;
  subjectCode: string;
  teacher:     string;
}

export interface ScheduleSlot {
  timeSlot: string;
  session:  ClassSessionResponse | null;
}

export interface DaySchedule {
  day:   string;
  slots: ScheduleSlot[];
}

export interface ClassScheduleResponse {
  studentId:       number;
  weeklySchedule:  DaySchedule[];
}

export interface ExamEntry {
  id:        number;
  subject:   string;
  teacher:   string;
  startTime: string;
  endTime:   string;
  examDate:  string;
  type:      'MONTH_EXAM' | 'FINAL_EXAM';
}

export interface ExamScheduleResponse {
  studentId: number;
  exams:     ExamEntry[];
}

export interface SessionInterface {
  subject: string;
  teacher: string;
  color?:  string;
}

// ── Grid constants ────────────────────────────────────────────
// Keep these as "H:MM" (no leading zero) to match backend "08:00:00" → stripped to "8:00"
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