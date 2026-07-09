export interface AttendanceMonthlyResponse {
  month: number;
  year: number;
  present: number;
  absent: number;
  late: number;
  permission: number;
  attendancePercentage: number;

}
