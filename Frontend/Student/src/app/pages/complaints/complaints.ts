import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { ComplaintResponse } from '../../models/ComplaintResponse';
import { ComplaintDetailsResponse } from '../../models/ComplaintDetailsResponse';
import { ComplaintStatisticsResponse } from '../../models/ComplaintStatisticsResponse';
import { CreateComplaintRequest } from '../../models/CreateComplaintRequest';
import { ComplaintsService } from './service/complaints';



@Component({
  selector: 'app-complaints',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterLink,
    RouterLinkActive
  ],
  templateUrl: './complaints.html',
  styleUrls: ['./complaints.css']
})
export class Complaints implements OnInit {

  complaints: ComplaintResponse[] = [];

  selectedComplaint?: ComplaintDetailsResponse;

  statistics?: ComplaintStatisticsResponse;

  showNewComplaint = false;

  loading = false;

  newComplaint: CreateComplaintRequest = {
    title: '',
    description: ''
  };

  constructor(
    private complaintsService: ComplaintsService
  ) {}

  ngOnInit(): void {
    this.loadStatistics();
    this.loadComplaints();
  }

  loadComplaints(): void {

    this.complaintsService.getMyComplaints().subscribe({

      next: (data) => {

        this.complaints = data;

        if (this.complaints.length > 0) {

          this.selectComplaint(this.complaints[0].complaintId);

        }

      },

      error: (err) => {

        console.error(err);

      }

    });

  }

  loadStatistics(): void {

    this.complaintsService.getStatistics().subscribe({

      next: (data) => {

        this.statistics = data;

      },

      error: (err) => {

        console.error(err);

      }

    });

  }

  selectComplaint(id: number): void {

    this.showNewComplaint = false;

    this.complaintsService.getComplaintDetails(id).subscribe({

      next: (data) => {

        this.selectedComplaint = data;

      },

      error: (err) => {

        console.error(err);

      }

    });

  }

  openNewComplaint(): void {

    this.showNewComplaint = true;

    this.selectedComplaint = undefined;

    this.newComplaint = {
      title: '',
      description: ''
    };

  }

  closeNewComplaint(): void {

    this.showNewComplaint = false;

    this.newComplaint = {
      title: '',
      description: ''
    };

  }

  cancelComplaint(): void {

    this.closeNewComplaint();

  }

  submitComplaint(): void {

    if (
      !this.newComplaint.title.trim() ||
      !this.newComplaint.description.trim()
    ) {
      return;
    }

    this.loading = true;

    this.complaintsService.createComplaint(this.newComplaint).subscribe({

      next: () => {

        this.loading = false;

        this.closeNewComplaint();

        this.loadComplaints();

        this.loadStatistics();

      },

      error: (err) => {

        console.error(err);

        this.loading = false;

      }

    });

  }

}
