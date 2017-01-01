import { Component } from '@angular/core';
import { UserService } from '../services/user.service';

@Component({
  moduleId: module.id,
  selector: 'register',
  templateUrl: 'register.component.html',
  providers: [UserService]
})

export class RegisterComponent  { 
	users :User[];
	showUsers: boolean;
	username: string;
	email: string;
	password: string;

	constructor(private userService: UserService) {
		this.userService = userService;
	}

	NewUser(username: string, password: string, email: string) {
	let user = {
		username: username, 
		password: password, 
		email: email
	}

	console.log(user);
    this.userService.createUser(user).subscribe(
       data => {
         // refresh the list
         return true;
       },
       error => {
         console.error("Error saving User!");
       }
    );
  }
}