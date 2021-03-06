import { Component, ViewChild } from '@angular/core';
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

    response: RecipeAPI[];
    @ViewChild("fileInput") fileInput: any;
    typing: boolean = true;
    dropping: boolean = false;
    dragging: boolean = false;
    requestSent: boolean = false;
    loaded: boolean = false;
    imageLoaded: boolean = false;
    contentLoaded: boolean = false;
    imageSrc: string = '';
    searchTags: any;
    searchedFor: string;
    from: number = 1;
    to: number = 10;
    searchingTag: any;
    recipesWithTags: boolean = false;
    recipesWithImage: boolean = false;

    
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

    submitPicture() {
        let fi = this.fileInput.nativeElement;
        if (fi.files && fi.files[0]) {
            let fileToUpload = fi.files[0];
            this.recipeService
                .searchRecipesWithPicture(fileToUpload)
                .subscribe(value => {
                    this.response = <RecipeAPI[]>value.json();
                    console.log(this.response)
                    this.response.forEach((recipe: any) => {
                        this.searchedFor = recipe.searchedFor;
                        this.contentLoaded = true
                    });
                    console.log(this.searchedFor);
                });
        }
    }

    ifMobile() { 
        if (window.innerWidth <= 800 && window.innerHeight <= 600){ return true; } 
        else {
            return false; 
    }    
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
        this.requestSent = true
        this.recongizedFile = file;
        reader.readAsDataURL(file)
        this.recipeService
            .searchRecipesWithPicture(file)
            .subscribe(value => {
                this.searchedFor = undefined;
                this.response = <RecipeAPI[]>value.json();
                console.log(this.response)
                this.response.forEach((recipe: any) => {
                    this.searchedFor = recipe.searchedFor;
                });
                this.contentLoaded = true
                console.log(this.searchedFor);
                this.recipesWithImage = true;
            });
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
        this.searchingTag = searchTag;
        this.requestSent = true
        this.recipesWithTags = true;
        this.recipeService.searchRecipesWithTags(searchTag)
             .subscribe(value => {
                this.searchedFor = undefined;
                this.response = <RecipeAPI[]>value.json();
                console.log(this.response)
                this.contentLoaded = true
                console.log(this.searchedFor);
                this.requestSent = false;
                this.contentLoaded = false;
                this.recipesWithTags = true
            });
    }

    // getMoreRecipesWithImage() {
    //     this.recipesWithImage = true;
    //     this.from += 10;
    //     this.to += 10
    //     this.recipeService.searchRecipesWithPicture(this.recongizedFile, this.from, this.to)
    //         .subscribe(value => {
    //             this.response = <RecipeAPI[]>value.json();
    //             console.log(this.response)  
    //         });

    // }

    // getMoreRecipesWithTags() {
    //     this.recipesWithTags = true;
    //     this.from += 10;
    //     this.to += 10
    //     this.recipeService.searchRecipesWithTags(this.searchingTag, this.from, this.to)
    //         .subscribe(value => {
    //             this.response = <RecipeAPI[]>value.json();
    //             console.log(this.response)  
    //         });
    // }
    
}

export interface Ingredient {
    id: number;
    name: string;
}

export interface Recipe {
    id: number;
    name: string;
    description: string;
    author?: any;
    ingredients: Ingredient[];
}

export interface RecipeAPI {
    recipe: Recipe;
    rating: number;
    searchedFor: string;
}

