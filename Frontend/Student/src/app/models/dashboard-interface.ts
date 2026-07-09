export interface DashboardAttendanceOverview {
  presentPercentage: number;
  absenceCount: number;
  LateCount: number;
}

export interface DashboardResponse {
  performanceScore: number;
  performanceLabel: string;
  rank: number;
  totalStudents: number;
  attendance: DashboardAttendanceOverview;
}

export interface AssignmentResponse {
  id: number;
  name: string;
  description: string;
  assignDate: string;
  deadline: string;
  fileLink: string;
  studentSubmission: string;
  courseName?: string;
}

export interface MarkResponse {
  id: number;
  courseId: number;
  courseName: string;
  studentId: number;
  studentName: string;
  typeId: number;
  typeName: string;
  feedbackDate: string;
  feedback: string;
  notes: string;
  isApproved: boolean;
  score: number;
  maxScore: number;
}