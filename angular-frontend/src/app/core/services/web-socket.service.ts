import { Injectable } from '@angular/core';
import { io, Socket } from 'socket.io-client';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class WebSocketService {
  private socket: Socket | null = null;

  connect(url: string): void {
    if (!this.socket) {
      this.socket = io(url);
    }
  }

  listen<T>(event: string): Observable<T> {
    return new Observable(observer => {
      this.socket?.on(event, data => observer.next(data));
      return () => this.socket?.off(event);
    });
  }

  emit(event: string, data: unknown): void {
    this.socket?.emit(event, data);
  }
}
