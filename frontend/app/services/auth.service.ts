import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/map';
import { tokenNotExpired } from 'angular2-jwt';
import { Router } from '@angular/router';

@Injectable()
export class AuthService {

  url = "http://localhost:8080/snapmeal"
  ifError: boolean = false;
  constructor(private http: Http, private router: Router) {}

  login(user: any) {
    this.http.post(this.url + '/login', user)
      .subscribe(
        // We're assuming the response will be an object
        // with the JWT on an id_token key
        (data) => {
          let headers = data.headers;
          let jjwt = headers.get("X-AUTH-TOKEN");
          localStorage.setItem('id_token', jjwt);
          this.router.navigateByUrl('');
        },
        (err) => {
           this.ifError = true;
        });

        //not sure if this gotta be here
  }

  loggedIn() {
      return tokenNotExpired();
  }

  logout() {
    localStorage.removeItem('id_token'); 
  }
}
