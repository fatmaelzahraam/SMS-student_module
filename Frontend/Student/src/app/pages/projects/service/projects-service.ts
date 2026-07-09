import { HttpClient } from '@angular/common/http';
import { inject, Injectable, Signal, signal } from '@angular/core';
import { catchError, of } from 'rxjs';
import { ProjectResponse } from '../../../models/project-model';

@Injectable({
  providedIn: 'root',
})
export class ProjectsService {
  private readonly http = inject(HttpClient);
  private readonly base = 'http://localhost:8080/api/v1/projects';

  projects  = signal<ProjectResponse[]>([]);
  isLoading = signal(false);
  error     = signal<string | null>(null);

  loadAll(): void {
    this.isLoading.set(true);
    this.error.set(null);

    this.http.get<ProjectResponse[]>(this.base).pipe(
      catchError(err => {
        console.error('Projects fetch failed:', err.status, err.message);
        this.error.set('Could not load projects. Please try again.');
        return of([] as ProjectResponse[]);
      })
    ).subscribe(data => {
      this.projects.set(data);
      this.isLoading.set(false);
    });
  }

  getById(id: number) {
    return this.http.get<ProjectResponse>(`${this.base}/${id}`);
  }

  getByCourse(courseId: number) {
    return this.http.get<ProjectResponse[]>(`${this.base}/course/${courseId}`);
  }

  reload(): void {
    this.loadAll();
  }
}