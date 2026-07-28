export interface SubjectAverage {
  courseId?: number;
  subject: string;
  percentage: number;
}

export interface SubjectMark {
  subject: string;
  percentage: number;
}

export interface MonthlyMarks {
  month: string;
  subjects: SubjectMark[];
}

export interface StudentMarksDashboard {
  averagePercentage: number;
  highestMark: number;
  lowestMark: number;
  totalSubjects: number;
  academicRank: number;
  monthlyMarks: MonthlyMarks[];
  subjectAverages: SubjectAverage[];
  markTypes: string[];   
}

export interface Mark {
  id: number;
  courseId: number;
  courseName: string;
  studentId: number;
  studentName: string;
  typeId: number;
  typeName: string;
  feedbackDate: string;
  feedback: string | null;
  notes: string | null;
  isApproved: boolean;
  score: number;
  maxScore: number;
}