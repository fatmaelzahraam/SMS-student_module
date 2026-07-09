import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {

  if (req.url.includes('/api/v1/auth/login')) {
    return next(req);
  }

  const token = localStorage.getItem('token');

  console.log('[interceptor]', req.url, '| token:', token ? token.slice(0, 20) + '...' : 'NULL');

  if (!token) {
    console.warn('[interceptor] SKIPPING — no token for:', req.url);
    return next(req);
  }

  return next(req.clone({
    setHeaders: { Authorization: `Bearer ${token}` }
  }));
};