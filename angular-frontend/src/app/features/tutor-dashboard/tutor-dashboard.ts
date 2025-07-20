import { Component, ChangeDetectionStrategy, OnInit } from '@angular/core';
import { StudentManagementService } from './services/student-management.service';

@Component({
  selector: 'app-tutor-dashboard',
  standalone: false,
  templateUrl: './tutor-dashboard.html',
  styleUrl: './tutor-dashboard.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class TutorDashboard implements OnInit {
  students$;

  constructor(private studentsService: StudentManagementService) {
    this.students$ = studentsService.students$;
  }

  ngOnInit(): void {
    this.studentsService.load();
  }
}
