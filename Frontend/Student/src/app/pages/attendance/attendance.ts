import { Component, OnInit } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';


import {ApexChart, ApexAxisChartSeries, ApexNonAxisChartSeries, ApexXAxis, ApexTitleSubtitle,
  ApexDataLabels, ApexStroke, ApexPlotOptions ,ChartComponent} from 'ng-apexcharts';
import { AttendanceNav } from '../../components/attendance-nav/attendance-nav';
import { AttendanceOverview } from '../../models/AttendanceOverview';
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
  dataLabels: ApexDataLabels;
  stroke: ApexStroke;
  title: ApexTitleSubtitle;
};

@Component({
  selector: 'app-attendance',
  standalone: true,
  imports: [AttendanceNav,ChartComponent],
  templateUrl: './attendance.html',
  styleUrl: './attendance.css'
})
export class Attendance implements OnInit {

  attendance?: AttendanceOverview;

  constructor(private attendanceService: AttendanceService) {}

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

  lineChartOptions: LineChartOptions = {
    series: [
      {
        name: 'Attendance',
        data: []
      }
    ],
    chart: {
      type: 'line',
      height: 300
    },
    dataLabels: {
      enabled: false
    },
    stroke: {
      curve: 'smooth'
    },
    title: {
      text: 'Attendance Trend'
    },
    xaxis: {
      categories: ['Present', 'Absent', 'Late']
    }
  };

  ngOnInit(): void {

    const studentId = 1;

    this.attendanceService.getOverview(studentId).subscribe({

      next: (data) => {

        console.log(data);

        this.attendance = data;

        this.radialChartOptions = {
          ...this.radialChartOptions,
          series: [data.attendancePercentage]
        };

        this.lineChartOptions = {
          ...this.lineChartOptions,
          series: [
            {
              name: 'Attendance',
              data: [
                data.presentDays,
                data.absentDays,
                data.lateDays
              ]
            }
          ]
        };

      },

      error: (err) => {
        console.error(err);
      }

    });

  }

}
