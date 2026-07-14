import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BREAK_SLOTS, ExamEntry, TIME_SLOTS } from '../../models/session-inerface';
import { ScheduleService } from './service/schedule-service';
import { AuthService } from '../login/service/auth-service';

// pill color assignment per subject keyword
const PILL_COLORS = [
  'pill-blue', 'pill-pink', 'pill-gray',
];

interface EnrichedExam extends ExamEntry {
  duration: string;
  from:     string;
  to:       string;
  notes:    string;
}

interface ExamGroup {
  date:  string;
  day:   string;
  exams: EnrichedExam[];   
}

@Component({
  selector: 'app-schedule',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './schedule.html',
  styleUrl: './schedule.css',
})
export class Schedule implements OnInit {

  private readonly scheduleService = inject(ScheduleService);
  private readonly authService     = inject(AuthService);

  readonly classSchedule = this.scheduleService.classSchedule;
  readonly monthExams    = this.scheduleService.monthExams;
  readonly finalExams    = this.scheduleService.finalExams;
  readonly isLoading     = this.scheduleService.isLoading;
  readonly error         = this.scheduleService.error;

  activeTab = signal<'class' | 'month' | 'final'>('class');

  readonly timeSlots  = TIME_SLOTS;
  readonly breakSlots = new Set(BREAK_SLOTS);

  // maps subject name → consistent pill color
  private subjectColorMap = new Map<string, string>();
  private colorIndex = 0;

  ngOnInit(): void {
    const studentId = this.authService.getCurrentUserId();
    this.scheduleService.loadAll(studentId);
  }

  isBreak(slot: string): boolean {
    return this.breakSlots.has(slot);
  }

  setTab(tab: 'class' | 'month' | 'final'): void {
    this.activeTab.set(tab);
  }

  clearError(): void {
    this.scheduleService.clearError();
  }

  // Assign a card class based on slot index (cycles blue/pink/gray)
  getCardClass(slotIndex: number): string {
    const classes = ['card-blue', 'card-pink', 'card-gray'];
    return classes[slotIndex % classes.length];
  }

  // Assign a consistent pill color per subject name
  getPillClass(subject: string): string {
    if (!this.subjectColorMap.has(subject)){
      this.subjectColorMap.set(
        subject,
        PILL_COLORS[this.colorIndex++ % PILL_COLORS.length]
      );
    }
    return this.subjectColorMap.get(subject)!;
  }

  groupedMonthExams = computed<ExamGroup[]>(() => {
  const exams = this.monthExams()?.exams ?? [];
  return this.groupByDate(exams);
});

groupedFinalExams = computed<ExamGroup[]>(() => {
  const exams = this.finalExams()?.exams ?? [];
  return this.groupByDate(exams);
});
private groupByDate(exams: ExamEntry[]): ExamGroup[] {
  const map = new Map<string, ExamGroup>();

  for (const exam of exams) {
    const dateKey  = exam.examDate ?? 'Unknown';
    const dateObj  = dateKey !== 'Unknown' ? new Date(dateKey) : null;

    const dateLabel = dateObj
      ? dateObj.toLocaleDateString('en-GB', { day: 'numeric', month: 'short', year: 'numeric' })
      : dateKey;

    const dayLabel = dateObj
      ? dateObj.toLocaleDateString('en-US', { weekday: 'long' })
      : '';

    const from     = this.formatTime(exam.startTime);
    const to       = this.formatTime(exam.endTime);
    const duration = this.calcDuration(exam.startTime, exam.endTime);

    if (!map.has(dateKey)) {
      map.set(dateKey, { date: dateLabel, day: dayLabel, exams: [] });
    }

    const enriched: EnrichedExam = { ...exam, from, to, duration, notes: '' };
    map.get(dateKey)!.exams.push(enriched);
  }

  return Array.from(map.values());
}

  private calcDuration(start: string, end: string): string {
    if (!start || !end) return '';

    const [sh, sm] = start.split(':').map(Number);
    const [eh, em] = end.split(':').map(Number);

    const totalMinutes = (eh * 60 + em) - (sh * 60 + sm);
    const hours   = Math.floor(totalMinutes / 60);
    const minutes = totalMinutes % 60;

    if (minutes === 0) {
      return hours === 1 ? '1 Hour' : `${hours} Hours`;
    }
    if (hours === 0) {
      return `${minutes} Min`;
    }
    return `${hours} Hour${hours > 1 ? 's' : ''} & ${minutes} Min`;
  }


  private formatTime(time: string | undefined): string {
    if (!time) return '';
    // If it's "08:00:00" strip seconds
    const parts = time.split(':');
    if (parts.length >= 2) {
      const h = parseInt(parts[0], 10);
      const m = parts[1];
      return `${h}:${m}`;
    }
    return time;
  }
}