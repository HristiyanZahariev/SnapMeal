package com.snapmeal.entity.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.data.annotation.*;

import javax.persistence.*;
import javax.persistence.Id;
import java.util.Collection;
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

    @JsonIgnore
    @OneToMany(mappedBy = "diet", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<User> users;

    @JsonProperty("ingredient")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "DietIngredient", joinColumns = { @JoinColumn(name = "dietId") },
            inverseJoinColumns = { @JoinColumn(name = "ingredientId") })
    private Collection<Ingredient> ingredients;

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


    public Collection<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Collection <Ingredient> ingredients) {
        this.ingredients = ingredients;
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
        return ingredients != null ? ingredients.equals(diet.ingredients) : diet.ingredients == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (users != null ? users.hashCode() : 0);
        return result;
    }
}