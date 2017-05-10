import {Injectable} from '@angular/core';
import {Http, Headers, RequestOptions} from '@angular/http';
import { AuthHttp, tokenNotExpired } from 'angular2-jwt';
import 'rxjs/add/operator/map';

@Injectable()

export class UserService {

	body: any;

	constructor(private http: Http, private authHttp: AuthHttp) {
	}

	getCurrentUser() {
		return this.authHttp.get('http://localhost:8080/snapmeal/user/current')
			.map(res => res.json())

	}

	createUser(user: any) {
		let body = JSON.stringify(user);
		let headers = new Headers({ 'Content-Type': 'application/json' });
		let options = new RequestOptions({ headers: headers });
		// let options = new RequestOptions({ headers: headers });
		let url='http://localhost:8080/snapmeal/user/register';
		   // let body = JSON.stringify({'username': user.username, 'password': user.password, 'email': user.email});
		return this.http.post(url, body, options).map(res =>  res.json().data);
	}

	selectDietPlan(diet: string) {
		console.log(diet)
		let url = 'http://localhost:8080/snapmeal/user/diet?diet='
		console.log(url+diet);
		this.body = null;
		return this.authHttp.post(url+diet, this.body);
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

	searchRecipesWithTags(userInput: any[]) {
		let body = JSON.stringify(userInput);
		let headers = new Headers({ 'Content-Type': 'application/json' });
		let options = new RequestOptions({ headers: headers });
		let url = "http://localhost:8080/snapmeal/recipe/search"
		return this.authHttp.post(url, body, options);
	}
}