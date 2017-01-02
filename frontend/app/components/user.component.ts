import { Component, ViewChild } from '@angular/core';
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
	@ViewChild("fileInput") fileInput: any;

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

	addFile(): void {
	    let fi = this.fileInput.nativeElement;
	    if (fi.files && fi.files[0]) {
	        let fileToUpload = fi.files[0];
	        this.userService
	            .upload(fileToUpload)
	            .subscribe(res => {
	                console.log(res);
	            });
	    }
	}
}

interface User {
	id: number;
	username: string;
	email: string;
}

