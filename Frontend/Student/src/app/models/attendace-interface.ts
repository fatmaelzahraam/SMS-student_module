export interface Attendance {
  attendanceId: number;
  studentId: number;
  studentName: string;
  sessionId: number | null;
  status: 'P' | 'A' | 'L';
  dateTime: string;
}