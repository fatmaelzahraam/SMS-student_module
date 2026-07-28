// Matches AttendanceMonthlyResponse.java exactly
export interface AttendanceMonthlyResponse {
  month: number;
  year: number;
  present: number;
  absent: number;
  late: number;
  attendancePercentage: number;
  presentDays: number;
}