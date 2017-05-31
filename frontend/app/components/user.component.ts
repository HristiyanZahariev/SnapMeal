import { Component, ViewChild, Input, AfterViewChecked } from '@angular/core';
import { UserService } from '../services/user.service';
import { AuthService } from '../services/auth.service';
import { AuthGuard } from '../services/auth-guard.service';
import { AuthHttp, tokenNotExpired } from 'angular2-jwt';
import {RatingModule} from "ngx-rating";
import { TagInputModule } from 'ng2-tag-input';
import {MdButtonModule} from '@angular/material';
import { RecipeService } from '../services/recipe.service';
import {MdProgressSpinnerModule} from '@angular/material';
import {MdGridListModule} from '@angular/material';
import {FileUploaderComponent} from './file-uploader.component';
import { DialogRef, ModalComponent } from 'angular2-modal';
import { ModalModule } from 'ngx-bootstrap';


@Component({
  moduleId: module.id,
  selector: 'user',
  templateUrl: 'user.component.html',
  providers: [
      [UserService],
      [RecipeService]
  ]
})

export class UserComponent  { 

	user: User
	recipes: RecipeAPI;
    recipesToLike: RecipeAPI[];
	overview: boolean;
	settings: boolean;
	likedRecipes: boolean;
	diets: boolean;
    diet: string;
    loading: boolean = false
    errors: boolean = false
    recommendedRecipes: boolean



	constructor(private userService: UserService, private recipeService: RecipeService) {
		this.userService = userService
	}

	public ngOnInit	(): void {
		this.userService.getUserProfile().subscribe(res => {
			console.log(res);
			this.user = res;
		});
		this.userService.getLikedRecipes().subscribe(res => {
			this.recipes = <Recipe>res.json()
			console.log(this.recipes)
		});
    }

    selectDietPlan() {
        console.log(this.diet)
        this.userService.selectDietPlan(this.diet).subscribe(res => {
            console.log(res);
        });    
    } 

    getRandomRecipes() {
        this.recipeService.getRandomRecipes().subscribe(
            (data: any) => {
                this.recipesToLike = <RecipeAPI[]>data.json();
                this.loading = false;
            },
            (err: any) => { 
                this.errors = true;
                this.loading = false;
                console.log(this.errors)
            }
        );
    }


    userSettings() {
    	this.settings = true;
    	this.overview = false;
    	this.likedRecipes = false;
    	this.diets = false;
        this.recommendedRecipes = false;
    }

    userRecommendedRecipes() {
        this.settings = false;
        this.overview = false;
        this.likedRecipes = false;
        this.diets = false;
        this.recommendedRecipes = true;
    }

    userOverview() {
    	this.overview = true;
    	this.settings = false;
    	this.likedRecipes = false;
    	this.diets = false;
        this.recommendedRecipes = false;

    }

    userLikedRecipes() {
    	this.likedRecipes = true;
    	this.overview = false;
    	this.settings = false;
		this.diets = false;
        this.recommendedRecipes = false;    	
    }

    userDiets() {
    	this.diets = true;
    	this.likedRecipes = false;
    	this.overview = false;
    	this.settings = false;
        this.recommendedRecipes = false;
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

