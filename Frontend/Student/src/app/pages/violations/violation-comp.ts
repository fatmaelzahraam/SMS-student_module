import { Component, inject, OnInit, ChangeDetectorRef } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { ViolationResponse } from '../../models/ViolationResponse';
import { ViolationDetailsResponse } from '../../models/ViolationDetailsResponse';
import { ViolationStatisticsResponse } from '../../models/ViolationStatisticsResponse';
import { ViolationsService } from './service/violations.service';
import { Profileservice } from '../profile/service/profileservice';

@Component({
  selector: 'app-violations',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './violation-comp.html',
  styleUrls: ['./violation-comp.css']
})
export class Violations implements OnInit {

  readonly profileService = inject(Profileservice);
  readonly profile        = this.profileService.profile;

  getInitials(): string { return this.profileService.getInitials(); }

  violations: ViolationResponse[]       = [];
  selectedViolation?: ViolationDetailsResponse;
  statistics?: ViolationStatisticsResponse;
  loading = false;

  constructor(
    private violationsService: ViolationsService,
    private cd: ChangeDetectorRef          
  ) {}

  ngOnInit(): void {
    this.loadStatistics();
    this.loadViolations();
  }

  loadViolations(): void {
    this.loading = true;
    this.violationsService.getMyViolations().subscribe({
      next: (data) => {
        this.violations = data;
        if (this.violations.length > 0) {
          this.selectViolation(this.violations[0].violationId);
        }
        this.loading = false;
        this.cd.detectChanges();           
      },
      error: (err) => {
        console.error(err);
        this.loading = false;
        this.cd.detectChanges();           
      }
    });
  }

  loadStatistics(): void {
    this.violationsService.getStatistics().subscribe({
      next: (data) => {
        this.statistics = data;
        this.cd.detectChanges();           
      },
      error: (err) => console.error(err)
    });
  }

  selectViolation(id: number): void {
    this.violationsService.getViolationDetails(id).subscribe({
      next: (data) => {
        this.selectedViolation = data;
        this.cd.detectChanges();           
      },
      error: (err) => console.error(err)
    });
  }
}