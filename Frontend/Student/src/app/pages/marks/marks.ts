import { Component, computed, effect, ElementRef, inject, signal, viewChild } from '@angular/core';
import { ArcElement, Chart, DoughnutController, Legend, Tooltip } from 'chart.js';
import { StudentMarksDashboard } from '../../models/marks-model';
import { MarksService } from './service/marks-service';
import { AuthService } from '../login/service/auth-service';
import { Profileservice } from '../profile/service/profileservice';
import { DecimalPipe } from '@angular/common';

Chart.register(ArcElement, DoughnutController, Tooltip, Legend);

interface TableRow {
  month: string;
  values: (number | null)[];
}

const SUBJECT_COLORS = [
  '#2f6fed',
  '#17a2a2',
  '#f2a33c',
  '#8a3fd1',
  '#d63447',
  '#21c3a6',
  '#8a8f98',
  '#4c6ef5',
];

@Component({
  selector: 'app-marks',
  imports:[DecimalPipe],
  templateUrl: './marks.html',
  styleUrl: './marks.css',
})
export class Marks {
  private marksService = inject(MarksService);
  private authService = inject(AuthService);
  private studentId: number;

  readonly profileService = inject(Profileservice);
  readonly profile = this.profileService.profile;

  getInitials(): string {
    return this.profileService.getInitials();
  }

  dashboard = signal<StudentMarksDashboard | null>(null);
  loading = signal(true);
  error = signal<string | null>(null);

  // Filter by subject (client-side)
  selectedSubject = signal<{ courseId: number | null; subject: string | null }>({
    courseId: null,
    subject: null,
  });

  // Filter by mark type (server-side re-fetch)
  selectedType = signal<string | null>(null);

  columns = computed(() => {
    const data = this.dashboard();
    if (!data) return [];

    const { courseId, subject } = this.selectedSubject();

    return courseId
      ? data.subjectAverages.filter((s) => s.courseId === courseId).map((s) => s.subject)
      : subject
        ? data.subjectAverages.filter((s) => s.subject === subject).map((s) => s.subject)
        : data.subjectAverages.map((s) => s.subject);
  });

  rows = computed<TableRow[]>(() => {
    const data = this.dashboard();
    if (!data) return [];

    const { courseId, subject } = this.selectedSubject();

    const cols = courseId
      ? data.subjectAverages.filter((s) => s.courseId === courseId).map((s) => s.subject)
      : subject
        ? data.subjectAverages.filter((s) => s.subject === subject).map((s) => s.subject)
        : data.subjectAverages.map((s) => s.subject);

    return data.monthlyMarks.map((m) => {
      const lookup = new Map(m.subjects.map((s) => [s.subject, s.percentage]));
      return {
        month: m.month,
        values: cols.map((col) => lookup.get(col) ?? null),
      };
    });
  });

  private donutCanvasRef = viewChild<ElementRef<HTMLCanvasElement>>('donutCanvas');
  private chart?: Chart;

  constructor() {
    this.studentId = this.authService.getCurrentUserId();

    // Re-fetch from backend when type filter changes
    effect(() => {
      const type = this.selectedType();
      this.fetchDashboard(this.studentId, type ?? undefined);
    });

    // Rebuild chart when dashboard or subject filter changes
    effect(() => {
      const data = this.dashboard();
      const canvasEl = this.donutCanvasRef()?.nativeElement;

      if (!data || !canvasEl) {
        this.chart?.destroy();
        this.chart = undefined;
        return;
      }

      const { courseId, subject } = this.selectedSubject();

      const filtered = courseId
        ? data.subjectAverages.filter((s) => s.courseId === courseId)
        : subject
          ? data.subjectAverages.filter((s) => s.subject === subject)
          : data.subjectAverages;

      this.chart?.destroy();
      this.chart = new Chart(canvasEl, {
        type: 'doughnut',
        data: {
          labels: filtered.map((d) => d.subject),
          datasets: [
            {
              data: filtered.map((d) => d.percentage),
              backgroundColor: filtered.map((_, i) => this.colorFor(i)),
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
      this.selectedSubject.set({ courseId: null, subject: null });
      return;
    }
    const parsed = Number(value);
    if (!Number.isNaN(parsed)) {
      this.selectedSubject.set({ courseId: parsed, subject: null });
    } else {
      this.selectedSubject.set({ courseId: null, subject: value });
    }
  }

  onTypeChange(event: Event): void {
    const value = (event.target as HTMLSelectElement).value;
    this.selectedType.set(value === 'all' ? null : value);
  }

  private fetchDashboard(studentId: number, typeName?: string): void {
    this.loading.set(true);
    this.error.set(null);

    this.marksService.getDashboard(studentId, typeName).subscribe({
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