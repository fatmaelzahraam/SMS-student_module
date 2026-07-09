import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const AuthGuard: CanActivateFn = () => {
  const router = inject(Router);
  const token = localStorage.getItem('token');
  const studentId = localStorage.getItem('studentId');

   if (token && studentId) return true;

  localStorage.clear(); //  clear stale token without studentId
  router.navigate(['']);
  return false;
};