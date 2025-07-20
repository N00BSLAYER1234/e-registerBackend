import { inject } from '@angular/core';
import { CanActivateFn } from '@angular/router';
import { AuthService } from '../services/auth.service';

export function roleGuard(roles: string[]): CanActivateFn {
  return () => {
    const user = inject(AuthService).currentUser;
    return user ? roles.includes(user.ruolo) : false;
  };
}
