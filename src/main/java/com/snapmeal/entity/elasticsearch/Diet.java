package com.snapmeal.entity.elasticsearch;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Created by hristiyan on 05.02.17.
 */
@Document(indexName = "snapmeal", type = "diet")
public class Diet {
    private String name;

    public Diet( String name) {
        this.name = name;
    }

    public Diet() {
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

        Diet diet = (Diet) o;

        return name != null ? name.equals(diet.name) : diet.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Diet{" +
                "name='" + name + '\'' +
                '}';
    }
}
