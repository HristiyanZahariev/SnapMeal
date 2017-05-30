import {Injectable} from '@angular/core';
import {Http, Headers, RequestOptions} from '@angular/http';
import { AuthHttp, tokenNotExpired } from 'angular2-jwt';
import 'rxjs/add/operator/map';

@Injectable()

export class RecipeService {

	body: any;

	constructor(private http: Http, private authHttp: AuthHttp) {
	}
	// formData doesnt support authHttp 
	searchRecipesWithPicture(imageToUpload: any) {
		let input = new FormData();
		input.append("image", imageToUpload);
		let headers = new Headers();
		headers.set('X-AUTH-TOKEN', localStorage.getItem('id_token'));
		let options = new RequestOptions({ headers: headers });

		return this.http
					.post("http://localhost:8080/snapmeal/image/upload", input, options);
	}

	setRecipeRating(recipeRating: number, recipeId: number) {
		let url = "http://localhost:8080/snapmeal/recipe/rating?recipe_id=" + recipeId + "&rating=" + recipeRating;
		console.log(url);
		this.body = null;
		return this.authHttp.post(url, this.body);
	}

	getRecipeBy(id: number) {
		let url = "http://localhost:8080/snapmeal/recipe/" + id;
		return this.authHttp.get(url);
	}

	getRecommendedRecipes() {
		let url = "http://localhost:8080/snapmeal/recipe/recommend";
		return this.authHttp.get(url);
	}

	searchRecipesWithTags(userInput: any[]) {
		let body = JSON.stringify(userInput);
		let headers = new Headers({ 'Content-Type': 'application/json' });
		let options = new RequestOptions({ headers: headers });
		let url = "http://localhost:8080/snapmeal/recipe/search"
		return this.authHttp.post(url, body, options);
	}

	getRecipeProfile(recipeId: number) {
		let url = "http://localhost:8080/snapmeal/recipe/" + recipeId;
		return this.authHttp.get(url);
	}
}