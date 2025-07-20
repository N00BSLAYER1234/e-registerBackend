import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Justification } from '../../models/justification';

@Injectable({ providedIn: 'root' })
export class TutorJustificationService {
  private reviewSubject = new BehaviorSubject<Justification[]>([]);
  review$ = this.reviewSubject.asObservable();
}
