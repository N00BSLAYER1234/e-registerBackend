import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { User } from '../../models/user';

@Injectable({ providedIn: 'root' })
export class StudentManagementService {
  private studentsSubject = new BehaviorSubject<User[]>([]);
  students$ = this.studentsSubject.asObservable();
}
