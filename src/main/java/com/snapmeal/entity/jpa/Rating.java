package com.snapmeal.entity.jpa;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by hristiyan on 22.02.17.
 */
@Entity
@IdClass(RatingId.class)
public class Rating {
    @Id
    @ManyToOne
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    private Recipe recipe;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private int value;

    public Rating() {
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
