import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse } from '../../shared/models/api-response';

@Injectable({ providedIn: 'root' })
export class ApiService {
  private baseUrl = '/api';

  constructor(private http: HttpClient) {}

  get<T>(endpoint: string): Observable<ApiResponse<T>> {
    return this.http.get<ApiResponse<T>>(this.baseUrl + endpoint);
  }

  post<T>(endpoint: string, data: unknown): Observable<ApiResponse<T>> {
    return this.http.post<ApiResponse<T>>(this.baseUrl + endpoint, data);
  }
}
