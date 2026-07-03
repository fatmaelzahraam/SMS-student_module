import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Authentication } from '../../services/authentication';


@Component({
  selector: 'app-change-password',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './change-password.html',
  styleUrl: './change-password.css'
})
export class ChangPassword {

  currentPassword = '';
  newPassword = '';
  confirmPassword = '';

  constructor(private authService: Authentication) {}

  updatePassword() {

    const body = {
      currentPassword: this.currentPassword,
      newPassword: this.newPassword,
      confirmPassword: this.confirmPassword
    };

    this.authService.changePassword(body).subscribe({
      next: (res) => {
        console.log(res);
        alert('Password Updated Successfully');
      },
      error: (err) => {
        console.log(err);
        alert(err.error?.message || 'Error');
      }
    });

  }
}