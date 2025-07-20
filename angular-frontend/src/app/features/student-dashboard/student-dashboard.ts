import { Component, ChangeDetectionStrategy } from '@angular/core';

@Component({
  selector: 'app-student-dashboard',
  standalone: false,
  templateUrl: './student-dashboard.html',
  styleUrl: './student-dashboard.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class StudentDashboard {

}
