import { Component } from '@angular/core';
import { HeaderComponent } from './components/header.component';
import { FooterComponent } from './components/footer.component'


@Component({
  selector: 'my-app',
  template: `
  	<my-header></my-header>
  	<router-outlet></router-outlet>
  `,
})

export class AppComponent  { 
}
