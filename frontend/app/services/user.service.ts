import {Injectable} from '@angular/core';
import {Http, Headers, RequestOptions} from '@angular/http';
import { AuthHttp, tokenNotExpired } from 'angular2-jwt';
import 'rxjs/add/operator/map';

@Injectable()

export class UserService {
	constructor(private authHttp: AuthHttp) {
	}

	getUsers() {
		return this.authHttp.get('http://localhost:8080/snapmeal/user/all')
			.map(res => res.json())

	}

	createUser(user: any) {
		let body = JSON.stringify(user);
		let headers = new Headers({ 'Content-Type': 'application/json' });
		let options = new RequestOptions({ headers: headers });
		// let options = new RequestOptions({ headers: headers });
		let url='http://localhost:8080/snapmeal/user/register';
		   // let body = JSON.stringify({'username': user.username, 'password': user.password, 'email': user.email});
		return this.authHttp.post(url, body, options).map(res =>  res.json().data);

	}

	upload(fileToUpload: any) {
	    let input = new FormData();
	    input.append("file", fileToUpload);

	    return this.authHttp
	        .post("http://localhost:8080/snapmeal/image/upload", input);
	}

	login(username: string, password: string) {
		//console.log(JSON.stringify({ username: username, password: password }))
    	return this.authHttp.post('http://localhost:8080/snapmeal/login', JSON.stringify({ username: username, password: password }))
    }
}