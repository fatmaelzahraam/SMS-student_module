import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, NavigationEnd, RouterLink, RouterLinkActive } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter } from 'rxjs/operators';

import { AttendanceNav } from '../../components/attendance-nav/attendance-nav';
import { AttendanceDailyResponse } from '../../models/AttendanceDailyResponse';
import { AttendanceService } from '../attendance/service/attendace-service';

@Component({
  selector: 'app-attendance-daily',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './attendance-daily.html',
  styleUrl: './attendance-daily.css'
})
export class AttendanceDaly implements OnInit, OnDestroy {

  dailyAttendance?: AttendanceDailyResponse;
  selectedDate: string = new Date().toISOString().split('T')[0];
  private routerSub?: Subscription;

  constructor(
    private attendanceService: AttendanceService,
    private router: Router,
    private cd: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadDailyAttendance();
  }

  ngOnDestroy(): void {
    this.routerSub?.unsubscribe();
  }

  loadDailyAttendance(): void {
    this.attendanceService
      .getDailyAttendance(this.selectedDate)
      .subscribe({
        next: (response) => {
          this.dailyAttendance = response;
           this.cd.markForCheck();
        },
        error: (err) => console.error('Failed to load daily attendance:', err)
      });
  }

  previousDay(): void {
    const date = new Date(this.selectedDate);
    date.setDate(date.getDate() - 1);
    this.selectedDate = date.toISOString().split('T')[0];
    this.loadDailyAttendance();
  }

  nextDay(): void {
    const date = new Date(this.selectedDate);
    date.setDate(date.getDate() + 1);
    this.selectedDate = date.toISOString().split('T')[0];
    this.loadDailyAttendance();
  }
}