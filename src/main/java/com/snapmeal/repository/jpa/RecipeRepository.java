package com.snapmeal.repository.jpa;

import com.snapmeal.entity.elasticsearch.RecipeEs;
import com.snapmeal.entity.jpa.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by hristiyan on 10.02.17.
 */
@Repository
public interface RecipeRepository extends org.springframework.data.repository.Repository<Recipe, String> {
    Recipe findById(Long id);
    List<Recipe> findAll();
    @Async
    <S extends Recipe> CompletableFuture<S> save(S recipe);
}
