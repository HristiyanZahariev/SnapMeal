package com.snapmeal.entity.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by hristiyan on 20.05.17.
 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RecipeAPI {

    private Recipe recipe;
    private List<IngredientAPI> ingredients;
    private Double rating;
    private String searchedFor;

    public RecipeAPI() {
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public Double getRating() {
        return rating;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getSearchedFor() {
        return searchedFor;
    }

    public void setSearchedFor(String searchedFor) {
        this.searchedFor = searchedFor;
    }

    public List<IngredientAPI> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientAPI> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecipeAPI recipeAPI = (RecipeAPI) o;

        if (recipe != null ? !recipe.equals(recipeAPI.recipe) : recipeAPI.recipe != null) return false;
        return rating != null ? rating.equals(recipeAPI.rating) : recipeAPI.rating == null;

    }

    @Override
    public int hashCode() {
        int result = recipe != null ? recipe.hashCode() : 0;
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        return result;
    }
}
