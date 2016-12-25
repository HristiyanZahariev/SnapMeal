package com.snapmeal.entity;

import com.snapmeal.entity.enums.IngredientType;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

/**
 * Created by hristiyan on 16.12.16.
 */
@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private IngredientType type;

    private int protein;
    private int fats;
    private int carbs;
    private int calories;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "ingredients")
    private Collection<Recipe> recipes;

    public Ingredient(String name, IngredientType type, int protein, int fats, int carbs, int calories, Collection<Recipe> recipes) {
        this.name = name;
        this.type = type;
        this.protein = protein;
        this.fats = fats;
        this.carbs = carbs;
        this.calories = calories;
        this.recipes = recipes;
    }


    public Collection<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Collection<Recipe> recipes) {
        this.recipes = recipes;
    }

    public Ingredient() {}

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public IngredientType getType() {
        return type;
    }

    public int getProtein() {
        return protein;
    }

    public int getFats() {
        return fats;
    }

    public int getCarbs() {
        return carbs;
    }

    public int getCalories() {
        return calories;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(IngredientType type) {
        this.type = type;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public void setFats(int fats) {
        this.fats = fats;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ingredient that = (Ingredient) o;

        if (id != that.id) return false;
        if (protein != that.protein) return false;
        if (fats != that.fats) return false;
        if (carbs != that.carbs) return false;
        if (calories != that.calories) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return type == that.type;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + protein;
        result = 31 * result + fats;
        result = 31 * result + carbs;
        result = 31 * result + calories;
        return result;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", protein=" + protein +
                ", fats=" + fats +
                ", carbs=" + carbs +
                ", calories=" + calories +
                '}';
    }
}
