import { Routes } from '@angular/router';

import { AuthGuard } from './guards/authguard-guard';

import { AuthLayout } from './layouts/auth-layout/auth-layout';
import { MainLayout } from './layouts/main-layout/main-layout';

import { Login } from './pages/login/login';
import { Dashboard } from './pages/dashboard/dashboard';
import { assignments } from './pages/assignments/assignments';

import { Attendance } from './pages/attendance/attendance';
import { AttendanceDaly } from './pages/AttendanceDaily/attendance-daily';
import { AttendanceMonthly } from './pages/attendance-monthly/attendance-monthly';

import { Projects } from './pages/projects/projects';
import { Courses } from './pages/courses/courses';
import { Marks } from './pages/marks/marks';
import { Notifications } from './pages/notifications/notifications';
import { Profile } from './pages/profile/profile';
import { Schedule } from './pages/schedule/schedule';
import { Settings } from './pages/settings/settings';
import { Support } from './pages/support/support';
import { Violations } from './pages/violations/violation-comp';

import { ChangPassword } from './components/change-password/change-password';

export const routes: Routes = [

  // =========================
  // Authentication
  // =========================
  {
    path: '',
    component: AuthLayout,
    children: [
      {
        path: '',
        redirectTo: 'login',
        pathMatch: 'full'
      },
      {
        path: 'login',
        component: Login
      }
    ]
  },

  // =========================
  // Main Layout
  // =========================
  {
    path: '',
    component: MainLayout,
    canActivate: [AuthGuard],
    children: [

      {
        path: 'dashboard',
        component: Dashboard
      },

      {
        path: 'assignments',
        component: assignments
      },

      // Attendance Pages
      {
        path: 'attendance',
        component: Attendance
      },
      {
        path: 'daily',
        component: AttendanceDaly
      },
      {
        path: 'monthly',
        component: AttendanceMonthly
      },

      {
        path: 'projects',
        component: Projects
      },

      {
        path: 'courses',
        component: Courses
      },

      {
        path: 'marks',
        component: Marks
      },

      {
        path: 'notifications',
        component: Notifications
      },

      {
        path: 'profile',
        component: Profile
      },

      {
        path: 'schedule',
        component: Schedule
      },

      {
        path: 'settings',
        component: Settings
      },

      {
        path: 'support',
        component: Support
      },

      {
        path: 'change-password',
        component: ChangPassword
      },

      {
        path: 'violations',
        component: Violations
      }
    ]
  },

  // // =========================
  // // Unknown Route
  // // =========================
  // {
  //   path: '**',
  //   redirectTo: 'login'
  // }

];