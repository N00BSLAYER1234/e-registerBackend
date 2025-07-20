import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: 'auth', loadChildren: () => import('./features/auth/auth-module').then(m => m.AuthModule) },
  { path: 'student-dashboard', loadChildren: () => import('./features/student-dashboard/student-dashboard-module').then(m => m.StudentDashboardModule) },
  { path: 'tutor-dashboard', loadChildren: () => import('./features/tutor-dashboard/tutor-dashboard-module').then(m => m.TutorDashboardModule) },
  { path: 'home', loadChildren: () => import('./features/home/home-module').then(m => m.HomeModule) },
  { path: '', pathMatch: 'full', redirectTo: 'home' },
  { path: '**', redirectTo: 'home' }
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
