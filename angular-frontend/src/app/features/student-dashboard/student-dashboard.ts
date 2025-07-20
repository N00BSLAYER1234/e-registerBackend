import { Component, ChangeDetectionStrategy, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { StudentAttendanceService } from './services/student-attendance.service';

@Component({
  selector: 'app-student-dashboard',
  standalone: false,
  templateUrl: './student-dashboard.html',
  styleUrl: './student-dashboard.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class StudentDashboard implements OnInit, OnDestroy {
  attendances$;
  private destroy$ = new Subject<void>();

  constructor(private attendanceService: StudentAttendanceService) {
    this.attendances$ = attendanceService.attendances$;
  }

  ngOnInit(): void {
    this.attendanceService.loadHistory(1);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
