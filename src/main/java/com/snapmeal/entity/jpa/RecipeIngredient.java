package com.snapmeal.entity.jpa;

import javax.persistence.*;

/**
 * Created by hristiyan on 29.05.17.
 */
@Entity
@Table(name = "recipe_ingredient")
@AssociationOverrides({
        @AssociationOverride(name = "pk.recipe",
                joinColumns = @JoinColumn(name = "recipe_id")),
        @AssociationOverride(name = "pk.ingredient",
                joinColumns = @JoinColumn(name = "ingredient_id")) })
public class RecipeIngredient implements java.io.Serializable {

    @EmbeddedId
    private RecipeIngredientId pk = new RecipeIngredientId();
    private String amount;
    private String carbs;
    private String fats;
    private String proteins;

    public RecipeIngredient() {
    }

    public RecipeIngredientId getPk() {
        return pk;
    }

    public void setPk(RecipeIngredientId pk) {
        this.pk = pk;
    }

    @Transient
    public Recipe getRecipe() {
        return getPk().getRecipe();
    }

    public void setRecipe(Recipe recipe) {
        getPk().setRecipe(recipe);
    }

    @Transient
    public Ingredient getIngredient() {
        return getPk().getIngredient();
    }

    public void setIngredient(Ingredient ingredient) {
        getPk().setIngredient(ingredient);
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCarbs() {
        return carbs;
    }

    public void setCarbs(String carbs) {
        this.carbs = carbs;
    }

    public String getFats() {
        return fats;
    }

    public void setFats(String fats) {
        this.fats = fats;
    }

    public String getProteins() {
        return proteins;
    }

    public void setProteins(String proteins) {
        this.proteins = proteins;
    }
}
