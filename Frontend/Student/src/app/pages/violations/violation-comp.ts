import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { ViolationResponse } from '../../models/ViolationResponse';
import { ViolationDetailsResponse } from '../../models/ViolationDetailsResponse';
import { ViolationStatisticsResponse } from '../../models/ViolationStatisticsResponse';
import { ViolationsService } from './service/violations.service';



@Component({
  selector: 'app-violations',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    RouterLinkActive
  ],
  templateUrl: './violation-comp.html',
  styleUrls: ['./violation-comp.css']
})
export class Violations implements OnInit {

  violations: ViolationResponse[] = [];

  selectedViolation?: ViolationDetailsResponse;

  statistics?: ViolationStatisticsResponse;

  loading = false;

  constructor(
    private violationsService: ViolationsService
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

      },

      error: (err) => {

        console.error(err);

        this.loading = false;

      }

    });

  }

  loadStatistics(): void {

    this.violationsService.getStatistics().subscribe({

      next: (data) => {

        this.statistics = data;

      },

      error: (err) => {

        console.error(err);

      }

    });

  }

  selectViolation(id: number): void {

    this.violationsService.getViolationDetails(id).subscribe({

      next: (data) => {

        this.selectedViolation = data;

      },

      error: (err) => {

        console.error(err);

      }

    });

  }

}
