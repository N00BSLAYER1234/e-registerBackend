import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../../shared/shared-module';

import { TutorDashboardRoutingModule } from './tutor-dashboard-routing-module';
import { TutorDashboard } from './tutor-dashboard';


@NgModule({
  declarations: [
    TutorDashboard
  ],
  imports: [
    CommonModule,
    TutorDashboardRoutingModule,
    SharedModule
  ]
})
export class TutorDashboardModule { }
