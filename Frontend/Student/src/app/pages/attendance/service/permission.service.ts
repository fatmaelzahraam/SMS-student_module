import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PermissionResponse } from '../../../models/PermissionResponse';

@Injectable({ providedIn: 'root' })
export class PermissionService {

  private http = inject(HttpClient);
  private readonly api = 'http://localhost:8080/api/v1/permissions';

  getMyPermissions(): Observable<PermissionResponse[]> {
    return this.http.get<PermissionResponse[]>(`${this.api}/my`);
  }

  /** from/to format: 'YYYY-MM-DD' */
  getMyPermissionsByDateRange(from: string, to: string): Observable<PermissionResponse[]> {
    const params = new HttpParams().set('from', from).set('to', to);
    return this.http.get<PermissionResponse[]>(`${this.api}/my/date/range`, { params });
  }
}