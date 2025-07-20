import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { StudentDashboardRoutingModule } from './student-dashboard-routing-module';
import { StudentDashboard } from './student-dashboard';


@NgModule({
  declarations: [
    StudentDashboard
  ],
  imports: [
    CommonModule,
    StudentDashboardRoutingModule
  ]
})
export class StudentDashboardModule { }
