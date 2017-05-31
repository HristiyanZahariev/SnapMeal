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
	recipes: RecipeAPI[];
    recipesToLike: RecipeAPI[];
    recommendedRecipes: RecipeAPI[];
    diets: Diet[];
	overview: boolean = true;
	settings: boolean;
	likedRecipes: boolean;
	userDiets: boolean;
    diet: string = "";
    loading: boolean = false
    errors: boolean = false
    recommendedRecipe: boolean
    recommendedRecipesFailed: boolean = false;



	constructor(private userService: UserService, private recipeService: RecipeService) {
		this.userService = userService
	}

	public ngOnInit	(): void {
		this.userService.getUserProfile().subscribe(res => {
			this.user = res;
            console.log(this.user)
		});

        this.userService.getCurrentDietPlan().subscribe(res => {
            this.diet = res.text();
        })
		this.userService.getLikedRecipes().subscribe(res => {
			this.recipes = <RecipeAPI[]>res.json()
			if (this.recipes.length == 0) {
                this.errors = true;
            }
		});
        this.userService.getAllDiets().subscribe(res => {
            this.diets = <Diet[]>res.json()
            console.log(this.diets)
        })

        this.recipeService.getRecommendedRecipes().subscribe(
            (data: any) => {
                this.recommendedRecipes = <RecipeAPI[]>data.json();
                this.loading = false;
            },
            (err: any) => { 
                this.recommendedRecipesFailed = true;
                this.loading = false;
                this.errors = true
            }
        );
    }

    selectDietPlan(userDiet: string) {
        console.log(this.diet)
        this.userService.selectDietPlan(userDiet).subscribe(res => {
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
    	this.userDiets = false;
        this.recommendedRecipe = false;
    }

    userRecommendedRecipes() {
        this.settings = false;
        this.overview = false;
        this.likedRecipes = false;
        this.userDiets = false;
        this.recommendedRecipe = true;
    }

    userOverview() {
    	this.overview = true;
    	this.settings = false;
    	this.likedRecipes = false;
    	this.userDiets = false;
        this.recommendedRecipe = false;

    }

    userLikedRecipes() {
    	this.likedRecipes = true;
    	this.overview = false;
    	this.settings = false;
		this.userDiets = false;
        this.recommendedRecipe = false;    	
    }

    userDietsFunc() {
    	this.userDiets = true;
    	this.likedRecipes = false;
    	this.overview = false;
    	this.settings = false;
        this.recommendedRecipe = false;
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
}

export interface Diet {
    id: number;
    name: string;
    description: string;
}
