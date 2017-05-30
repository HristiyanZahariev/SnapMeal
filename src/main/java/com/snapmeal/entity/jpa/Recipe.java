package com.snapmeal.entity.jpa;


import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.jvnet.hk2.config.Element;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by hristiyan on 11.12.16.
 */
@Entity
@JsonAutoDetect
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Recipe implements Serializable {

    @Id
    private Long id;

    private String name;
    private String description;

    private String imageUrl;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.recipe", cascade=CascadeType.ALL)
    private Set<RecipeIngredient> recipeIngredients = new HashSet<RecipeIngredient>(0);

//    @JsonProperty(value="ingredient", access = JsonProperty.Access.WRITE_ONLY)
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "RecipeIngredient", joinColumns = { @JoinColumn(name = "recipeId") },
//            inverseJoinColumns = { @JoinColumn(name = "ingredientId") })
//    private Set<Ingredient> ingredient;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.EAGER)
    private Author author;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //@JsonManagedReference("recipe-rating")
    private Set<Rating> ratings;

    private int servings;

    public Recipe() {}

    //Made it to test the class
    public Recipe(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Recipe(String name, String description, Set<Ingredient> ingredient) {
        this.name = name;
        this.description = description;
        //this.ingredient = ingredient;
    }

    public Set<RecipeIngredient> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(Set<RecipeIngredient> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
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

//    public Set<Ingredient> getIngredients() {
//        return ingredient;
//    }
//
//    public void setIngredients(Set<Ingredient> ingredient) {
//        this.ingredient = ingredient;
//    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Set<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
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
}
