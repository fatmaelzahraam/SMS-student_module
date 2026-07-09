import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { AttendanceNav } from '../../components/attendance-nav/attendance-nav';
import { AttendanceDailyResponse } from '../../models/AttendanceDailyResponse';
import { AttendanceService } from '../attendance/service/attendace-service';


@Component({
  selector: 'app-attendance-daily',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    RouterLinkActive,
   AttendanceNav
  ],
  templateUrl: './attendance-daily.html',
  styleUrl: './attendance-daily.css',
})
export class AttendanceDaly implements OnInit {

  dailyAttendance?: AttendanceDailyResponse;

  studentId = 1;
selectedDate = '2026-07-06';

  constructor(
    private attendanceService: AttendanceService
  ) {}

  ngOnInit(): void {
    this.loadDailyAttendance();
  }

  loadDailyAttendance(): void {
    this.attendanceService
      .getDailyAttendance(this.studentId, this.selectedDate)
      .subscribe({
        next: (response) => {
          this.dailyAttendance = response;
          console.log(response);
        },
        error: (error) => {
          console.error(error);
        }
      });
  }

}

