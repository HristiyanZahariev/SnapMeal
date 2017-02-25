package com.snapmeal.entity.elasticsearch;



import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Field(type = FieldType.Nested)
    private List<RatingEs> ratings;

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

    public List<RatingEs> getRatings() {
        return ratings;
    }

    public void setRatings(List<RatingEs> ratings) {
        this.ratings = ratings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecipeEs recipeEs = (RecipeEs) o;

        if (id != null ? !id.equals(recipeEs.id) : recipeEs.id != null) return false;
        if (name != null ? !name.equals(recipeEs.name) : recipeEs.name != null) return false;
        if (description != null ? !description.equals(recipeEs.description) : recipeEs.description != null)
            return false;
        if (ratings != null ? !ratings.equals(recipeEs.ratings) : recipeEs.ratings != null) return false;
        return ingredient != null ? ingredient.equals(recipeEs.ingredient) : recipeEs.ingredient == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (ratings != null ? ratings.hashCode() : 0);
        result = 31 * result + (ingredient != null ? ingredient.hashCode() : 0);
        return result;
    }
}
