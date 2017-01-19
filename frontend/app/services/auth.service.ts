import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/map';
import { tokenNotExpired } from 'angular2-jwt';

@Injectable()
export class AuthService {

  constructor(private http: Http) {}

  login(user: any) {
    this.http.post('http://localhost:8080/snapmeal/login', user)
      .subscribe((res) => {
        // We're assuming the response will be an object
        // with the JWT on an id_token key
        console.log(res.headers);
        let headers = res.headers;
        console.log(headers.get("X-AUTH-TOKEN"));
        let jjwt = headers.get("X-AUTH-TOKEN");
        localStorage.setItem('id_token', jjwt);
        // data => console.log(data),
        // error => console.log(error)
      });
  }

  loggedIn() {
      return tokenNotExpired();
  }

  logout() {
    localStorage.removeItem('id_token'); 
  }
}