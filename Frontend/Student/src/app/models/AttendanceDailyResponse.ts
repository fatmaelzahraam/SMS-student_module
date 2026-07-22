// Matches AttendaceResponse.java exactly
export interface AttendanceResponse {
  id: number;
  studentId: number;
  studentName: string;
  sessionId: number | null;
  sessionNumber: number;       // slot position 1-8 in the day
  status: 'P' | 'A' | 'L';
  dateTime: string;
  courseName: string | null;   // Session → Course
  teacherName: string | null;  // Session → Course → Teacher → User
}

// Matches AttendanceDailyResponse.java exactly
export interface AttendanceDailyResponse {
  date: string;
  totalSessions: number;
  present: number;
  absent: number;
  late: number;
  attendancePercentage: number;
  attendanceList: AttendanceResponse[];
}