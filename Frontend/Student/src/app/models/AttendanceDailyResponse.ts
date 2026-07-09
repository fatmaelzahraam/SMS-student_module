export interface AttendanceResponse {
  session: number;
  time: string;
  subject: string;
  instructor: string;
  status: string;
}

export interface AttendanceDailyResponse {

  date: string;

  totalSessions: number;

  present: number;

  absent: number;

  late: number;

  attendancePercentage: number;

  attendanceList: AttendanceResponse[];

}
