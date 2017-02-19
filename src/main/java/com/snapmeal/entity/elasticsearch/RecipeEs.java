package com.snapmeal.entity.elasticsearch;



import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;


/**
 * Created by hristiyan on 11.12.16.
 */
@Document(indexName = "snapmeal", type = "recipeTests")
public class RecipeEs {

    @Id
    private String id;

    private String name;
    private String description;

    @Field( type = FieldType.Nested)
    @JsonProperty("ingredient")
//    private List<DietEs> diet;
    private List<IngredientEs> ingredient;

    public RecipeEs(String name, String description, List<DietEs>  diet) {
        this.name = name;
        this.description = description;
//        this.diet = diet;
    }

    public RecipeEs() {
    }

    public List<IngredientEs> getIngredient() {
        return ingredient;
    }

    public void setIngredient(List<IngredientEs> ingredient) {
        this.ingredient = ingredient;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

}
