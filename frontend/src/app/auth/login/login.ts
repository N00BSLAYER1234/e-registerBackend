import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';


@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class LoginComponent implements OnInit {
  form: FormGroup;
  role: 'student' | 'tutor' | null = null;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private auth: AuthService
  ) {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    const role = this.route.snapshot.queryParamMap.get('role');
    if (role === 'student' || role === 'tutor') {
      this.role = role;
    }
  }

  selectRole(r: 'student' | 'tutor'): void {
    this.role = r;
  }

  onSubmit(): void {
    if (this.form.valid && this.role) {
      this.auth.login({ email: this.form.value.email, role: this.role });
      const target = this.role === 'student' ? '/student' : '/tutor';
      this.router.navigate([target]);
    }
  }
}
