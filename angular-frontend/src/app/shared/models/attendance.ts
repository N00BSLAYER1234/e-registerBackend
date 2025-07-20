import { User } from './user';

export type AttendanceStatus = 'present' | 'absent' | 'pending';

export interface Attendance {
  id: number;
  student: User;
  date: string; // ISO date
  status: AttendanceStatus;
  approved: boolean;
}
