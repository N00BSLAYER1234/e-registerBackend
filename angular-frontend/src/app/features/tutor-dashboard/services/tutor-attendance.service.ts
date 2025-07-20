import { Injectable } from '@angular/core';
import { ApiService } from '../../../core/services/api.service';
import { Attendance } from '../../../shared/models/attendance';
import { Observable } from 'rxjs';
import { ApiResponse } from '../../../shared/models/api-response';

@Injectable({ providedIn: 'root' })
export class TutorAttendanceService {
  constructor(private api: ApiService) {}

  approve(presenceId: number): Observable<ApiResponse<Attendance>> {
    return this.api.post<Attendance>(`/tutor/approve/${presenceId}`, {});
  }
}
