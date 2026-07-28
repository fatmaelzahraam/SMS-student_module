import { CommonModule } from '@angular/common';
import { Component, inject, NgZone, signal } from '@angular/core';
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from './service/auth-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './login.html',
  styleUrls: ['./login.css'],
})
export class Login {
  private authService = inject(AuthService);
  private router      = inject(Router);
  private zone        = inject(NgZone);

  showPassword = signal(false);
  loginError   = signal('');

  loginForm = new FormGroup({
    email:    new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required]),
  });

  togglePassword() {
    this.showPassword.update(v => !v);
  }

  submit() {
    this.loginForm.markAllAsTouched();
    this.loginError.set('');
    if (!this.loginForm.valid) return;

    this.authService.authenticate({
      email:    this.loginForm.value.email!,
      password: this.loginForm.value.password!,
    }).subscribe({
      next: (value) => {
        localStorage.setItem('token',     value.token);
        localStorage.setItem('role',      value.role);
        localStorage.setItem('studentId', value.studentId?.toString() ?? '');

        this.zone.run(() => {
          this.router.navigate(['/dashboard']);
        });
      },
      error: (err) => {
        if (err.status === 401) {
          this.loginError.set('Incorrect email or password. Please try again.');
        } else {
          this.loginError.set('Something went wrong. Please try again later.');
        }
      },
    });
  }
}