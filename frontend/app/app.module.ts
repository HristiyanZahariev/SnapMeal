import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import { ActivatedRoute } from '@angular/router';
import { CarouselModule } from 'ng2-bootstrap'
import { AuthHttp, AuthConfig, AUTH_PROVIDERS, provideAuth } from 'angular2-jwt';
import { CollapseDirective } from 'ng2-bootstrap'
import { ModalModule } from 'ng2-bootstrap';
import { AppComponent }  from './app.component';
import { HomeComponent }  from './components/home.component';
import { AboutComponent }  from './components/about.component';
import { RegisterComponent } from './components/register.component';
import { LoginComponent } from './components/login.component';
import { HeaderComponent } from './components/header.component';
import { FooterComponent } from './components/footer.component';
import {routing} from './app.routing';
import { AuthService } from './services/auth.service';
import { AuthGuard } from './services/auth-guard.service';
import { AuthGuardLogged } from './services/auth-guard-logged.service';
import {RatingModule} from "ngx-rating";
import { TagInputModule } from 'ng2-tag-input';
import {MdButtonModule, MdCheckboxModule} from '@angular/material';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MdProgressSpinnerModule} from '@angular/material';
import {MdGridListModule} from '@angular/material';
import {FileUploaderComponent} from './components/file-uploader.component';
import {RecipeComponent} from './components/recipe.component';
import {RecipeProfile} from './components/recipe-profile.component'
import {UserComponent} from './components/user.component';
import {EqualValidator} from './directives/equal-validator.directive';
import {MdProgressBarModule} from '@angular/material';


@NgModule({
  imports:      [ BrowserModule, FormsModule, ModalModule.forRoot(), CarouselModule.forRoot(), HttpModule, routing, RatingModule, TagInputModule, MdButtonModule, MdCheckboxModule, BrowserAnimationsModule, MdProgressSpinnerModule, MdGridListModule, MdProgressBarModule ],
  declarations: [ AppComponent, HomeComponent, AboutComponent, RegisterComponent, LoginComponent, HeaderComponent, FooterComponent, FileUploaderComponent, RecipeComponent, RecipeProfile, UserComponent, EqualValidator],
  bootstrap:    [ AppComponent ],
  providers: [ 
  	AuthService, 
  	AuthGuard,
    AuthGuardLogged,
    AuthHttp,
    provideAuth({
        headerName: 'X-AUTH-TOKEN',
        headerPrefix: '',
        tokenName: 'token',
        tokenGetter: (() => localStorage.getItem('id_token')),
        globalHeaders: [{ 'Content-Type': 'application/json' }],
        noJwtError: true
    })
  ]
})
export class AppModule { }