import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { ApiService } from './api.service';
import { User } from '../../shared/models/user';
import { LoginForm } from '../../shared/models/login-form';
import { ApiResponse } from '../../shared/models/api-response';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private currentUser$ = new BehaviorSubject<User | null>(null);

  constructor(private api: ApiService) {}

  login(form: LoginForm): Observable<ApiResponse<User>> {
    return this.api.post<User>('/auth/login', form);
  }

  setUser(user: User | null): void {
    this.currentUser$.next(user);
  }

  get userChanges(): Observable<User | null> {
    return this.currentUser$.asObservable();
  }

  get currentUser(): User | null {
    return this.currentUser$.value;
  }
}
