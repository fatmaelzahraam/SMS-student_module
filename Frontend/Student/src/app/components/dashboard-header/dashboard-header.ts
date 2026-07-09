import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { Profileservice } from '../../pages/profile/service/profileservice';



@Component({
  selector: 'app-dashboard-header',
  imports: [CommonModule],
  templateUrl: './dashboard-header.html',
  styleUrl: './dashboard-header.css',
})
export class DashboardHeader {
  readonly profileService = inject(Profileservice);
  readonly profile        = this.profileService.profile;

  ngOnInit(): void {
    this.profileService.getProfile();
  }

  getInitials(): string {
    return this.profileService.getInitials();
  }

}
