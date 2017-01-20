import { Component } from '@angular/core';
import { HeaderComponent } from './components/header.component'

@Component({
  selector: 'my-app',
  template: `
  <header></header>
  <router-outlet></router-outlet>
  `,
})

export class AppComponent  { 
}
