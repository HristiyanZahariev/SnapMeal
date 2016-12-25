package com.snapmeal.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by hristiyan on 25.12.16.
 */
@Entity
public class Diet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "diets")
    private Set<Recipe> recipes;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "diets")
    private Set<User> users;

    public Diet(String name, Set<Recipe> recipes) {
        this.name = name;
        this.recipes = recipes;
    }

    public Diet() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Diet diet = (Diet) o;

        if (id != diet.id) return false;
        if (name != null ? !name.equals(diet.name) : diet.name != null) return false;
        return recipes != null ? recipes.equals(diet.recipes) : diet.recipes == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (recipes != null ? recipes.hashCode() : 0);
        return result;
    }
}