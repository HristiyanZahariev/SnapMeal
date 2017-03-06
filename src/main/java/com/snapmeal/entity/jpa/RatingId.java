package com.snapmeal.entity.jpa;

import java.io.Serializable;

/**
 * Created by hristiyan on 23.02.17.
 */
public class RatingId implements Serializable {
    private Long recipe;
    private Long user;

    public RatingId() {
    }

    public Long getRecipe() {
        return recipe;
    }

    public void setRecipe(Long recipe) {
        this.recipe = recipe;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RatingId ratingId = (RatingId) o;

        if (recipe != null ? !recipe.equals(ratingId.recipe) : ratingId.recipe != null) return false;
        return user != null ? user.equals(ratingId.user) : ratingId.user == null;

    }


    @Override
    public int hashCode() {
        int result = recipe != null ? recipe.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }
}
