import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TutorDashboardRoutingModule } from './tutor-dashboard-routing-module';
import { TutorDashboard } from './tutor-dashboard';


@NgModule({
  declarations: [
    TutorDashboard
  ],
  imports: [
    CommonModule,
    TutorDashboardRoutingModule
  ]
})
export class TutorDashboardModule { }
