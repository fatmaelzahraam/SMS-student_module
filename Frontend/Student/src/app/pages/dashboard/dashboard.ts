import {
  Component, OnInit, AfterViewInit, OnDestroy,
  ViewChild, ElementRef, signal, computed, effect, ChangeDetectorRef
} from '@angular/core';
import { CommonModule, DecimalPipe } from '@angular/common';
import { Chart, registerables } from 'chart.js';
import { Dashboardservice } from './service/dashboardservice';
import { DashboardHeader } from '../../components/dashboard-header/dashboard-header';
import { AuthService } from '../login/service/auth-service';
import { DashboardResponse } from '../../models/dashboard-interface';

Chart.register(...registerables);

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, DecimalPipe, DashboardHeader],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit, AfterViewInit, OnDestroy {

  // ── Chart canvas refs ────────────────────────────────────────────────────
  @ViewChild('lineChartRef') lineChartRef!: ElementRef<HTMLCanvasElement>;
  @ViewChild('donutChartRef') donutChartRef!: ElementRef<HTMLCanvasElement>;

  private lineChartInst:  Chart | null = null;
  private donutChartInst: Chart | null = null;

  // ── Signals ───────────────────────────────────────────────────────────────
  dashboard = signal<DashboardResponse | null>(null);
  loading   = signal(true);
  error     = signal<string | null>(null);

  // ── Derived signals ───────────────────────────────────────────────────────
  performanceLabel = computed(() => {
    const score = this.dashboard()?.performanceScore ?? 0;
    if (score >= 90) return { text: 'Excellent',         color: '#2ecc71' };
    if (score >= 75) return { text: 'Good',              color: '#87A7C8' };
    if (score >= 60) return { text: 'Average',           color: '#f39c12' };
    return               { text: 'Needs Improvement', color: '#e74c3c' };
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

  // ── Assignment computed signals ───────────────────────────────────────────
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

  // ── Effects declared as fields (valid injection context) ─────────────────
  private readonly dataEffect = effect(() => {
    // React to service loading finishing
    const loading   = this.svc.isLoading();
    const dashboard = this.svc.dashboard();
    const err       = this.svc.error();

    // Use untracked queueMicrotask to avoid NG0100
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
    // Track the signals that should trigger a chart rebuild
    this.svc.recentMarks();
    this.svc.assignments();

    queueMicrotask(() => {
      this.buildLineChart();
      this.buildDonutChart();
    });
  });

  // ── Constructor / DI ──────────────────────────────────────────────────────
  constructor(
    readonly svc: Dashboardservice,
    private authService: AuthService,
    private cdr: ChangeDetectorRef,
  ) {}

  // ── Lifecycle ─────────────────────────────────────────────────────────────
  ngOnInit(): void {
    const studentId = this.authService.getCurrentUserId();
    this.svc.loadAll(studentId);
  }

  ngAfterViewInit(): void {
    // Initial chart render once view is ready (data may already be cached)
    this.buildLineChart();
    this.buildDonutChart();
  }

  ngOnDestroy(): void {
    this.lineChartInst?.destroy();
    this.donutChartInst?.destroy();
    this.dataEffect.destroy();
    this.chartEffect.destroy();
  }

  // ── Helpers ───────────────────────────────────────────────────────────────
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

  clearError(): void { this.svc.clearError(); }

  // ── Chart builders ────────────────────────────────────────────────────────
  private buildLineChart(): void {
    if (!this.lineChartRef?.nativeElement) return;
    if (this.lineChartInst) { this.lineChartInst.destroy(); this.lineChartInst = null; }

    const marks = this.svc.recentMarks();
    const labels = marks.length
      ? marks.map(m => m.feedbackDate?.slice(5) ?? '').reverse()
      : ['May 18', 'May 19', 'May 20', 'May 21', 'May 22', 'May 23', 'May 24', 'May 25'];
    const data = marks.length
      ? marks.map(m => Math.round((m.score / m.maxScore) * 100)).reverse()
      : [20, 35, 28, 55, 60, 72, 80, 85];

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
        plugins: { legend: { display: false } },
        scales: {
          y: {
            min: 0, max: 100,
            ticks: { callback: v => v + '%', font: { size: 10 }, color: '#888' },
            grid:   { color: '#f0f0f0' },
            border: { display: false },
          },
          x: {
            ticks: { font: { size: 10 }, color: '#888', maxRotation: 0 },
            grid:   { display: false },
            border: { display: false },
          },
        },
      },
    });
  }

  private buildDonutChart(): void {
    if (!this.donutChartRef?.nativeElement) return;
    if (this.donutChartInst) { this.donutChartInst.destroy(); this.donutChartInst = null; }

    const completed = this.completedAssignments();
    const remaining = this.remainingCount();

    this.donutChartInst = new Chart(this.donutChartRef.nativeElement, {
      type: 'doughnut',
      data: {
        labels: ['Completed', 'Remaining'],
        datasets: [{
          data: [completed || 24, remaining || 4],
          backgroundColor: ['#620000', '#e0e0e0'],
          borderWidth: 0,
          hoverOffset: 4,
        }],
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        cutout: '75%',
        plugins: {
          legend:  { display: false },
          tooltip: { enabled: true },
        },
      },
    });
  }
}