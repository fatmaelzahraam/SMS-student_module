import { Routes } from '@angular/router';
import { assignments} from './components/assignments/assignments';
import { Login } from './components/login/login';
import { Dashboard } from './components/dashboard/dashboard';
import { Attendance } from './components/attendance/attendance';
import { Projects } from './components/projects/projects';
import { Courses } from './components/courses/courses';
import { Marks } from './components/marks/marks';
import { Notifications } from './components/notifications/notifications';
import { Profile } from './components/profile/profile';
import { Schedule } from './components/schedule/schedule';
import { AuthGuard } from './guards/authguard-guard';
import { Settings } from './components/settings/settings';
import { ChangPassword } from './components/change-password/change-password';


export const routes: Routes = [
    {
        path:"",
        component: Login,
    },
     {
        path:"dashboard",
        component:Dashboard,
       canActivate: [AuthGuard],
    },
    {
        path:"assignments",
        component: assignments,
    },
    {
        path:"attendance",
        component:Attendance,
    },
    {
        path:"projects",
        component:Projects,
    },
    {
        path:"courses",
        component:Courses,
    },
    {
        path:"marks",
        component:Marks,
    },
    {
        path:"notifications",
        component:Notifications,
    },
    {
        path:"profile",
        component:Profile,
        canActivate: [AuthGuard],
    },
    {
        path:"scheduls",
        component:Schedule,
    },
    {
        path:"settings",
        component:Settings,
    },
    {
        path: "ChangePassword",
        component:ChangPassword,
    }
];
