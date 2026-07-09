export interface AttendanceRequest {
  studentId: number;
  sessionId: number;
  status: 'P' | 'A' | 'L';
}