import { Component} from '@angular/core';
import { UserService } from '../services/user.service';


@Component({
    moduleId: module.id,
    templateUrl: 'login.component.html',
    selector: 'login',
    providers: [UserService]
})

export class LoginComponent {
    model: any = {};
    loading = false;

    constructor(private userService: UserService) {
        this.userService = userService;
    }

    login(username: string, password: string) {
        this.userService.login(username, password).subscribe(
           data => {
             console.log("evrika bace")
             return true;
           },
           error => {
             console.error("Error saving User!");
           }
        );
    }
           
}