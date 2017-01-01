import { Component } from '@angular/core';
import { UserService } from '../services/user.service';

@Component({
  moduleId: module.id,
  selector: 'user',
  templateUrl: 'user.component.html',
  providers: [UserService]
})
export class UserComponent  { 
	users :User[];
	showUsers: boolean;
	username: string;
	email: string;
	password: string;

	constructor(private userService: UserService) {
		this.showUsers = false;
		this.userService = userService;

		this.userService.getUsers().subscribe(users => {
			this.users = users;
		});
	}

	toogleUsers() {
		if (this.showUsers == false) {
			this.showUsers = true;
		}
		else {
			this.showUsers = false;
		}
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
         console.log("fiki");
         return true;
       },
       error => {
         console.error("Error saving User!");
       }
    );
  }
}


interface address {
	street: string;
	city: string;
	state: string;
}

interface User {
	id: number;
	username: string;
	email: string;
}

