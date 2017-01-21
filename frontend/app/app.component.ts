import { Component } from '@angular/core';
import { HeaderComponent } from './components/header.component';
import { FooterComponent } from './components/footer.component'

@Component({
  selector: 'my-app',
  template: `
  <header></header>
  <router-outlet></router-outlet>
  <footer></footer>
  `,
})

export class AppComponent  { 
}
