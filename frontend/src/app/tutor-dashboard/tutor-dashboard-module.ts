import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { TutorDashboard } from './tutor-dashboard/tutor-dashboard';



@NgModule({
  declarations: [
    TutorDashboard
  ],
  imports: [
    CommonModule,
    RouterModule
  ]
})
export class TutorDashboardModule { }
