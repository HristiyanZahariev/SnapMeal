package com.snapmeal.entity.elasticsearch;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;

/**
 * Created by hristiyan on 15.02.17.
 */
@Document(indexName = "snapmeal", type = "ingredients")
public class IngredientEs {
    @Id
    private String id;
    private String name;

    public IngredientEs() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IngredientEs that = (IngredientEs) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
