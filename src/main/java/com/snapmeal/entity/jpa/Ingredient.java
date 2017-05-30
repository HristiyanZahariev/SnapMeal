package com.snapmeal.entity.jpa;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hristiyan on 16.12.16.
 */
@Entity
@JsonAutoDetect
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Ingredient implements java.io.Serializable {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.ingredient")
    private Set<RecipeIngredient> recipeIngredients;
//    private double proteins;
//    private double fats;
//    private double carbs;

//    @ManyToMany(mappedBy = "ingredient", fetch = FetchType.EAGER)
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    private Set<Recipe> recipes;


    @ManyToMany(mappedBy = "ingredients", fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Diet> diets;

    public Ingredient() {
    }

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
//
//    public Set<Recipe> getRecipes() {
//        return recipes;
//    }
//
//    public void setRecipes(Set<Recipe> recipes) {
//        this.recipes = recipes;
//    }

    public Set<Diet> getDiets() {
        return diets;
    }

    public void setDiets(Set<Diet> diets) {
        this.diets = diets;
    }

    public Set<RecipeIngredient> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(Set<RecipeIngredient> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    //
//    public String getAmount() {
//        return amount;
//    }
//
//    public void setAmount(String amount) {
//        this.amount = amount;
//    }
//
//    public double getProteins() {
//        return proteins;
//    }
//
//    public void setProteins(double proteins) {
//        this.proteins = proteins;
//    }
//
//    public double getFats() {
//        return fats;
//    }
//
//    public void setFats(double fats) {
//        this.fats = fats;
//    }
//
//    public double getCarbs() {
//        return carbs;
//    }
//
//    public void setCarbs(double carbs) {
//        this.carbs = carbs;
//    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Ingredient that = (Ingredient) o;
//
//        if (id != null ? !id.equals(that.id) : that.id != null) return false;
//        if (name != null ? !name.equals(that.name) : that.name != null) return false;
//        return recipes != null ? recipes.equals(that.recipes) : that.recipes == null;
//
//    }
//
//    @Override
//    public int hashCode() {
//        int result = id != null ? id.hashCode() : 0;
//        result = 31 * result + (name != null ? name.hashCode() : 0);
//        result = 31 * result + (recipes != null ? recipes.hashCode() : 0);
//        return result;
//    }
}
