import { Component, ViewChild, Input, AfterViewChecked } from '@angular/core';
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


@Component({
  moduleId: module.id,
  selector: 'liked-recipes',
  templateUrl: 'liked-recipes.component.html',
  providers: [UserService]
})

export class LikedRecipes  { 

    @Input("user_id") 
    userId: number;
    recipes: Recipe;


	constructor(private userService: UserService) {
		this.userService = userService
	}

	public ngAfterViewInit	(): void {
		this.userService.getLikedRecipes().subscribe(res => {
			this.recipes = <Recipe>value.json()
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


