export type UserRole = 'student' | 'tutor' | 'admin';

export interface User {
  id: number;
  username: string;
  email: string;
  role: UserRole;
}
