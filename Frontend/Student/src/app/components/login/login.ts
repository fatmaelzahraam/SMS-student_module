import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Authentication } from '../../services/authentication';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,                         
  imports: [ReactiveFormsModule, CommonModule , RouterModule], 
  templateUrl: './login.html',
  styleUrls: ['./login.css'],
})
export class Login {

  errorMessage = '';
  isLoading = false;

  loginForm = new FormGroup({
    email: new FormControl('', [
      Validators.required,
      Validators.email
    ]),
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(6)
    ])
  });

   constructor(private Authentication: Authentication, private router: Router) {}


  onSubmit() {
    if (this.loginForm.invalid) return;

    this.isLoading = true;
    this.errorMessage = '';

    this.Authentication.login({
      email: this.loginForm.value.email!,
      password: this.loginForm.value.password!
    }).subscribe({
      next: (response) => {
      this.isLoading = false;                  
      this.Authentication.saveSession(response);  
      this.redirectByRole(response.role);   
      },
      error: (err) => {
        this.isLoading = false;
        
        this.errorMessage = err.error?.message || 'Invalid email or password';
      }
    });}

    private redirectByRole(role: string): void {
    switch (role) {
      case 'STUDENT':
        this.router.navigate(['/dashboard']);
        break;
      default:
        this.router.navigate(['/dashboard']);
    }
}
}