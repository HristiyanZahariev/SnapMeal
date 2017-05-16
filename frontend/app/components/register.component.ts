import { Component } from '@angular/core';
import { UserService } from '../services/user.service';
import { Router } from '@angular/router';

@Component({
  moduleId: module.id,
  selector: 'register',
  templateUrl: 'register.component.html',
  providers: [UserService]
})

export class RegisterComponent  { 
	user: any = {};
	loading: boolean = false;
	confirmation: any = {};
	pazzword = "passowrd"

	constructor(private userService: UserService, private router: Router) {
		//this.userService = userService;
	}

	register() {
        this.loading = true;
        this.userService.create(this.user)
            .subscribe(
                data => {
                    this.router.navigate(['/login']);
                },
                error => {
                    this.loading = false;
                });
    }

}

export interface User {
    username: string;
    email: string;
    firstname?: any;
    lastname?: any;
    ratings: any[];
}

export interface Ingredient {
    id: string;
    name: string;
}

export interface Content {
    id: string;
    name: string;
    description: string;
    rating: number;
    ingredient: Ingredient[];
}

export interface Recipe {
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