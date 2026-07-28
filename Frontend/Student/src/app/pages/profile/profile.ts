import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { Profileservice } from './service/profileservice';
import { RouterLink } from '@angular/router';
import { StudentProfileResponse } from '../../models/student-profile-response';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, DatePipe, RouterLink],
  templateUrl: './profile.html',
  styleUrl: './profile.css',
})
export class Profile implements OnInit {
  studentData: StudentProfileResponse | null = null;
  loading = true;
  error: string | null = null;
  profileImage!: typeof this.studentService.profileImage;

  constructor(public studentService: Profileservice) {
    // assign after studentService is initialized
    this.profileImage = this.studentService.profileImage;
  }

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

  onImageSelected(event: Event): void {
    const file = (event.target as HTMLInputElement).files?.[0];
    if (!file) return;

    // guard: images only
    if (!file.type.startsWith('image/')) {
      alert('Please select an image file.');
      return;
    }

    // guard: max 2 MB
    if (file.size > 2 * 1024 * 1024) {
      alert('Image must be under 2 MB.');
      return;
    }

    const reader = new FileReader();
    reader.onload = () => {
      this.studentService.saveProfileImage(reader.result as string);
    };
    reader.readAsDataURL(file);
  }

  removeImage(): void {
    this.studentService.clearProfileImage();
  }
}