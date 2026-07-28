import { Component, inject, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule, NgModel } from '@angular/forms';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { ComplaintResponse } from '../../models/ComplaintResponse';
import { ComplaintDetailsResponse } from '../../models/ComplaintDetailsResponse';
import { ComplaintStatisticsResponse } from '../../models/ComplaintStatisticsResponse';
import { CreateComplaintRequest } from '../../models/CreateComplaintRequest';
import { ComplaintsService } from './service/complaints';
import { Profileservice } from '../profile/service/profileservice';

@Component({
  selector: 'app-complaints',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, DatePipe, RouterLinkActive],
  templateUrl: './complaints.html',
  styleUrls: ['./complaints.css']
})
export class Complaints implements OnInit {

  readonly profileService = inject(Profileservice);
  readonly profile        = this.profileService.profile;

  getInitials(): string { return this.profileService.getInitials(); }

  complaints: ComplaintResponse[]       = [];
  selectedComplaint?: ComplaintDetailsResponse;
  statistics?: ComplaintStatisticsResponse;
  showNewComplaint = false;
  loading          = false;
  errorMessage: string | null = null;

  newComplaint: CreateComplaintRequest = { title: '', description: '' };

  constructor(
    private complaintsService: ComplaintsService,
    private cd: ChangeDetectorRef          
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
        this.cd.detectChanges();           
      },
      error: (err) => console.error(err)
    });
  }

  loadStatistics(): void {
    this.complaintsService.getStatistics().subscribe({
      next: (data) => {
        this.statistics = data;
        this.cd.detectChanges();           
      },
      error: (err) => console.error(err)
    });
  }

  selectComplaint(id: number): void {
    this.showNewComplaint = false;
    this.complaintsService.getComplaintDetails(id).subscribe({
      next: (data) => {
        this.selectedComplaint = data;
        this.cd.detectChanges();           
      },
      error: (err) => console.error(err)
    });
  }

  openNewComplaint(): void {
    this.showNewComplaint = true;
    this.selectedComplaint = undefined;
    this.errorMessage      = null;
    this.newComplaint      = { title: '', description: '' };
  }

  closeNewComplaint(): void {
    this.showNewComplaint = false;
    this.errorMessage     = null;
    this.newComplaint     = { title: '', description: '' };
  }

  cancelComplaint(): void { this.closeNewComplaint(); }

  submitComplaint(titleField: NgModel, descField: NgModel): void {
    titleField.control.markAsTouched();
    descField.control.markAsTouched();

    if (titleField.invalid || descField.invalid) return;
    if (!this.newComplaint.title.trim() || !this.newComplaint.description.trim()) return;

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
        this.errorMessage = 'Failed to submit complaint. Please try again.';
        this.cd.detectChanges();           
      }
    });
  }
}