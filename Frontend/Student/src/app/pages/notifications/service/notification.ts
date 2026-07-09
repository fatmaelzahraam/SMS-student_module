import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { NotificationItem } from '../../../models/notification';


@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(private http: HttpClient) {}

  getNotifications(): Observable<NotificationItem[]> {
    return this.http.get<NotificationItem[]>(
      'http://localhost:8080/api/v1/notifications'
    );
  }
}


