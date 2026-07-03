import { Component, OnInit ,inject,effect,AfterViewInit, OnDestroy, ViewChild, ElementRef} from '@angular/core';
import { SideMenu } from '../side-menu/side-menu';
import {Chart,LineController, LineElement, PointElement,LinearScale, CategoryScale,DoughnutController, ArcElement, Tooltip, Legend, Filler, ChartOptions} from 'chart.js';
import { RouterOutlet } from '@angular/router';
import { Dashboardservice } from '../../services/dashboardservice';
Chart.register(LineController, LineElement, PointElement,LinearScale, CategoryScale,DoughnutController, ArcElement,Tooltip, Legend, Filler);

@Component({
  selector: 'app-dashboard',
  imports: [SideMenu,RouterOutlet],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit, AfterViewInit, OnDestroy{

  svc = inject(Dashboardservice);

  @ViewChild('lineCanvas')   lineCanvas!:   ElementRef<HTMLCanvasElement>;
  @ViewChild('donutCanvas')  donutCanvas!:  ElementRef<HTMLCanvasElement>;

   private lineChart?:  Chart<'line'>;
  private donutChart?: Chart<'doughnut'>;

  private lineOptions: ChartOptions<'line'> = {
    responsive: true,
    plugins: {
      legend: { display: false },
      tooltip: { mode: 'index', intersect: false }
    },
    scales: {
      x: { grid: { display: false } },
      y: { min: 0, max: 100, grid: { color: '#eee' } }
    }
  };

  private donutOptions: ChartOptions<'doughnut'> = {
    responsive: true,
    cutout: '75%',
    plugins: {
      legend: {
        position: 'right',
        labels: { usePointStyle: true, padding: 20 }
      }
    }
  };


  constructor() {
    
    effect(() => {
      const lineData  = this.svc.lineChartData();
      const donutData = this.svc.donutChartData();

      if (this.lineChart && lineData.datasets[0]?.data.length) {
        this.lineChart.data = lineData;
        this.lineChart.update();
      }

      if (this.donutChart && donutData.datasets[0]?.data.length) {
        this.donutChart.data = donutData;
        this.donutChart.update();
      }
    });
  }

  ngAfterViewInit(): void {
    this.initCharts();
  }

  private initCharts(): void {
    // Line Chart
    this.lineChart = new Chart(this.lineCanvas.nativeElement, {
      type: 'line',
      data: this.svc.lineChartData(),
      options: this.lineOptions
    });

    // Donut Chart
    this.donutChart = new Chart(this.donutCanvas.nativeElement, {
      type: 'doughnut',
      data: this.svc.donutChartData(),
      options: this.donutOptions
    });
  }

  ngOnInit(): void {
    this.svc.loadDashboard();
  }


  ngOnDestroy(): void {
    this.lineChart?.destroy();
    this.donutChart?.destroy();
  }

}
