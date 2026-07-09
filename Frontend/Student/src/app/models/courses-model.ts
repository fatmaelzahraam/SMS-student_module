export interface AssignmentSummary {
  id: number;
  name: string;
}

export interface CourseResponse {
  id: number;
  courseName: string;
  courseType: string;
  description?: string;
  studyPlan?: string;
  teacherId: number;
  teacherName: string;
  termId: number;
  termNumber: number;
  termYear: number;
  assignments: AssignmentSummary[];
}

export interface CourseRequest {
  id: number;
  teacherId: number;
  termId: number;
  courseType: string;
  courseName: string;
  description?: string;
  studyPlan?: string;
  assignmentIds?: number[];
}