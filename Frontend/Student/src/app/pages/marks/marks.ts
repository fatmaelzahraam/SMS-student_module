import {
  Component,
  ElementRef,
  computed,
  effect,
  inject,
  signal,
  viewChild,
} from '@angular/core';
import { ArcElement, Chart, DoughnutController, Legend, Tooltip } from 'chart.js';
import { StudentMarksDashboard } from '../../models/marks-model';
import { MarksService } from './service/marks-service';
import { AuthService } from '../login/service/auth-service';

Chart.register(ArcElement, DoughnutController, Tooltip, Legend);

interface TableRow {
  month: string;
  values: (number | null)[];
}

const SUBJECT_COLORS = [
  '#2f6fed', // blue
  '#17a2a2', // teal
  '#f2a33c', // orange
  '#8a3fd1', // purple
  '#d63447', // red
  '#21c3a6', // mint
  '#8a8f98', // gray
  '#4c6ef5', // indigo (overflow)
];


@Component({
  selector: 'app-marks',
  templateUrl: './marks.html',
  styleUrl: './marks.css',
})
export class Marks {
  private marksService = inject(MarksService);
  private authService  = inject(AuthService);
  private studentId: number;

  dashboard = signal<StudentMarksDashboard | null>(null);
  loading = signal(true);
  error = signal<string | null>(null);
  selectedCourseId = signal<number | null>(null);

  columns = computed(() => this.dashboard()?.subjectAverages.map((s) => s.subject) ?? []);

  rows = computed<TableRow[]>(() => {
    const data = this.dashboard();
    if (!data) return [];
    return data.monthlyMarks.map((row) => {
      const lookup = new Map(row.subjects.map((s) => [s.subject, s.percentage]));
      return {
        month: row.month,
        values: this.columns().map((col) => lookup.get(col) ?? null),
      };
    });
  });

  private donutCanvasRef = viewChild<ElementRef<HTMLCanvasElement>>('donutCanvas');
  private chart?: Chart;

  constructor() {
    this.studentId = this.authService.getCurrentUserId();

    effect(() => {
      this.fetchDashboard(this.studentId, this.selectedCourseId());
    });

    effect(() => {
      const data = this.dashboard();
      const canvasEl = this.donutCanvasRef()?.nativeElement;

      if (!data || !canvasEl) {
        this.chart?.destroy();
        this.chart = undefined;
        return;
      }

      this.chart?.destroy();
      this.chart = new Chart(canvasEl, {
        type: 'doughnut',
        data: {
          labels: data.subjectAverages.map((d) => d.subject),
          datasets: [
            {
              data: data.subjectAverages.map((d) => d.percentage),
              backgroundColor: data.subjectAverages.map((_, i) => this.colorFor(i)),
              borderWidth: 0,
              hoverOffset: 6,
            },
          ],
        },
        options: {
          cutout: '68%',
          maintainAspectRatio: false,
          plugins: {
            legend: { display: false },
            tooltip: { callbacks: { label: (ctx) => `${ctx.label}: ${ctx.parsed}%` } },
          },
        },
      });
    });
  }

  colorFor(index: number): string {
    return SUBJECT_COLORS[index % SUBJECT_COLORS.length];
  }

  onSubjectChange(event: Event): void {
    const value = (event.target as HTMLSelectElement).value;
    if (value === 'all') {
      this.selectedCourseId.set(null);
      return;
    }
    const parsed = Number(value);
    this.selectedCourseId.set(Number.isNaN(parsed) ? null : parsed);
  }

  private fetchDashboard(studentId: number, courseId: number | null): void {
    this.loading.set(true);
    this.error.set(null);

    this.marksService.getDashboard(studentId).subscribe({
      next: (data) => {
        this.dashboard.set(data);
        this.loading.set(false);
      },
      error: () => {
        this.error.set('Could not load marks right now. Please try again.');
        this.loading.set(false);
      },
    });
  }
}