import { Injectable } from '@angular/core';
import { CanActivate } from '@angular/router';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';

@Injectable()
export class AuthGuardLogged implements CanActivate {

  constructor(private authService: AuthService) {}

  canActivate() {
    return !this.authService.loggedIn();
  }
} 