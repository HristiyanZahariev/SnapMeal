package com.snapmeal.entity.jpa;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 * Created by hristiyan on 29.05.17.
 */
@Embeddable
public class RecipeIngredientId implements java.io.Serializable {


    @ManyToOne
    private Recipe recipe;

    @ManyToOne
    private Ingredient ingredient;

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
}
