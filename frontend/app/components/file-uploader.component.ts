import { Component } from '@angular/core';
import { RecipeService } from '../services/recipe.service';

@Component({
    moduleId: module.id,
    selector: 'file-uploader',
    templateUrl: 'file-uploader.component.html',
    providers: [RecipeService]
})
export class FileUploaderComponent {
    
    constructor(private recipeService: RecipeService) {
        this.recipeService = recipeService;
    }

    recipes: Recipe
    typing: boolean = true;
    dropping: boolean = false;
    dragging: boolean = false;
    requestSent: boolean = false;
    loaded: boolean = false;
    imageLoaded: boolean = false;
    imageSrc: string = '';
    searchTags: any;
    
    handleDragEnter() {
        this.dragging = true;
    }
    
    handleDragLeave() {
        this.dragging = false;
    }
    
    handleDrop(e: any) {
        this.dropping = true;
        e.preventDefault();
        this.dragging = false;
        this.typing = false;
        this.handleInputChange(e);
    }
    
    handleImageLoad() {
        this.imageLoaded = true;
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
        // this.recipeService
        //     .searchRecipesWithPicture(file)
        //     .subscribe(value => {
        //         this.recipes = <Recipe>value.json();
        //         this.requestSent = false
        //         console.log(this.recipes)
        //     });
    }

    handleTyping(e: any) {
        e.preventDefault();
        this.typing = true;
        this.dropping = false;
        console.log("working?")

    }
    
    _handleReaderLoaded(e: any) {
        var reader = e.target;
        this.imageSrc = reader.result;
        this.loaded = true;
    }

    recipesWithKeyWords(searchTag: any) {
        this.recipeService.searchRecipesWithTags(searchTag).subscribe(res => {
            console.log(res);
        });
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

