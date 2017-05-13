package com.snapmeal.entity.jpa;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by hristiyan on 25.12.16.
 */
@Entity
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String nationality;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "author")
    private Set<Recipe> recipes;

    public Author(String name, String nationality) {
        this.name = name;
        this.nationality = nationality;
    }

    public Author() {} //JPA

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public long getId() {
        return id;
    }

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        if (name != null ? !name.equals(author.name) : author.name != null) return false;
        return nationality != null ? nationality.equals(author.nationality) : author.nationality == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (nationality != null ? nationality.hashCode() : 0);
        return result;
    }

}