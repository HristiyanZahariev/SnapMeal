import {Injectable} from '@angular/core';
import {Http, Headers, RequestOptions} from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()

export class UserService {
	constructor(private http: Http) {
	}

	getUsers() {
		return this.http.get('http://localhost:8080/snapmeal/user/all')
			.map(res => res.json());
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

	upload(fileToUpload: any) {
	    let input = new FormData();
	    input.append("file", fileToUpload);

	    return this.http
	        .post("http://localhost:8080/snapmeal/image/upload", input);
	}

	login(username: string, password: string) {
		//console.log(JSON.stringify({ username: username, password: password }))
    	return this.http.post('http://localhost:8080/snapmeal/login', JSON.stringify({ username: username, password: password }))
    }
}