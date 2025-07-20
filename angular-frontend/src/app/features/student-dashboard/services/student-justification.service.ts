import { Injectable } from '@angular/core';
import { ApiService } from '../../../core/services/api.service';
import { Justification } from '../../../shared/models/justification';
import { Observable } from 'rxjs';
import { ApiResponse } from '../../../shared/models/api-response';

@Injectable({ providedIn: 'root' })
export class StudentJustificationService {
  constructor(private api: ApiService) {}

  create(data: Partial<Justification>): Observable<ApiResponse<Justification>> {
    return this.api.post<Justification>('/student/justification', data);
  }
}
