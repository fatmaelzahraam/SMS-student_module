export interface PerformanceDTO {
  score: number;
  label: string;
}

export interface AttendanceDTO {
  percent: number;
  period: string;
  absentCount: number;
  lateCount: number;
}

export interface AssignmentsDTO {
  completed: number;
  total: number;
  delta: number;
  completionPercent: number;
}

export interface AcademicRankDTO {
  rank: number;
  totalStudents: number;
  rankChange: number;
}

export interface PerformancePointDTO {
  date: string;
  score: number;
}

export interface DashboardInterface {
  performance: PerformanceDTO;
  attendance: AttendanceDTO;
  assignments: AssignmentsDTO;
  academicRank: AcademicRankDTO;
  performanceOverTime: PerformancePointDTO[];
}
