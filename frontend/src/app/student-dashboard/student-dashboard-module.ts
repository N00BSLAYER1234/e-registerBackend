import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { StudentDashboard } from './student-dashboard/student-dashboard';



@NgModule({
  declarations: [
    StudentDashboard
  ],
  imports: [
    CommonModule,
    RouterModule
  ]
})
export class StudentDashboardModule { }
