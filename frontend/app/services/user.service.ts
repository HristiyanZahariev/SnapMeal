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

	getUserProfile() {
		return this.authHttp.get('http://localhost:8080/snapmeal/user/profile')
			.map(res => res.json())
	}
}