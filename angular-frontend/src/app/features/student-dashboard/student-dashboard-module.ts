import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../../shared/shared-module';

import { StudentDashboardRoutingModule } from './student-dashboard-routing-module';
import { StudentDashboard } from './student-dashboard';


@NgModule({
  declarations: [
    StudentDashboard
  ],
  imports: [
    CommonModule,
    StudentDashboardRoutingModule,
    SharedModule
  ]
})
export class StudentDashboardModule { }
