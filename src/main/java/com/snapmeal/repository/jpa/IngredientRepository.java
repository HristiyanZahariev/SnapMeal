package com.snapmeal.repository.jpa;

import com.snapmeal.entity.jpa.Ingredient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by hristiyan on 18.02.17.
 */
@Repository
public interface IngredientRepository extends org.springframework.data.repository.Repository<Ingredient, Long> {
    @Async
    <S extends Ingredient> CompletableFuture<S> save(S diet);
    List<Ingredient> findAll();
}
