import { Component} from '@angular/core';
import { AuthService } from '../services/auth.service';


@Component({
    moduleId: module.id,
    templateUrl: 'login.component.html',
    selector: 'login',
    providers: [AuthService]
})

export class LoginComponent {

  credentials: Credentials;

  constructor(private auth: AuthService) {}

  onLogin(username:any, password:any, email: any) {
    let user = {
        username: username, 
        password: password, 
        email: email
    }
    this.auth.login(user);
  }
}

interface Credentials {
  username: string,
  password: string,
  email: string
}