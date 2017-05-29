package com.snapmeal.entity.jpa;

/**
 * Created by hristiyan on 20.05.17.
 */
public class RecipeAPI {

    private Recipe recipe;
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
