import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { NotificationItem } from '../models/notification'; // الاسم الصحيح للموديل

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  // عنوان الـ API المعتمد من الباك إند بتاعكم مستقبلاً
  private apiUrl = 'http://localhost:8080/api/v1/notifications';

  constructor(private http: HttpClient) {}

  // الميثود اللي هتجيب الداتا حية من السبرينج بوت أول ما تشغلوه
  getNotifications(): Observable<NotificationItem[]> {
    return this.http.get<NotificationItem[]>(this.apiUrl);
  }
}