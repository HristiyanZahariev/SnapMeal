import { Component } from '@angular/core';
import { UserService } from '../services/user.service';

@Component({
    moduleId: module.id,
    selector: 'file-uploader',
    templateUrl: 'file-uploader.component.html',
    inputs:['activeColor','baseColor','overlayColor']
})
export class FileUploaderComponent {
    
    constructor(private userService: UserService) {
        this.userService = userService;
    }

    activeColor: string = 'green';
    baseColor: string = '#ccc';
    overlayColor: string = 'rgba(255,255,255,0.5)';
    recipes: Recipe
    dragging: boolean = false;
    requestSent: boolean = false;
    loaded: boolean = false;
    imageLoaded: boolean = false;
    imageSrc: string = '';
    
    handleDragEnter() {
        this.dragging = true;
        console.log("bumyes")
    }
    
    handleDragLeave() {
        this.dragging = false;
    }
    
    handleDrop(e: any) {
        e.preventDefault();
        this.dragging = false;
        this.handleInputChange(e);
    }
    
    handleImageLoad() {
        this.imageLoaded = true;
        //this.iconColor = this.overlayColor;
    }

    handleInputChange(e: any) {
        var file = e.dataTransfer ? e.dataTransfer.files[0] : e.target.files[0];

        var pattern = /image-*/;
        var reader = new FileReader();

        if (!file.type.match(pattern)) {
            alert('invalid format');
            return;
        }

        this.loaded = false;

        reader.onload = this._handleReaderLoaded.bind(this);
        reader.readAsDataURL(file);
        this.userService
            .searchRecipesWithPicture(file)
            .subscribe(value => {
                this.recipes = <Recipe>value.json();
                this.requestSent = false
                console.log(this.recipes.content)
            });
    }

    
    _handleReaderLoaded(e: any) {
        var reader = e.target;
        this.imageSrc = reader.result;
        this.loaded = true;
    }
    
    // _setActive() {
    //     this.borderColor = this.activeColor;
    //     if (this.imageSrc.length === 0) {
    //         this.iconColor = this.activeColor;
    //     }
    // }
    
    // _setInactive() {
    //     this.borderColor = this.baseColor;
    //     if (this.imageSrc.length === 0) {
    //         this.iconColor = this.baseColor;
    //     }
    // }
    
}

export interface Ingredient {
    id: string;
    name: string;
}

export interface Content {
    id: string;
    name: string;
    description: string;
    rating: number;
    ingredient: Ingredient[];
}

export interface Recipe {
    content: Content[];
    last: boolean;
    totalPages: number;
    totalElements: number;
    first: boolean;
    sort?: any;
    numberOfElements: number;
    size: number;
    number: number;
}

