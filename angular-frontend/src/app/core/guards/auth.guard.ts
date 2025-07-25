import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';


export class AuthGuard implements CanActivate {
  constructor(private auth: AuthService, private router: Router) {}

  canActivate(): boolean {
    const loggedIn = !!this.auth.currentUser;
    if (!loggedIn) {
      this.router.navigate(['/auth']);
    }
    return loggedIn;
  }
}
