import { Component} from '@angular/core';
import { AuthService } from '../services/auth.service';
import {MdProgressBarModule} from '@angular/material';


@Component({
    moduleId: module.id,
    templateUrl: 'login.component.html',
    selector: 'login',
    providers: [AuthService]
})

export class LoginComponent {

  credentials: Credentials;
  loading = false;

  constructor(private auth: AuthService) {}

  onLogin(username:any, password:any, email: any) {
    let user = {
        username: username, 
        password: password, 
        email: email
    }
    this.loading = true;
    this.auth.login(user); 
  }

  logOut() {
      this.auth.logout();
  }
}

interface Credentials {
  username: string,
  password: string,
  email: string
}