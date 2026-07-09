export interface ProjectResponse {
  id: number;
  courseId: number;
  courseName: string;
  name: string;
  description?: string;
  assignDate: string;   
  deadline: string;     
}
export interface ProjectMember {
  name: string;
  role?: 'Leader' | 'Co-Leader' | null;
}