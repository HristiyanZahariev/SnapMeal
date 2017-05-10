import { Component, ViewChild } from '@angular/core';
import { UserService } from '../services/user.service';
import { AuthService } from '../services/auth.service';
import { AuthGuard } from '../services/auth-guard.service';
import { AuthHttp, tokenNotExpired } from 'angular2-jwt';
import {RatingModule} from "ngx-rating";
import { TagInputModule } from 'ng2-tag-input';
import {MdButtonModule} from '@angular/material';
import {MdProgressSpinnerModule} from '@angular/material';


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
	requestSent: boolean;
	showUsers: boolean;
	diet: any;
	starsCount: any[];
	searchTags: any;
	picture: any

	@ViewChild("fileInput") fileInput: any;
	recipes: Recipe;

	constructor(private userService: UserService) {
		this.showUsers = false;
		this.userService = userService;
		this.picture = false;
		this.requestSent = false;

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

	withPicture() {
		if (this.picture == false) {
			this.picture = true
		}
		else {
			this.picture = false
		}
	}


	selectDietPlan() {
		console.log(this.diet)
		this.userService.selectDietPlan(this.diet).subscribe(res => {
			console.log(res);
		});	
	} 

	recipesWithKeyWords(searchTag: any) {
		this.userService.searchRecipesWithTags(searchTag).subscribe(res => {
			console.log(res);
		});
		console.log(searchTag)
	}

	recipesWithPicture(): void {
		this.requestSent =  true;
	    let fi = this.fileInput.nativeElement;
	    if (fi.files && fi.files[0]) {
	        let fileToUpload = fi.files[0];
	        this.userService
	            .searchRecipesWithPicture(fileToUpload)
				.subscribe(value => {
    				this.recipes = <Recipe>value.json();
    				this.requestSent = false
    				console.log(this.recipes.content)
	    		});
		}
	}	

	setRecipeRating(recipeRating: number, recipeId: number) :void {
		this.userService.setRecipeRating(recipeRating, recipeId)
						.subscribe(res => {
							console.log(res);
						});
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

