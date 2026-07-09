export interface ComplaintDetailsResponse {
  complaintId: number;
  title: string;
  description: string;
  status: string;
  response: string | null;
  submittedAt: string;
}
