import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CreateComplaintRequest } from '../../../models/CreateComplaintRequest';
import { ComplaintResponse } from '../../../models/ComplaintResponse';
import { ComplaintDetailsResponse } from '../../../models/ComplaintDetailsResponse';
import { ComplaintStatisticsResponse } from '../../../models/ComplaintStatisticsResponse';



@Injectable({
  providedIn: 'root'
})
export class ComplaintsService {

  private readonly apiUrl = 'http://localhost:8080/student/complaints';

  constructor(private http: HttpClient) { }

  createComplaint(request: CreateComplaintRequest): Observable<void> {
    return this.http.post<void>(this.apiUrl, request);
  }

  getMyComplaints(): Observable<ComplaintResponse[]> {
    return this.http.get<ComplaintResponse[]>(this.apiUrl);
  }

  getComplaintDetails(complaintId: number): Observable<ComplaintDetailsResponse> {
    return this.http.get<ComplaintDetailsResponse>(
      `${this.apiUrl}/${complaintId}`
    );
  }

  getStatistics(): Observable<ComplaintStatisticsResponse> {
    return this.http.get<ComplaintStatisticsResponse>(
      `${this.apiUrl}/statistics`
    );
  }
}
