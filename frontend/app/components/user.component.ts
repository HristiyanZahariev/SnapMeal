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
  selector: 'user',
  templateUrl: 'user.component.html',
  providers: [UserService]
})

export class UserComponent  { 

	user: User

	constructor(private userService: UserService) {
		this.userService = userService
	}

	public ngAfterViewInit	(): void {
		this.userService.getUserProfile().subscribe(res => {
			console.log(res);
			this.user = res;
		});      
    }   

}

export interface User {
    username: string;
    email: string;
    firstname?: any;
    lastname?: any;
    ratings: any[];
}


