package com.snapmeal.entity;

import com.snapmeal.entity.jpa.Ingredient;
import com.snapmeal.entity.jpa.Recipe;
import com.snapmeal.entity.jpa.RecipeIngredient;

import javax.persistence.Id;
import java.util.List;

/**
 * Created by hristiyan on 30.05.17.
 */
public class RecipeJsonApi {

    private Long id;

    private String name;
    private String description;

    private String imageUrl;
    private int servings;

    private List<RecipeIngredient> recipeIngredients;
    private List<Ingredient> ingredient;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public List<RecipeIngredient> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(List<RecipeIngredient> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public List<Ingredient> getIngredient() {
        return ingredient;
    }

    public void setIngredient(List<Ingredient> ingredient) {
        this.ingredient = ingredient;
    }
}
