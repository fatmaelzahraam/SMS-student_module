import { Component } from '@angular/core';
import { Routes } from '@angular/router';
import { RouterOutlet } from '@angular/router';

import { AuthLayout } from './layouts/auth-layout/auth-layout';
import { MainLayout } from './layouts/main-layout/main-layout';

import { Login } from './pages/login/login';
import { Dashboard } from './pages/dashboard/dashboard';
import { assignments } from './pages/assignments/assignments';

import { Attendance } from './pages/attendance/attendance';
import { AttendanceDaly } from './pages/AttendanceDaily/attendance-daily';
import { AttendanceMonthly } from './pages/attendance-monthly/attendance-monthly';
import { AttendanceNav } from './components/attendance-nav/attendance-nav';

import { Projects } from './pages/projects/projects';
import { Courses } from './pages/courses/courses';
import { Marks } from './pages/marks/marks';
import { Notifications } from './pages/notifications/notifications';
import { Profile } from './pages/profile/profile';
import { Schedule } from './pages/schedule/schedule';
import { Settings } from './pages/settings/settings';
import { Support } from './pages/support/support';
import { Violations } from './pages/violations/violation-comp';
import { Complaints } from './pages/complaints/complaints';

@Component({
  standalone: true,
  imports: [AttendanceNav, RouterOutlet],
  template: `
    <app-attendance-nav></app-attendance-nav>
    <router-outlet></router-outlet>
  `
})
class AttendanceShell {}

export const routes: Routes = [

  {
    path: '',
    component: AuthLayout,
    children: [
      { path: '',      redirectTo: 'login', pathMatch: 'full' },
      { path: 'login', component: Login }
    ]
  },

  {
    path: '',
    component: MainLayout,
    children: [
      { path: 'dashboard',     component: Dashboard },
      { path: 'assignments',   component: assignments },

      {
        path: 'attendance',
        component: AttendanceShell,
        children: [
          { path: '',        component: Attendance },       // /attendance
          { path: 'daily',   component: AttendanceDaly },   // /attendance/daily
          { path: 'monthly', component: AttendanceMonthly } // /attendance/monthly
        ]
      },

      { path: 'projects',      component: Projects },
      { path: 'courses',       component: Courses },
      { path: 'marks',         component: Marks },
      { path: 'notifications', component: Notifications },
      { path: 'profile',       component: Profile },
      { path: 'schedule',      component: Schedule },
      { path: 'settings',      component: Settings },

      {
        path: 'support',
        component: Support,
        children: [
          { path: '',           redirectTo: 'complaints', pathMatch: 'full' },
          { path: 'complaints', component: Complaints },
          { path: 'violations', component: Violations },
        ]
      }
    ]
  },
];