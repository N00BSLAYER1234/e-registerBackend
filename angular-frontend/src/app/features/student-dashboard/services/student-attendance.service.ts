import { Injectable } from '@angular/core';
import { ApiService } from '../../../core/services/api.service';
import { Attendance } from '../../../shared/models/attendance';
import { ApiResponse } from '../../../shared/models/api-response';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class StudentAttendanceService {
  constructor(private api: ApiService) {}

  mark(data: Partial<Attendance>): Observable<ApiResponse<Attendance>> {
    return this.api.post<Attendance>('/student/attendance', data);
  }

  history(studentId: number): Observable<ApiResponse<Attendance[]>> {
    return this.api.get<Attendance[]>(`/student/attendance/${studentId}`);
  }
}
