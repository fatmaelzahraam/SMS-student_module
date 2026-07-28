import {Component, inject} from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import {Profileservice} from '../../pages/profile/service/profileservice';

@Component({
  selector: 'app-attendance-nav',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './attendance-nav.html',
  styleUrl: './attendance-nav.css',
})
export class AttendanceNav {
  readonly profileService = inject(Profileservice);
  readonly profile = this.profileService.profile;

  getInitials(): string {
    return this.profileService.getInitials();
  }
}
