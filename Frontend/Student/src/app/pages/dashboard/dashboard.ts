import {
  ChangeDetectorRef,
  Component,
  computed,
  effect,
  ElementRef,
  OnDestroy,
  OnInit,
  signal,
  ViewChild
} from '@angular/core';
import {CommonModule, DecimalPipe} from '@angular/common';
import {Chart, registerables} from 'chart.js';
import {Dashboardservice} from './service/dashboardservice';
import {DashboardHeader} from '../../components/dashboard-header/dashboard-header';
import {AuthService} from '../login/service/auth-service';
import {DashboardResponse} from '../../models/dashboard-interface';

Chart.register(...registerables);

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, DecimalPipe, DashboardHeader],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit, OnDestroy {

  // ── Chart canvas refs ────────────────────────────────────────────────────────
  @ViewChild('lineChartRef') lineChartRef!: ElementRef<HTMLCanvasElement>;
  @ViewChild('donutChartRef') donutChartRef!: ElementRef<HTMLCanvasElement>;

  private lineChartInst: Chart | null = null;
  private donutChartInst: Chart | null = null;

  // ── Signals ──────────────────────────────────────────────────────────────────
  dashboard = signal<DashboardResponse | null>(null);
  loading = signal(true);
  error = signal<string | null>(null);

  performanceLabel = computed(() => {
    const score = this.dashboard()?.performanceScore ?? 0;
    if (score >= 90) return {text: 'Excellent', color: '#2ecc71'};
    if (score >= 75) return {text: 'Good', color: '#87A7C8'};
    if (score >= 60) return {text: 'Average', color: '#f39c12'};
    return {text: 'Needs Improvement', color: '#e74c3c'};
  });

  attendanceCircumference = 2 * Math.PI * 45;

  attendanceDashOffset = computed(() => {
    const pct = this.dashboard()?.attendance?.presentPercentage ?? 0;
    return this.attendanceCircumference * (1 - pct / 100);
  });

  rankOrdinal = computed(() => {
    const r = this.dashboard()?.rank ?? 0;
    if (r === 1) return '1st';
    if (r === 2) return '2nd';
    if (r === 3) return '3rd';
    return `${r}th`;
  });

  // ── Assignment computed signals ───────────────────────────────────────────────
  totalAssignments = computed(() => this.svc.assignments().length);

  completedAssignments = computed(() =>
    this.svc.assignments().filter(a => !!a.studentSubmission).length
  );

  completedPct = computed(() => {
    const total = this.totalAssignments();
    return total === 0 ? 0 : Math.round((this.completedAssignments() / total) * 100);
  });

  remainingCount = computed(() =>
    Math.max(0, this.totalAssignments() - this.completedAssignments())
  );

  // ── Effects ───────────────────────────────────────────────────────────────────
  private readonly dataEffect = effect(() => {
    const loading = this.svc.isLoading();
    const dashboard = this.svc.dashboard();
    const err = this.svc.error();

    queueMicrotask(() => {
      if (!loading) {
        this.dashboard.set(dashboard);
        this.loading.set(false);
        if (err) this.error.set(err);
        this.cdr.markForCheck();
      }
    });
  });

  private readonly chartEffect = effect(() => {
    // Track every signal that feeds the charts
    const marks = this.svc.recentMarks();
    const assignments = this.svc.assignments();
    const loading = this.svc.isLoading();

    // Wait until the backend has responded before touching the canvas
    if (loading) return;

    queueMicrotask(() => {
      this.buildLineChart();
      this.buildDonutChart();
    });
  });

  constructor(
    readonly svc: Dashboardservice,
    private authService: AuthService,
    private cdr: ChangeDetectorRef,
  ) {
  }

  // ── Lifecycle ─────────────────────────────────────────────────────────────────
  ngOnInit(): void {
    const studentId = this.authService.getCurrentUserId();
    this.svc.loadAll(studentId);
  }

  ngOnDestroy(): void {
    this.lineChartInst?.destroy();
    this.donutChartInst?.destroy();
    this.dataEffect.destroy();
    this.chartEffect.destroy();
  }

  // ── Helpers used by the template ─────────────────────────────────────────────
  getPerformanceBarWidth(): string {
    return `${this.dashboard()?.performanceScore ?? 0}%`;
  }

  getAttendanceColor(): string {
    const pct = this.dashboard()?.attendance?.presentPercentage ?? 0;
    if (pct >= 90) return '#2ecc71';
    if (pct >= 75) return '#f39c12';
    return '#e74c3c';
  }

  getPerformanceLabel(score: number): string {
    if (score >= 90) return 'Excellent';
    if (score >= 75) return 'Good';
    if (score >= 60) return 'Average';
    return 'Needs work';
  }

  clearError(): void {
    this.svc.clearError();
  }

  // ── Chart builders ────────────────────────────────────────────────────────────
  private buildLineChart(): void {
    if (!this.lineChartRef?.nativeElement) return;
    if (this.lineChartInst) {
      this.lineChartInst.destroy();
      this.lineChartInst = null;
    }

    const marks = this.svc.recentMarks();


    const sorted = [...marks].sort((a, b) =>
      (a.feedbackDate ?? '').localeCompare(b.feedbackDate ?? '')
    );
    const labels = sorted.map(m =>
      m.feedbackDate ? m.feedbackDate.slice(5) : '??'
    );


    const data = sorted.map(m =>
      m.maxScore > 0 ? Math.round((m.score / m.maxScore) * 100) : 0
    );

    this.lineChartInst = new Chart(this.lineChartRef.nativeElement, {
      type: 'line',
      data: {
        labels,
        datasets: [{
          data,
          borderColor: '#620000',
          backgroundColor: 'rgba(98,0,0,0.08)',
          borderWidth: 2,
          pointBackgroundColor: '#620000',
          pointRadius: 4,
          pointHoverRadius: 6,
          fill: true,
          tension: 0.4,
        }],
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {display: false},
          ...(marks.length === 0 && {
            title: {
              display: true,
              text: 'No marks data available yet',
              color: '#aaa',
              font: {size: 13},
            },
          }),
        },
        scales: {
          y: {
            min: 0,
            max: 100,
            ticks: {callback: v => v + '%', font: {size: 10}, color: '#888'},
            grid: {color: '#f0f0f0'},
            border: {display: false},
          },
          x: {
            ticks: {font: {size: 10}, color: '#888', maxRotation: 0},
            grid: {display: false},
            border: {display: false},
          },
        },
      },
    });
  }

  private buildDonutChart(): void {
    if (!this.donutChartRef?.nativeElement) return;
    if (this.donutChartInst) {
      this.donutChartInst.destroy();
      this.donutChartInst = null;
    }

    const completed = this.completedAssignments();
    const remaining = this.remainingCount();
    const hasData = this.totalAssignments() > 0;

    this.donutChartInst = new Chart(this.donutChartRef.nativeElement, {
      type: 'doughnut',
      data: {
        labels: ['Completed', 'Remaining'],
        datasets: [{
          data: hasData ? [completed, remaining] : [1, 0],
          backgroundColor: hasData
            ? ['#620000', '#e0e0e0']
            : ['#e0e0e0', '#e0e0e0'],
          borderWidth: 0,
          hoverOffset: 4,
        }],
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        cutout: '75%',
        plugins: {
          legend: {display: false},
          tooltip: {enabled: hasData},   // disable tooltip on empty state
          ...((!hasData) && {
            title: {
              display: true,
              text: 'No assignments yet',
              color: '#aaa',
              font: {size: 13},
            },
          }),
        },
      },
    });
  }
}
