package com.snapmeal.entity.elasticsearch;



import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;


/**
 * Created by hristiyan on 11.12.16.
 */
@Document(indexName = "snapmeal", type = "recipe")
public class RecipeEs {

    @Id
    private String id;

    private String name;
    private String description;

    public RecipeEs(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public RecipeEs() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecipeEs recipeEs = (RecipeEs) o;

        if (id != null ? !id.equals(recipeEs.id) : recipeEs.id != null) return false;
        if (name != null ? !name.equals(recipeEs.name) : recipeEs.name != null) return false;
        return description != null ? description.equals(recipeEs.description) : recipeEs.description == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RecipeEs{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
