package com.snapmeal.entity.jpa;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by hristiyan on 11.12.16.
 */
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "RecipeIngredient", joinColumns = { @JoinColumn(name = "recipeId") },
            inverseJoinColumns = { @JoinColumn(name = "ingredientId") })
    private Set<Ingredient> ingredient;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonProperty("diet")
    @JoinTable(name = "RecipeDiet", joinColumns = { @JoinColumn(name = "recipeId") },
            inverseJoinColumns = { @JoinColumn(name = "dietId") })
    private Set<Diet> diets;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Author author;

    public Recipe() {}

    //Made it to test the class
    public Recipe(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Recipe(String name, String description, Set<Ingredient> ingredient) {
        this.name = name;
        this.description = description;
        this.ingredient = ingredient;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Ingredient> getIngredients() {
        return ingredient;
    }

    public void setIngredients(Set<Ingredient> ingredient) {
        this.ingredient = ingredient;
    }

    public Set<Diet> getDiets() {
        return diets;
    }

    public void setDiets(Set<Diet> diets) {
        this.diets = diets;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recipe recipe = (Recipe) o;

        if (id != null ? !id.equals(recipe.id) : recipe.id != null) return false;
        if (name != null ? !name.equals(recipe.name) : recipe.name != null) return false;
        return description != null ? description.equals(recipe.description) : recipe.description == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
