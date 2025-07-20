import { Injectable } from '@angular/core';
import { ApiService } from '../../../core/services/api.service';
import { Attendance } from '../../../shared/models/attendance';
import { ApiResponse } from '../../../shared/models/api-response';
import { Observable, BehaviorSubject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class StudentAttendanceService {
  private attendancesSubject = new BehaviorSubject<Attendance[]>([]);
  readonly attendances$ = this.attendancesSubject.asObservable();

  constructor(private api: ApiService) {}

  mark(data: Partial<Attendance>): Observable<ApiResponse<Attendance>> {
    return this.api.post<Attendance>('/student/attendance', data);
  }

  loadHistory(studentId: number): void {
    this.history(studentId).subscribe(res => {
      if (res.success) {
        this.attendancesSubject.next(res.data);
      }
    });
  }

  private history(studentId: number): Observable<ApiResponse<Attendance[]>> {
    return this.api.get<Attendance[]>(`/student/attendance/${studentId}`);
  }
}
