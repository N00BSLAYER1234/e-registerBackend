import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Home } from './home/home/home';
import { LoginComponent } from './auth/login/login';
import { StudentDashboard } from './student-dashboard/student-dashboard/student-dashboard';
import { TutorDashboard } from './tutor-dashboard/tutor-dashboard/tutor-dashboard';

const routes: Routes = [
  { path: '', component: Home },
  { path: 'login', component: LoginComponent },
  { path: 'student', component: StudentDashboard },
  { path: 'tutor', component: TutorDashboard }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
