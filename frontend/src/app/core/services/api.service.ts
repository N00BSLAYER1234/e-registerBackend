import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ApiService {
  constructor(private http: HttpClient) {}

  get<T>(endpoint: string): Observable<T> {
    return this.http.get<T>(endpoint);
  }

  post<T>(endpoint: string, data: unknown): Observable<T> {
    return this.http.post<T>(endpoint, data);
  }
}
