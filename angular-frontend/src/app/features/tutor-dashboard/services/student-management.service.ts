import { Injectable } from '@angular/core';
import { ApiService } from '../../../core/services/api.service';
import { User } from '../../../shared/models/user';
import { Observable, BehaviorSubject } from 'rxjs';
import { ApiResponse } from '../../../shared/models/api-response';

@Injectable({ providedIn: 'root' })
export class StudentManagementService {
  private studentsSubject = new BehaviorSubject<User[]>([]);
  readonly students$ = this.studentsSubject.asObservable();

  constructor(private api: ApiService) {}

  load(): void {
    this.list().subscribe(res => {
      if (res.success) {
        this.studentsSubject.next(res.data);
      }
    });
  }

  private list(): Observable<ApiResponse<User[]>> {
    return this.api.get<User[]>('/tutor/students');
  }
}
