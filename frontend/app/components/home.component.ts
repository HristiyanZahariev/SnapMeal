import { Component, ViewChild } from '@angular/core';
import { UserService } from '../services/user.service';
import { RecipeService } from '../services/recipe.service';
import { AuthService } from '../services/auth.service';
import { AuthGuard } from '../services/auth-guard.service';
import { AuthHttp, tokenNotExpired } from 'angular2-jwt';
import {RatingModule} from "ngx-rating";
import { TagInputModule } from 'ng2-tag-input';
import {MdButtonModule} from '@angular/material';
import {MdProgressSpinnerModule} from '@angular/material';
import {MdGridListModule} from '@angular/material';
import {FileUploaderComponent} from './file-uploader.component';
import { CarouselModule } from 'ng2-bootstrap'

@Component({
  moduleId: module.id,
  selector: 'user',
  templateUrl: 'home.component.html',	
  providers: [
  	[UserService],
  	[AuthGuard],
  	[RecipeService]
  ]
})
export class HomeComponent  { 
	user :any;
	requestSent: boolean;
	showUsers: boolean;
	diet: any;
	response: RecipeAPI[];
	recipesToLike: RecipeAPI[];
	starsCount: any[];
	searchTags: any;
	picture: any
	loading: boolean = false;
	errors: boolean = false;

	@ViewChild("fileInput") fileInput: any;
	recipes: Recipe;

	constructor(private userService: UserService, private recipeService: RecipeService) {
		this.showUsers = false;
		this.userService = userService;
		this.picture = false;
		this.requestSent = false;
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

	ngOnInit() {
		this.loading = true;
		this.recipeService.getRecommendedRecipes().subscribe(
			(data: any) => {
				this.response = <RecipeAPI[]>data.json();
				this.loading = false;
			},
			(err: any) => { 
				this.errors = true;
				this.loading = false;
				console.log(this.errors)
			}
		);
	}

	getRandomRecipes() {
		this.recipeService.getRandomRecipes().subscribe(
			(data: any) => {
				this.recipesToLike = <RecipeAPI[]>data.json();
				this.loading = false;
				console.log(this.recipesToLike);
			},
			(err: any) => { 
				this.errors = true;
				this.loading = false;
				console.log(this.errors)
			}
		);
	}
}

export interface Ingredient {
    id: number;
    name: string;
}

export interface Recipe {
    id: number;
    name: string;
    description: string;
    author?: any;
    ingredients: Ingredient[];
}

export interface RecipeAPI {
    recipe: Recipe;
    rating: number;
    searchedFor: string;
}

