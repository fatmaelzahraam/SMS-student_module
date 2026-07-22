// Matches PermissionResponse.java exactly
export interface PermissionResponse {
  id: number;
  reason: string;
  notes: string | null;
  date: string;        // ISO date string e.g. "2026-07-06"
  studentId: number;
  studentName: string;
}
