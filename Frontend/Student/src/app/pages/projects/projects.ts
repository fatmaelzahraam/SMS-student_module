import { CommonModule } from '@angular/common';
import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { ProjectMember, ProjectResponse } from '../../models/project-model';
import { ProjectsService } from './service/projects-service';
import {RouterLink} from '@angular/router';
import {Profileservice} from '../profile/service/profileservice';

@Component({
  selector: 'app-projects',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './projects.html',
  styleUrl: './projects.css',
})
export class Projects implements OnInit {
  protected readonly projectService = inject(ProjectsService);

  readonly profileService = inject(Profileservice);
  readonly profile = this.profileService.profile;

  getInitials(): string {
    return this.profileService.getInitials();
  }

  // Proxy service signals
  protected readonly isLoading = computed(() => this.projectService.isLoading());
  protected readonly error     = computed(() => this.projectService.error());
  protected readonly projects  = computed(() => this.projectService.projects());
  protected readonly hasProjects = computed(() => this.projectService.projects().length > 0);

  // Panel signals
  protected readonly teamProject   = signal<ProjectResponse | null>(null);
  protected readonly detailProject = signal<ProjectResponse | null>(null);

  protected readonly mockTeams: Record<number, ProjectMember[]> = {};

  ngOnInit(): void {
    this.projectService.loadAll();
  }

  protected teamFor(project: ProjectResponse): ProjectMember[] {
    if (!this.mockTeams[project.id]) {
      this.mockTeams[project.id] = [
        { name: 'Fatma Elzahraa Muhammed', role: 'Leader' },
        { name: 'Abdelrahman Hossam', role: 'Co-Leader' },
        { name: 'Loaa Walid' },
        { name: 'Ahmed Shabaan' },
        { name: 'Ahmed Hossam' },
      ];
    }
    return this.mockTeams[project.id];
  }

  // Panel actions
  protected openTeam(project: ProjectResponse): void {
    this.detailProject.set(null);
    this.teamProject.set(project);
  }

  protected openDetail(project: ProjectResponse): void {
    this.teamProject.set(null);
    this.detailProject.set(project);
  }

  protected closeAll(): void {
    this.teamProject.set(null);
    this.detailProject.set(null);
  }

  protected retry(): void {
    this.projectService.reload();
  }

  // Helpers
  protected formatDate(iso: string): string {
    const d = new Date(iso);
    return d.toLocaleDateString('en-GB', { day: 'numeric', month: 'short', year: 'numeric' });
  }

  protected trackById(_: number, p: ProjectResponse): number {
    return p.id;
  }
}
