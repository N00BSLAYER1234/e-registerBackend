import { Component, ChangeDetectionStrategy } from '@angular/core';

@Component({
  selector: 'app-auth',
  standalone: false,
  templateUrl: './auth.html',
  styleUrl: './auth.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class Auth {

}
