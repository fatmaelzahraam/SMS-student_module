import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

import {
  NgApexchartsModule,
  ApexChart,
  ApexNonAxisChartSeries,
  ApexPlotOptions
} from 'ng-apexcharts';
import { AttendanceNav } from '../../components/attendance-nav/attendance-nav';
import { AttendanceMonthlyResponse } from '../../models/AttendaceMonthlyResponse';
import { AttendanceService } from '../attendance/service/attendace-service';

export type RadialChartOptions = {
  series: ApexNonAxisChartSeries;
  chart: ApexChart;
  plotOptions: ApexPlotOptions;
  labels: string[];
};

@Component({
  selector: 'app-attendance-monthly',
  standalone: true,
  imports: [
    CommonModule,
    AttendanceNav,
    NgApexchartsModule
  ],
  templateUrl: './attendance-monthly.html',
  styleUrl: './attendance-monthly.css'
})
export class AttendanceMonthly implements OnInit {

  monthlyAttendance?: AttendanceMonthlyResponse;

  studentId = 1;
  month = 7;
  year = 2026;

  radialChartOptions: RadialChartOptions = {
    series: [0],
    chart: {
      type: 'radialBar',
      height: 300
    },
    plotOptions: {
      radialBar: {
        hollow: {
          size: '70%'
        }
      }
    },
    labels: ['Attendance']
  };

  constructor(private attendanceService: AttendanceService) {}

  ngOnInit(): void {
    this.loadMonthlyAttendance();
  }

  loadMonthlyAttendance(): void {
    this.attendanceService
      .getMonthlyAttendance(this.studentId, this.month, this.year)
      .subscribe({
        next: (response) => {
          this.monthlyAttendance = response;

          this.radialChartOptions = {
            ...this.radialChartOptions,
            series: [response.attendancePercentage]
          };

          console.log(response);
        },
        error: (error) => {
          console.error(error);
        }
      });
  }
}
