import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { CoreModule } from './core/core-module';
import { SharedModule } from './shared/shared-module';
import { AuthModule } from './auth/auth-module';
import { StudentDashboardModule } from './student-dashboard/student-dashboard-module';
import { TutorDashboardModule } from './tutor-dashboard/tutor-dashboard-module';
import { HomeModule } from './home/home-module';
import { authInterceptor } from './core/interceptors/auth.interceptor';
import { errorInterceptor } from './core/interceptors/error.interceptor';

@NgModule({
  declarations: [
    App
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    CoreModule,
    SharedModule,
    AuthModule,
    StudentDashboardModule,
    TutorDashboardModule,
    HomeModule
  ],
  providers: [
    provideBrowserGlobalErrorListeners(),
    { provide: HTTP_INTERCEPTORS, useValue: authInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useValue: errorInterceptor, multi: true }
  ],
  bootstrap: [App]
})
export class AppModule { }
