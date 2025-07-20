import { Injectable } from '@angular/core';
import { ApiService } from '../../../core/services/api.service';
import { User } from '../../../shared/models/user';
import { Observable } from 'rxjs';
import { ApiResponse } from '../../../shared/models/api-response';

@Injectable({ providedIn: 'root' })
export class StudentManagementService {
  constructor(private api: ApiService) {}

  list(): Observable<ApiResponse<User[]>> {
    return this.api.get<User[]>('/tutor/students');
  }
}
