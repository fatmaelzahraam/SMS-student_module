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
        return '👤';

      case 'GRADE':
        return '📘';

      case 'ASSIGNMENT':
        return '📝';

      case 'TRAINING':
        return '📅';

      case 'REPORT':
        return '📄';

      case 'SYSTEM':
        return '⚙️';

      default:
        return '🔔';
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