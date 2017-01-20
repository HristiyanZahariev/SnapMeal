import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import { AuthHttp, AuthConfig, AUTH_PROVIDERS, provideAuth } from 'angular2-jwt';

import { AppComponent }  from './app.component';
import { UserComponent }  from './components/user.component';
import { AboutComponent }  from './components/about.component';
import { RegisterComponent } from './components/register.component';
import { LoginComponent } from './components/login.component';
import { HeaderComponent } from './components/header.component';
import {routing} from './app.routing';
import { AuthService } from './services/auth.service';
import { AuthGuard } from './services/auth-guard.service';

@NgModule({
  imports:      [ BrowserModule, FormsModule, HttpModule, routing ],
  declarations: [ AppComponent, UserComponent, AboutComponent, RegisterComponent, LoginComponent, HeaderComponent],
  bootstrap:    [ AppComponent ],
  providers: [ 
  	AuthService, 
  	AuthGuard, 
    AuthHttp,
    provideAuth({
        headerName: 'X-AUTH-TOKEN',
        headerPrefix: '',
        tokenName: 'token',
        tokenGetter: (() => localStorage.getItem('id_token')),
        globalHeaders: [{ 'Content-Type': 'application/json' }],
        noJwtError: true
    })
  ]
})
export class AppModule { }