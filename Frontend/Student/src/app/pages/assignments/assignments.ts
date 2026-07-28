import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AssignmentService } from './service/assignmentservice';
import { AssignmentResponse } from '../../models/assignment-response';
import { CoursesService } from '../courses/service/courses-service';
import { catchError, forkJoin, of, switchMap } from 'rxjs';
import { RouterLink } from '@angular/router';
import { Profileservice } from '../profile/service/profileservice';

const PAGE_SIZE = 5;

@Component({
  selector: 'app-assignments',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './assignments.html',
  styleUrls: ['./assignments.css']
})
export class assignments implements OnInit {

  readonly profileService = inject(Profileservice);
  readonly profile = this.profileService.profile;

  getInitials(): string {
    return this.profileService.getInitials();
  }

  assignments = signal<AssignmentResponse[]>([]);
  courseNames = signal<Record<number, string>>({});
  isLoading   = signal(true);
  error       = signal<string | null>(null);

  // ── Details modal ────────────────────────────────────────────
  selectedAssignment = signal<AssignmentResponse | null>(null);

  openDetails(item: AssignmentResponse): void {
    this.selectedAssignment.set(item);
  }

  closeDetails(): void {
    this.selectedAssignment.set(null);
  }

  /** Close modal when the backdrop is clicked */
  onBackdropClick(event: MouseEvent): void {
    if ((event.target as HTMLElement).classList.contains('modal-backdrop')) {
      this.closeDetails();
    }
  }

  // ── Filtering: current year only ────────────────────────────
  currentYearAssignments = computed(() => {
    const currentYear = new Date().getFullYear();
    return this.assignments().filter(a => {
      if (!a.deadline) return false;
      return new Date(a.deadline).getFullYear() === currentYear;
    });
  });

  // ── Sort by deadline ascending ───────────────────────────────
  sortedAssignments = computed(() =>
    [...this.currentYearAssignments()].sort((a, b) => {
      if (!a.deadline) return 1;
      if (!b.deadline) return -1;
      return new Date(a.deadline).getTime() - new Date(b.deadline).getTime();
    })
  );

  // ── Pagination ───────────────────────────────────────────────
  currentPage = signal(1);
  readonly pageSize = PAGE_SIZE;

  totalPages = computed(() =>
    Math.max(1, Math.ceil(this.sortedAssignments().length / PAGE_SIZE))
  );

  pagedAssignments = computed(() => {
    const page  = this.currentPage();
    const start = (page - 1) * PAGE_SIZE;
    return this.sortedAssignments().slice(start, start + PAGE_SIZE);
  });

  pageNumbers = computed(() =>
    Array.from({ length: this.totalPages() }, (_, i) => i + 1)
  );

  constructor(
    private assignmentService: AssignmentService,
    private courseService: CoursesService
  ) {}

  ngOnInit(): void {
    this.assignmentService.getAssignments().pipe(
      switchMap((assignments) => {
        this.assignments.set(assignments);

        const uniqueIds = [
          ...new Set(assignments.flatMap(a => a.courseIds ?? []))
        ];

        if (uniqueIds.length === 0) return of({});

        const requests = uniqueIds.reduce((acc, id) => {
          acc[id] = this.courseService.getCourseById(id).pipe(
            catchError(() => of(null))
          );
          return acc;
        }, {} as Record<number, any>);

        return forkJoin(requests);
      })
    ).subscribe({
      next: (results: any) => {
        const names: Record<number, string> = {};
        for (const [id, course] of Object.entries(results)) {
          if (course) names[+id] = (course as any).courseName;
        }
        this.courseNames.set(names);
        this.isLoading.set(false);
      },
      error: (err) => {
        console.error('Failed to load assignments:', err);
        this.error.set('Failed to load assignments. Please try again.');
        this.isLoading.set(false);
      }
    });
  }

  getCourseName(courseId: number): string {
    return this.courseNames()[courseId] ?? `Course ${courseId}`;
  }

  /** Open the file link in a new tab */
  viewAssignment(item: AssignmentResponse): void {
    if (item.fileLink) {
      window.open(item.fileLink, '_blank');
    } else {
      console.warn('No file link found for assignment:', item.id);
    }
  }

  isUrgent(deadline: string): boolean {
    if (!deadline) return false;
    const diff = new Date(deadline).getTime() - Date.now();
    return diff >= 0 && diff <= 3 * 24 * 60 * 60 * 1000;
  }

  // ── Pagination helpers ────────────────────────────────────────
  goToPage(page: number): void {
    if (page >= 1 && page <= this.totalPages()) {
      this.currentPage.set(page);
    }
  }

  prevPage(): void { this.goToPage(this.currentPage() - 1); }
  nextPage(): void { this.goToPage(this.currentPage() + 1); }
}