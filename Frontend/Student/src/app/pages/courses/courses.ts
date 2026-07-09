import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CoursesService } from './service/courses-service';
import { CourseResponse } from '../../models/courses-model';
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-courses',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './courses.html',
  styleUrl: './courses.css',
})
export class Courses implements OnInit {
  protected readonly courseService = inject(CoursesService);

  // Proxy the service signals directly
  protected readonly isLoading  = computed(() => this.courseService.isLoading());
  protected readonly error      = computed(() => this.courseService.error());

  // Local UI signals
  protected readonly searchQuery    = signal('');
  protected readonly showPrevious   = signal(false);
  protected readonly selectedCourse = signal<CourseResponse | null>(null);

  // Split current vs previous terms
  private readonly latestYear = computed(() => {
    const data = this.courseService.courses();
    const years = data.map(c => c.termYear);
    return years.length ? Math.max(...years) : null;
  });

  protected readonly currentCourses = computed(() =>
    this.courseService.courses().filter(
      c => c.termYear === this.latestYear() && this.matchesSearch(c)
    )
  );

  protected readonly previousCourses = computed(() =>
    this.courseService.courses().filter(
      c => c.termYear !== this.latestYear() && this.matchesSearch(c)
    )
  );

  protected readonly visibleCourses = computed(() =>
    this.showPrevious() ? this.previousCourses() : this.currentCourses()
  );

  protected readonly hasCourses = computed(() =>
    this.courseService.courses().length > 0
  );

  ngOnInit(): void {
    this.courseService.loadAll();
  }

  // Actions
  protected onSearch(event: Event): void {
    this.searchQuery.set((event.target as HTMLInputElement).value);
  }

  protected togglePrevious(): void {
    this.showPrevious.update(v => !v);
  }

  protected openDetail(course: CourseResponse): void {
    this.selectedCourse.set(course);
  }

  protected closeDetail(): void {
    this.selectedCourse.set(null);
  }

  protected retry(): void {
    this.courseService.reload();
  }

  // Helpers
  private matchesSearch(c: CourseResponse): boolean {
    const q = this.searchQuery().toLowerCase();
    return (
      !q ||
      c.courseName.toLowerCase().includes(q) ||
      c.courseType.toLowerCase().includes(q) ||
      c.teacherName.toLowerCase().includes(q)
    );
  }

  protected iconFor(courseType: string): string {
    const t = courseType.toLowerCase();
    if (t.includes('mobile') || t.includes('flutter')) return 'assets/icons/flutter.svg';
    if (t.includes('java'))                              return 'assets/icons/java.svg';
    if (t.includes('database') || t.includes('sql'))    return 'assets/icons/sql.svg';
    if (t.includes('web'))                               return 'assets/icons/web.svg';
    return 'assets/icons/book.svg';
  }

  protected initials(name: string): string {
    return name.split(' ').slice(0, 2).map(w => w[0]).join('').toUpperCase();
  }

  protected lessonCount(course: CourseResponse): number {
    return course.assignments?.length ?? 0;
  }

  protected trackById(_: number, c: CourseResponse): number {
    return c.id;
  }
}