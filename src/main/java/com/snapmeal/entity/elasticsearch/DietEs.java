package com.snapmeal.entity.elasticsearch;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

/**
 * Created by hristiyan on 05.02.17.
 */
@Document(indexName = "snapmeal", type = "diets")
public class DietEs {
    @Id
    private String id;
    private String name;
    private String description;
    @Field( type = FieldType.Nested)
    @JsonProperty("ingredient")
//    private List<DietEs> diet;
    private List<IngredientEs> ingredient;

    public DietEs(String name) {
        this.name = name;
    }

    public DietEs() {
    }

    public List<IngredientEs> getIngredient() {
        return ingredient;
    }

    public void setIngredient(List<IngredientEs> ingredient) {
        this.ingredient = ingredient;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DietEs dietEs = (DietEs) o;

        if (name != null ? !name.equals(dietEs.name) : dietEs.name != null) return false;
        return description != null ? description.equals(dietEs.description) : dietEs.description == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
