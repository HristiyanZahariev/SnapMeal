import { Component, ViewChild } from '@angular/core';
import { UserService } from '../services/user.service';
import { AuthService } from '../services/auth.service';
import { AuthGuard } from '../services/auth-guard.service';
import { AuthHttp, tokenNotExpired } from 'angular2-jwt';
import {RatingModule} from "ngx-rating";


@Component({
  moduleId: module.id,
  selector: 'user',
  templateUrl: 'user.component.html',
  providers: [
  	[UserService],
  	[AuthGuard]
  ]
})
export class UserComponent  { 
	user :any;
	showUsers: boolean;
	diet: any;
	starsCount: any[];

	@ViewChild("fileInput") fileInput: any;
	recipes: RootObject;

	constructor(private userService: UserService) {
		this.showUsers = false;
		this.userService = userService;

		this.userService.getCurrentUser().subscribe(user => {
			this.user = user;
			console.log(() => localStorage.getItem("id_token"));
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


	selectDietPlan() {
		console.log(this.diet)
		console.log(this.user)
		this.userService.selectDietPlan(this.diet, this.user).subscribe(res => {
			console.log(res);
		});	
	} 

	addFile(): void {
	    let fi = this.fileInput.nativeElement;
	    if (fi.files && fi.files[0]) {
	        let fileToUpload = fi.files[0];
	        this.userService
	            .upload(fileToUpload)
				.subscribe(value => {
    				this.recipes = <RootObject>value.json();
    				console.log(this.recipes)

    				console.log(this.recipes.content);
	    		});
		}
	}
}

export interface Ingredient {
    id: string;
    name: string;
}

export interface Content {
    id: string;
    name: string;
    description: string;
    ingredient: Ingredient[];
}

export interface RootObject {
    content: Content[];
    last: boolean;
    totalPages: number;
    totalElements: number;
    first: boolean;
    sort?: any;
    numberOfElements: number;
    size: number;
    number: number;
}

