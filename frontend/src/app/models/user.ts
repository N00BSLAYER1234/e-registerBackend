export interface User {
  id?: number;
  nome?: string;
  cognome?: string;
  email: string;
  ruolo: 'STUDENTE' | 'TUTOR' | 'ADMIN' | 'student' | 'tutor';
  token?: string;
}
