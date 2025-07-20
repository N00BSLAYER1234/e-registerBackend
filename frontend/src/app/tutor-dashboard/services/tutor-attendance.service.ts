import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Attendance } from '../../models/attendance';

@Injectable({ providedIn: 'root' })
export class TutorAttendanceService {
  private approvalsSubject = new BehaviorSubject<Attendance[]>([]);
  approvals$ = this.approvalsSubject.asObservable();
}
