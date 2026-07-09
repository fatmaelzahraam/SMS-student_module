import { CommonModule } from '@angular/common';
import { Component, inject, NgZone } from '@angular/core';
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from './service/auth-service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './login.html',
  styleUrls: ['./login.css'],
})
export class Login {
  private AuthService = inject(AuthService);
  private router      = inject(Router);
  private zone        = inject(NgZone);

  loginForm = new FormGroup({
    email:    new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required]),
  });

  submit() {
    this.loginForm.markAllAsTouched();
    if (!this.loginForm.valid) return;

    this.AuthService.authenticate({
      email:    this.loginForm.value.email!,
      password: this.loginForm.value.password!,
    }).subscribe({
      next: (value) => {
        console.log('Login response:', value);

        // ── Save session ──────────────────────────────
        localStorage.setItem('token',     value.token);
        localStorage.setItem('role',      value.role);
        localStorage.setItem('studentId', value.studentId?.toString() ?? '');

        console.log('studentId saved:', localStorage.getItem('studentId'));

        Swal.fire({
          title: 'Done!',
          text: 'Your action has been completed successfully.',
          icon: 'success',
          confirmButtonText: 'OK',
        }).then(() => {
          // ── Navigate inside Angular zone ──────────
          this.zone.run(() => {
            this.router.navigate(['/dashboard']);
          });
        });
      },
      error: (err) => {
        if (err.status === 401) {
          Swal.fire({
            title: 'Login Failed',
            text: 'The username or password you entered is incorrect. Please double-check and try again.',
            icon: 'error',
            confirmButtonText: 'Try again',
          });
        }
      },
    });
  }
}