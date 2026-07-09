// schedule.ts
import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BREAK_SLOTS, TIME_SLOTS } from '../../models/session-inerface';
import { ScheduleService } from './service/schedule-service';
import { AuthService } from '../login/service/auth-service';

@Component({
  selector: 'app-schedule',
  standalone: true,
  imports: [CommonModule,],
  templateUrl: './schedule.html',
  styleUrl: './schedule.css',
})
export class Schedule implements OnInit {

  // ── Single inject, single name ────────────────────────────
  private readonly scheduleService = inject(ScheduleService);
  private readonly authService     = inject(AuthService);

  // ── Expose signals to template ────────────────────────────
  readonly classSchedule = this.scheduleService.classSchedule;
  readonly monthExams    = this.scheduleService.monthExams;
  readonly finalExams    = this.scheduleService.finalExams;
  readonly isLoading     = this.scheduleService.isLoading;
  readonly error         = this.scheduleService.error;


  activeTab = signal<'class' | 'month' | 'final'>('class');

  readonly timeSlots  = TIME_SLOTS;
  readonly breakSlots = new Set(BREAK_SLOTS);

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
}