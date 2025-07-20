import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Home } from './home/home';



@NgModule({
  declarations: [
    Home
  ],
  imports: [
    CommonModule,
    RouterModule
  ]
})
export class HomeModule { }
