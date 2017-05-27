import { Component, ViewChild, Input, OnInit, OnDestroy } from '@angular/core';
import { UserService } from '../services/user.service';
import { AuthService } from '../services/auth.service';
import { AuthGuard } from '../services/auth-guard.service';
import { AuthHttp, tokenNotExpired } from 'angular2-jwt';
import {RatingModule} from "ngx-rating";
import { TagInputModule } from 'ng2-tag-input';
import {MdButtonModule} from '@angular/material';
import {MdProgressSpinnerModule} from '@angular/material';
import {MdGridListModule} from '@angular/material';
import {FileUploaderComponent} from './file-uploader.component';
import { RecipeService } from '../services/recipe.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  moduleId: module.id,
  selector: 'recipe-profile',
  templateUrl: 'recipe-profile.component.html',
  providers: [RecipeService]
})
export class RecipeProfile  { 
    requestSent: boolean;
    diet: any;
    starsCount: any[];
    searchTags: any;
    picture: any
    recipe: RecipeAPI;
    id: number;
    loading = true;
    private sub: any;



    constructor(private recipeService: RecipeService, private route: ActivatedRoute) {
        this.recipeService = recipeService;
    }

    ngInit() {
        this.sub = this.route.params.subscribe(params => {
            this.id = +params['id'];
            this.recipeService.getRecipeBy(this.id).subscribe(res => {
                this.recipe = res.json();
                this.loading = false;
            })

        });
      }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }




    setRecipeRating(recipeRating: number, recipeId: number) :void {
        this.recipeService.setRecipeRating(recipeRating, recipeId).subscribe(res => {
            console.log(res);
        });
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
}