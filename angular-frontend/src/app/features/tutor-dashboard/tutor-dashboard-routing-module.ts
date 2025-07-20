import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TutorDashboard } from './tutor-dashboard';

const routes: Routes = [{ path: '', component: TutorDashboard }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TutorDashboardRoutingModule { }
