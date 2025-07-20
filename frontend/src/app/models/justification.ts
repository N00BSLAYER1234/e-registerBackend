export interface Justification {
  id: number;
  studentId: number;
  date: string;
  tipo: string;
  approvato: boolean;
  note?: string;
}
