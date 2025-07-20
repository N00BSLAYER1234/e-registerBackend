import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Justification } from '../../models/justification';

@Injectable({ providedIn: 'root' })
export class StudentJustificationService {
  private justificationsSubject = new BehaviorSubject<Justification[]>([]);
  justifications$ = this.justificationsSubject.asObservable();
}
