export interface Attendance {
  id: number;
  studentId: number;
  date: string; // ISO date
  status: 'PRESENTE' | 'ASSENTE';
  approvato: boolean;
}
