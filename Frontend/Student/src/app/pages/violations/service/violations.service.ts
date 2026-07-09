import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ViolationResponse } from '../../../models/ViolationResponse';
import { ViolationDetailsResponse } from '../../../models/ViolationDetailsResponse';
import { ViolationStatisticsResponse } from '../../../models/ViolationStatisticsResponse';





@Injectable({
  providedIn: 'root'
})
export class ViolationsService {

  private readonly apiUrl = 'http://localhost:8080/api/student/violations';

  constructor(
    private http: HttpClient
  ) {}

  getMyViolations(): Observable<ViolationResponse[]> {

    return this.http.get<ViolationResponse[]>(
      `${this.apiUrl}`
    );

  }

  getViolationDetails(id: number): Observable<ViolationDetailsResponse> {

    return this.http.get<ViolationDetailsResponse>(
      `${this.apiUrl}/${id}`
    );

  }

  getStatistics(): Observable<ViolationStatisticsResponse> {

    return this.http.get<ViolationStatisticsResponse>(
      `${this.apiUrl}/statistics`
    );

  }

}
