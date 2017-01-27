import { Component} from '@angular/core';
import { AuthService } from '../services/auth.service'

@Component({
    moduleId: module.id,
    templateUrl: 'header.component.html',
    selector: 'header',
    providers: [AuthService]
})

export class HeaderComponent {
  constructor(private auth: AuthService) {}

  logOut() {
    this.auth.logout();
  }
}
