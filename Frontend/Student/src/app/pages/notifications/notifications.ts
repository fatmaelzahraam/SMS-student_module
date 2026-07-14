import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NotificationService } from './service/notification';
import { NotificationItem } from '../../models/notification';

@Component({
  selector: 'app-notifications',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './notifications.html',
  styleUrl: './notifications.css',
})
export class Notifications implements OnInit {
  
  notifications: NotificationItem[] =[];

  constructor(private notificationService: NotificationService) {}

  ngOnInit(): void {
    this.loadNotifications();
  }

  loadNotifications(): void {
    this.notificationService.getNotifications().subscribe({
      next: (data) => {
        this.notifications = data;
      },
      error: (err) => {
        console.error('Error loading notifications', err);
      }
    });
  }

  getIcon(type: string): string {
  switch (type) {
    case 'STUDENT':
      return 'fa-solid fa-user';

    case 'GRADE':
      return 'fa-solid fa-book';

    case 'ASSIGNMENT':
      return 'fa-solid fa-file-lines';

    case 'TRAINING':
      return 'fa-solid fa-calendar-days';

    case 'REPORT':
      return 'fa-solid fa-file';

    case 'SYSTEM':
      return 'fa-solid fa-gear';

    default:
      return 'fa-solid fa-bell';
  }
}
  getZoomClass(type: string): string {
    switch (type) {
      case 'STUDENT':
        return 'green';

      case 'GRADE':
        return 'blue';

      case 'ASSIGNMENT':
        return 'yellow';

      case 'TRAINING':
        return 'purple';

      case 'REPORT':
        return 'red';

      case 'SYSTEM':
        return 'orange';

      default:
        return 'gray';
    }
  }

}