import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-attendance-nav',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './attendance-nav.html',
  styleUrl: './attendance-nav.css',
})
export class AttendanceNav {}
