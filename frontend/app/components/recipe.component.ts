import { Component, ViewChild, Input } from '@angular/core';
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
import { Router } from '@angular/router';
import { ModalModule } from 'ngx-bootstrap';

@Component({
  moduleId: module.id,
  selector: 'recipe',
  templateUrl: 'recipe.component.html',
  providers: [RecipeService]
})
export class RecipeComponent  { 
    requestSent: boolean;
    diet: any;
    starsCount: any[];
    searchTags: any;
    picture: any
    @Input("recipes") 
    response: RecipeAPI;

    constructor(private recipeService: RecipeService, private router: Router) {
        this.recipeService = recipeService;
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
