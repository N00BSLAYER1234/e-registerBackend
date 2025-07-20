import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';


export class RoleGuard implements CanActivate {
  constructor(private auth: AuthService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const expectedRole = route.data['role'];
    const user = this.auth.currentUser;
    const hasRole = !!user && user.role === expectedRole;
    if (!hasRole) {
      this.router.navigate(['/auth']);
    }
    return hasRole;
  }
}
