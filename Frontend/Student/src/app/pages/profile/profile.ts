import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { Profileservice } from './service/profileservice';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, DatePipe], 
  templateUrl: './profile.html',
  styleUrl: './profile.css',
})
export class Profile implements OnInit {
  studentData: any = null;
  loading = true;
  error: string | null = null;

  constructor(private studentService: Profileservice) {}
  

  ngOnInit(): void {
    this.studentService.getProfile().subscribe({
      next: (data) => {
        this.studentData = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error loading profile', err);
        this.error = 'Failed to load profile.';
        this.loading = false;
      }
    });
  }
}