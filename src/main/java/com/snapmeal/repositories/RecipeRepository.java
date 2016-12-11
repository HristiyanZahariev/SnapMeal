package com.snapmeal.repositories;

import com.snapmeal.dao.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hristiyan on 11.12.16.
 */
@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    List<Recipe> findByName(String name);
}
