import { Routes } from '@angular/router';

import { ChangPassword } from './components/change-password/change-password';
import { AuthLayout } from './layouts/auth-layout/auth-layout';
import { Login } from './pages/login/login';
import { AuthGuard } from './guards/authguard-guard';
import { MainLayout } from './layouts/main-layout/main-layout';
import { assignments } from './pages/assignments/assignments';
import { Attendance } from './pages/attendance/attendance';
import { Projects } from './pages/projects/projects';
import { Courses } from './pages/courses/courses';
import { Marks } from './pages/marks/marks';
import { Notifications } from './pages/notifications/notifications';
import { Profile } from './pages/profile/profile';
import { Schedule } from './pages/schedule/schedule';
import { Settings } from './pages/settings/settings';
import { Dashboard } from './pages/dashboard/dashboard';
import { Support } from './pages/support/support';
import { Violations } from './pages/violations/violation-comp';
import { AttendanceDaly } from './pages/AttendanceDaily/attendance-daily';
import { AttendanceMonthly } from './pages/attendance-monthly/attendance-monthly';



export const routes: Routes = [
    {
    path: '',
    component: AuthLayout,
    children: [
        {
            path: "login",
            component:Login
        },
        {
            path: "",
            component:Login
        }
    ]
    },
     {
        path:"",
        component:MainLayout,
       canActivate: [AuthGuard],
       children: [
    {
        path:"dashboard",
        component: Dashboard,
        canActivate: [AuthGuard],
    },
    {
        path:"assignments",
        component: assignments,
        canActivate: [AuthGuard],
    },
    {
        path:"attendance",
        component:Attendance,
        canActivate: [AuthGuard],
        children:
        [
     {
        path:"attendance/daily",
        component:AttendanceDaly,
        canActivate: [AuthGuard],
    },
      {
        path:"attendance/monthly",
        component:AttendanceMonthly,
        canActivate: [AuthGuard],
    },
        ]
    },
    {
        path:"projects",
        component:Projects,
        canActivate: [AuthGuard],
    },
    {
        path:"courses",
        component:Courses,
        canActivate: [AuthGuard],
    },
    {
        path:"marks",
        component:Marks,
        canActivate: [AuthGuard],
    },
    {
        path:"notifications",
        component:Notifications,
        canActivate: [AuthGuard],
    },
    {
        path:"profile",
        component:Profile,
        canActivate: [AuthGuard],
    },
    {
        path:"schedule",
        component:Schedule,
        canActivate: [AuthGuard],
    },
    {
        path:"settings",
        component:Settings,
        canActivate: [AuthGuard],
    },
    {
        path:"support",
        component:Support,
        canActivate: [AuthGuard],
    },
    {
        path:"change-password",
        component:ChangPassword,
        canActivate: [AuthGuard],
    },
      {
        path:"violations",
        component:Violations,
        canActivate: [AuthGuard],
    },
        ]
    },
 
];
