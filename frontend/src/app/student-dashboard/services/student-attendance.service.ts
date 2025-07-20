import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Attendance } from '../../models/attendance';

@Injectable({ providedIn: 'root' })
export class StudentAttendanceService {
  private recordsSubject = new BehaviorSubject<Attendance[]>([]);
  records$ = this.recordsSubject.asObservable();
}
