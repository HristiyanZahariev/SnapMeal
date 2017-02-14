package com.snapmeal.entity.jpa;

import org.springframework.data.annotation.*;

import javax.persistence.*;
import javax.persistence.Id;
import java.util.Set;

/**
 * Created by hristiyan on 25.12.16.
 */
@Entity
public class Diet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "diet", cascade = CascadeType.ALL)
    private Set<User> users;

    @ManyToMany(mappedBy = "diet")
    private Set<Recipe> recipes;

    public Diet() {
    }

    public Diet(String name) {
        this.name = name;
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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
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

        if (id != null ? !id.equals(diet.id) : diet.id != null) return false;
        if (name != null ? !name.equals(diet.name) : diet.name != null) return false;
        if (description != null ? !description.equals(diet.description) : diet.description != null) return false;
        if (users != null ? !users.equals(diet.users) : diet.users != null) return false;
        return recipes != null ? recipes.equals(diet.recipes) : diet.recipes == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (users != null ? users.hashCode() : 0);
        result = 31 * result + (recipes != null ? recipes.hashCode() : 0);
        return result;
    }
}