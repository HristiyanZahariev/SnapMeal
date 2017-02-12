package com.snapmeal.repository.jpa;

import com.snapmeal.entity.jpa.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by hristiyan on 10.02.17.
 */
@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Recipe findById(String id);
}
