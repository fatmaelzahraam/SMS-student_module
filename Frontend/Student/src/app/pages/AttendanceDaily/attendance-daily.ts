import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive } from '@angular/router';

import { AttendanceNav } from '../../components/attendance-nav/attendance-nav';
import { AttendanceDailyResponse } from '../../models/AttendanceDailyResponse';
import { AttendanceService } from '../attendance/service/attendace-service';


@Component({
  selector: 'app-attendance-daily',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive, AttendanceNav],
  templateUrl: './attendance-daily.html',
  styleUrl: './attendance-daily.css'
})
export class AttendanceDaly implements OnInit {

  dailyAttendance?: AttendanceDailyResponse;

  // Default to today — no hardcoded studentId
  selectedDate: string = new Date().toISOString().split('T')[0];

  constructor(private attendanceService: AttendanceService) {}

  ngOnInit(): void {
    this.loadDailyAttendance();
  }

  loadDailyAttendance(): void {
    this.attendanceService
      .getDailyAttendance(this.selectedDate)
      .subscribe({
        next: (response) => {
          this.dailyAttendance = response;
        },
        error: (err) => console.error('Failed to load daily attendance:', err)
      });
  }

  // Called by the left chevron button
  previousDay(): void {
    const date = new Date(this.selectedDate);
    date.setDate(date.getDate() - 1);
    this.selectedDate = date.toISOString().split('T')[0];
    this.loadDailyAttendance();
  }

  // Called by the right chevron button
  nextDay(): void {
    const date = new Date(this.selectedDate);
    date.setDate(date.getDate() + 1);
    this.selectedDate = date.toISOString().split('T')[0];
    this.loadDailyAttendance();
  }
}