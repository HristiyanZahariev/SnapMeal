import { Component} from '@angular/core';


@Component({
    moduleId: module.id,
    templateUrl: 'footer.component.html',
    selector: 'my-footer'
})

export class FooterComponent {
    ifMobile() { 
        if (window.innerWidth <= 800 && window.innerHeight <= 600){ return true; } 
        else {
            return false; 
    	}
    } 
}