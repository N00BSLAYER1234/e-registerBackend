import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../../shared/shared-module';

import { AuthRoutingModule } from './auth-routing-module';
import { Auth } from './auth';


@NgModule({
  declarations: [
    Auth
  ],
  imports: [
    CommonModule,
    AuthRoutingModule,
    SharedModule
  ]
})
export class AuthModule { }
