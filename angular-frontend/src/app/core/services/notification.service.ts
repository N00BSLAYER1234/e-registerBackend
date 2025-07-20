import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Notification } from '../../shared/models/notification';

@Injectable({ providedIn: 'root' })
export class NotificationService {
  private notifications$ = new BehaviorSubject<Notification[]>([]);

  get notifications(): Observable<Notification[]> {
    return this.notifications$.asObservable();
  }

  add(notification: Notification): void {
    const value = this.notifications$.value;
    this.notifications$.next([...value, notification]);
  }
}
