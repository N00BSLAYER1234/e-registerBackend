import { Component, ChangeDetectionStrategy } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { take } from 'rxjs/operators';
import { AuthService } from '../../core/services/auth.service';
import { LoginForm } from '../../shared/models/login-form';

@Component({
  selector: 'app-auth',
  standalone: false,
  templateUrl: './auth.html',
  styleUrl: './auth.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class Auth {
  form;

  constructor(private fb: FormBuilder, private auth: AuthService, private router: Router) {
    this.form = this.fb.nonNullable.group({
      username: '',
      password: '',
      role: 'student' as LoginForm['role']
    });
  }

  login(): void {
    if (this.form.valid) {
      this.auth.login(this.form.getRawValue()).pipe(take(1)).subscribe(res => {
        if (res.success) {
          this.auth.setUser(res.data);
          this.router.navigate([
            res.data.role === 'tutor' ? '/tutor-dashboard' : '/student-dashboard'
          ]);
        }
      });
    }
  }
}
