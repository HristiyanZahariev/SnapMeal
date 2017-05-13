import {ModuleWithProviders} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

import {HomeComponent} from './components/home.component';
import {UserComponent} from './components/user.component';
import {AboutComponent} from './components/about.component';
import {RegisterComponent} from './components/register.component';
import {LoginComponent} from './components/login.component';
import { AuthGuard } from './services/auth-guard.service';
import {AuthGuardLogged} from './services/auth-guard-logged.service'

export const AppRoutes:Routes = [
	{
		path: '',
		component: HomeComponent, 
		canActivate: [AuthGuard] 
	},
	{
		path: 'about',
		component: AboutComponent
	},

	{
		path: 'register',
		component: RegisterComponent,
		canActivate: [AuthGuardLogged]
	},
	{
		path: 'login',
		component: LoginComponent
	},
	{
		path: 'profile',
		component: UserComponent,
		canActivate: [AuthGuard] 
	}

];

export const routing: ModuleWithProviders = RouterModule.forRoot(AppRoutes);