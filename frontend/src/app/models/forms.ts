export interface LoginForm {
  username: string;
  password: string;
}

export interface AttendanceForm {
  date: string;
  status: 'PRESENTE' | 'ASSENTE';
}

export interface JustificationForm {
  date: string;
  tipo: string;
  note?: string;
}
