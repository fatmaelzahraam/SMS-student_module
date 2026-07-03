import { HttpClient } from '@angular/common/http';
import { Injectable , inject ,  signal, computed} from '@angular/core';
import { Observable } from 'rxjs';
import { DashboardInterface } from '../models/dashboard-interface';
import { ChartData } from 'chart.js';

@Injectable({
  providedIn: 'root',
})
export class Dashboardservice {

  private http = inject(HttpClient);

  private _dashboard = signal<DashboardInterface | null>(null);
  private _loading   = signal<boolean>(false);
  private _error     = signal<string | null>(null);

  readonly dashboard = this._dashboard.asReadonly();
  readonly loading   = this._loading.asReadonly();
  readonly error     = this._error.asReadonly();

  readonly performance    = computed(() => this._dashboard()?.performance ?? null);
  readonly attendance     = computed(() => this._dashboard()?.attendance ?? null);
  readonly assignments    = computed(() => this._dashboard()?.assignments ?? null);
  readonly academicRank   = computed(() => this._dashboard()?.academicRank ?? null);
  readonly performanceTrend = computed(() => this._dashboard()?.performanceOverTime ?? []);

   readonly assignmentPercent = computed(() => {
    const a = this._dashboard()?.assignments;
    if (!a || a.total === 0) return 0;
    return Math.round((a.completed / a.total) * 100);
  });

   readonly performanceLabel = computed(() => {
    const score = this._dashboard()?.performance?.score ?? 0;
    if (score >= 90) return 'Excellent';
    if (score >= 75) return 'Good';
    if (score >= 60) return 'Average';
    return 'Needs Improvement';
  });

  readonly lineChartData = computed<ChartData<'line'>>(() => {
    const trend = this._dashboard()?.performanceOverTime ?? [];
    return {
      labels: trend.map(p => p.date),
      datasets: [{
        label: 'Performance',
        data: trend.map(p => p.score),
        borderColor: '#620000',
        backgroundColor: 'rgba(98, 0, 0, 0.1)',
        fill: true,
        tension: 0.4,
        pointBackgroundColor: '#620000',
        pointRadius: 5
      }]
    };
  });

  readonly donutChartData = computed<ChartData<'doughnut'>>(() => {
    const a = this._dashboard()?.assignments;
    if (!a) return { labels: [], datasets: [] };
    return {
      labels: ['Completed', 'Remaining'],
      datasets: [{
        data: [a.completed, a.total - a.completed],
        backgroundColor: ['#620000', '#EEEEEE'],
        borderWidth: 0,
        hoverOffset: 4
      }]
    };
  });
  

  loadDashboard(): void {
    this._loading.set(true);
    this._error.set(null);

    this.http.get<DashboardInterface>('/api/v1/dashboard').subscribe({
      next: (data) => {
        this._dashboard.set(data);
        this._loading.set(false);
      },
      error: (err) => {
        this._error.set(err.message ?? 'Failed to load dashboard');
        this._loading.set(false);
      }
    });
  }

  reset(): void {
    this._dashboard.set(null);
    this._error.set(null);
  }
  
}
