import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AssignmentService } from './service/assignmentservice';
import { AssignmentResponse } from '../../models/assignment-response';
import { CoursesService } from '../courses/service/courses-service';
import { catchError, forkJoin, of, switchMap } from 'rxjs';


@Component({
  selector: 'app-assignments',
  standalone: true,
  imports: [CommonModule ],
  templateUrl: './assignments.html',
  styleUrls: ['./assignments.css']
})
export class assignments implements OnInit {
 assignments = signal<AssignmentResponse[]>([]);
  // map of courseId → courseName resolved after load
  courseNames = signal<Record<number, string>>({});
  isLoading   = signal(true);
  error       = signal<string | null>(null);
 
  constructor(
    private assignmentService: AssignmentService,
    private courseService: CoursesService
  ) {}
 
  ngOnInit(): void {
    this.assignmentService.getAssignments().pipe(
      switchMap((assignments) => {
        this.assignments.set(assignments);
 
        // Collect every unique courseId across all assignments
        const uniqueIds = [
          ...new Set(assignments.flatMap(a => a.courseIds ?? []))
        ];
 
        if (uniqueIds.length === 0) return of({});
 
        // Fetch each course by id in parallel
        const requests = uniqueIds.reduce((acc, id) => {
          acc[id] = this.courseService.getCourseById(id).pipe(
            catchError(() => of(null))   // don't break if one course 404s
          );
          return acc;
        }, {} as Record<number, any>);
 
        return forkJoin(requests);
      })
    ).subscribe({
      next: (results: any) => {
        // Build courseId → courseName lookup
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
 
  /** Resolve a courseId to its name, fallback to the id if not loaded yet */
  getCourseName(courseId: number): string {
    return this.courseNames()[courseId] ?? `Course ${courseId}`;
  }
 
  /** Open the assignment's file link in a new tab */
  viewAssignment(item: AssignmentResponse): void {
    if (item.fileLink) {
      window.open(item.fileLink, '_blank');
    } else {
      console.warn('No file link found for assignment:', item.id);
    }
  }
 
  /** Returns true if the deadline is within 3 days — turns the View button burgundy */
  isUrgent(deadline: string): boolean {
    if (!deadline) return false;
    const diff = new Date(deadline).getTime() - Date.now();
    return diff >= 0 && diff <= 3 * 24 * 60 * 60 * 1000;
  }
}
