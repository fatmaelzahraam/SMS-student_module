import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, NavigationEnd } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter } from 'rxjs/operators';

import {
  ApexChart, ApexAxisChartSeries, ApexNonAxisChartSeries,
  ApexXAxis, ApexYAxis, ApexTitleSubtitle, ApexDataLabels,
  ApexStroke, ApexPlotOptions, ApexTooltip, ChartComponent
} from 'ng-apexcharts';

import { forkJoin } from 'rxjs';
import { AttendanceNav } from '../../components/attendance-nav/attendance-nav';
import { AttendanceOverview } from '../../models/AttendanceOverview';
import { PermissionResponse } from '../../models/PermissionResponse';
import { PermissionService } from '../attendance/service/permission.service';
import { AttendanceService } from './service/attendace-service';

export type RadialChartOptions = {
  series: ApexNonAxisChartSeries;
  chart: ApexChart;
  plotOptions: ApexPlotOptions;
  labels: string[];
};

export type LineChartOptions = {
  series: ApexAxisChartSeries;
  chart: ApexChart;
  xaxis: ApexXAxis;
  yaxis: ApexYAxis;
  tooltip: ApexTooltip;
  dataLabels: ApexDataLabels;
  stroke: ApexStroke;
  title: ApexTitleSubtitle;
};

@Component({
  selector: 'app-attendance',
  standalone: true,
  imports: [CommonModule, ChartComponent],
  templateUrl: './attendance.html',
  styleUrl: './attendance.css'
})
export class Attendance implements OnInit, OnDestroy {

  attendance?: AttendanceOverview;
  permissions: PermissionResponse[] = [];
  private routerSub?: Subscription;

  radialChartOptions: RadialChartOptions = {
    series: [0],
    chart: { type: 'radialBar', height: 300 },
    plotOptions: {
      radialBar: {
        hollow: { size: '70%' },
        dataLabels: {
          value: { formatter: (val: number) => val.toFixed(2) + '%' }
        }
      }
    },
    labels: ['Attendance']
  };

  lineChartOptions: LineChartOptions = {
    series: [{ name: 'Attendance', data: [] }],
    chart: { type: 'line', height: 300 },
    dataLabels: { enabled: false },
    stroke: { curve: 'smooth' },
    title: { text: 'Attendance Trend' },
    xaxis: { categories: ['Present', 'Absent', 'Late'] },
    yaxis: {
      labels: { formatter: (val: number) => val.toFixed(2) + '%' }
    },
    tooltip: {
      y: { formatter: (val: number) => val.toFixed(2) + '%' }
    }
  };

  constructor(
    private attendanceService: AttendanceService,
    private permissionService: PermissionService,
    private router: Router,
    private cd: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.loadData();
  }

  ngOnDestroy(): void {
    this.routerSub?.unsubscribe();
  }

  private loadData(): void {
    forkJoin({
      overview:    this.attendanceService.getOverview(),
      permissions: this.permissionService.getMyPermissions()
    }).subscribe({
      next: ({ overview, permissions }) => {
        this.attendance = overview;

        this.radialChartOptions = {
          ...this.radialChartOptions,
          series: [overview.attendancePercentage]
        };

        this.lineChartOptions = {
          ...this.lineChartOptions,
          series: [{
            name: 'Attendance',
            data: [overview.presentDays, overview.absentDays, overview.lateDays]
          }]
        };

        this.permissions = permissions;
        this.cd.markForCheck();
      },
      error: (err) => console.error('Failed to load attendance data:', err)
    });
  }
}