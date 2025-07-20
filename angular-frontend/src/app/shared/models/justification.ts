import { User } from './user';

export type JustificationStatus = 'pending' | 'approved' | 'rejected';

export interface Justification {
  id: number;
  student: User;
  date: string;
  reason: string;
  status: JustificationStatus;
  reviewNote?: string;
}
