import { HttpClient } from '@angular/common/http';
import { inject, Injectable, Signal, signal } from '@angular/core';
import { catchError, of } from 'rxjs';
import { CourseResponse } from '../../../models/courses-model';

@Injectable({
  providedIn: 'root',
})
export class CoursesService {
  private readonly http = inject(HttpClient);
  private readonly base = 'http://localhost:8080/api/v1/courses';

  // Signals that the component can bind to directly
  courses    = signal<CourseResponse[]>([]);
  isLoading  = signal(false);
  error      = signal<string | null>(null);

  loadAll(): void {
    this.isLoading.set(true);
    this.error.set(null);

    this.http.get<CourseResponse[]>(this.base).pipe(
      catchError(err => {
        console.error('Courses fetch failed:', err.status, err.message);
        this.error.set('Could not load courses. Please try again.');
        return of([] as CourseResponse[]);
      })
    ).subscribe(data => {
      this.courses.set(data);
      this.isLoading.set(false);
    });
  }

  getCourseById(id: number) {
    return this.http.get<CourseResponse>(`${this.base}/${id}`);
  }

  getCoursesByTeacher(teacherId: Signal<number | null>) {
    const id = teacherId();
    if (id === null) return;
    return this.http.get<CourseResponse[]>(`${this.base}/teacher/${id}`);
  }

  getCoursesByTerm(termId: Signal<number | null>) {
    const id = termId();
    if (id === null) return;
    return this.http.get<CourseResponse[]>(`${this.base}/term/${id}`);
  }

  getCoursesByType(courseType: Signal<string | null>) {
    const type = courseType();
    if (!type) return;
    return this.http.get<CourseResponse[]>(`${this.base}/type/${type}`);
  }

  reload(): void {
    this.loadAll();
  }
}