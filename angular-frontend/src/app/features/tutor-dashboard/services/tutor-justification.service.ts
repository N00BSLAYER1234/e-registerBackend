import { Injectable } from '@angular/core';
import { ApiService } from '../../../core/services/api.service';
import { Justification } from '../../../shared/models/justification';
import { Observable } from 'rxjs';
import { ApiResponse } from '../../../shared/models/api-response';

@Injectable({ providedIn: 'root' })
export class TutorJustificationService {
  constructor(private api: ApiService) {}

  review(id: number, status: string): Observable<ApiResponse<Justification>> {
    return this.api.post<Justification>(`/tutor/justification/${id}`, { status });
  }
}
