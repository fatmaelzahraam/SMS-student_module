import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

import {
  NgApexchartsModule,
  ApexChart,
  ApexNonAxisChartSeries,
  ApexPlotOptions
} from 'ng-apexcharts';

import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { AttendanceNav } from '../../components/attendance-nav/attendance-nav';
import { PermissionResponse } from '../../models/PermissionResponse';
import { PermissionService } from '../attendance/service/permission.service';
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
  imports: [CommonModule, AttendanceNav, NgApexchartsModule],
  templateUrl: './attendance-monthly.html',
  styleUrl: './attendance-monthly.css'
})
export class AttendanceMonthly implements OnInit {

  monthlyAttendance?: AttendanceMonthlyResponse;
  permissions: PermissionResponse[] = [];

  month = new Date().getMonth() + 1;
  year  = new Date().getFullYear();

  radialChartOptions: RadialChartOptions = {
    series: [0],
    chart: { type: 'radialBar', height: 300 },
    plotOptions: {
      radialBar: {
        hollow: { size: '70%' },
        dataLabels: {
          value: {
            formatter: (val: number) => val.toFixed(2) + '%'
          }
        }
      }
    },
    labels: ['Attendance']
  };

  constructor(
    private attendanceService: AttendanceService,
    private permissionService: PermissionService
  ) {}

  ngOnInit(): void {
    this.loadMonthlyData();
  }

  loadMonthlyData(): void {
    const from = this.firstDayOfMonth();
    const to   = this.lastDayOfMonth();

    forkJoin({
      monthly: this.attendanceService
        .getMonthlyAttendance(this.month, this.year)
        .pipe(catchError(err => {
          console.error('Monthly attendance error:', err);
          return of(null);
        })),

      permissions: this.permissionService
        .getMyPermissionsByDateRange(from, to)
        .pipe(catchError(err => {
          console.error('Permissions error:', err);
          return of([]);
        }))

    }).subscribe(({ monthly, permissions }) => {

      if (monthly) {
        this.monthlyAttendance = monthly;
        this.radialChartOptions = {
          ...this.radialChartOptions,
          series: [monthly.attendancePercentage]
        };
      }

      this.permissions = permissions ?? [];
    });
  }

  previousMonth(): void {
    if (this.month === 1) { this.month = 12; this.year -= 1; }
    else                  { this.month -= 1; }
    this.loadMonthlyData();
  }

  nextMonth(): void {
    if (this.month === 12) { this.month = 1; this.year += 1; }
    else                   { this.month += 1; }
    this.loadMonthlyData();
  }

  get monthLabel(): string {
    return new Date(this.year, this.month - 1, 1)
      .toLocaleDateString('en-US', { month: 'long', year: 'numeric' });
  }

  private firstDayOfMonth(): string {
    return `${this.year}-${String(this.month).padStart(2, '0')}-01`;
  }

  private lastDayOfMonth(): string {
    const last = new Date(this.year, this.month, 0).getDate();
    return `${this.year}-${String(this.month).padStart(2, '0')}-${String(last).padStart(2, '0')}`;
  }
}