export interface AssignmentResponse {
  id: number;
  name: string;
  deadline: string;        // LocalDate  'YYYY-MM-DD'
  assignDate: string;
  description: string;
  fileLink: string;
  studentSubmission: string;
  courseIds: number[];
  completed: number;
  total: number;
  completionPercent: number;
}