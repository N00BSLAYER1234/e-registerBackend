import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class WebSocketService {
  private subject = new Subject<string>();
  messages$ = this.subject.asObservable();

  connect(): void {
    // placeholder for socket.io-client connection
  }

  emit(message: string): void {
    this.subject.next(message);
  }
}
