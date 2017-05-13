package com.snapmeal.entity.jpa;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by hristiyan on 22.02.17.
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
@IdClass(RatingId.class)
public class Rating {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    //@JsonBackReference("recipe-rating")
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    private Recipe recipe;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    //@JsonBackReference("user-rating")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private float value;

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

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rating rating = (Rating) o;

        if (Float.compare(rating.value, value) != 0) return false;
        if (recipe != null ? !recipe.equals(rating.recipe) : rating.recipe != null) return false;
        return user != null ? user.equals(rating.user) : rating.user == null;

    }

    @Override
    public int hashCode() {
        int result = recipe != null ? recipe.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (value != +0.0f ? Float.floatToIntBits(value) : 0);
        return result;
    }
}
