package com.snapmeal.service;

import com.snapmeal.entity.elasticsearch.RecipeEs;

import com.snapmeal.repository.elasticsearch.RecipeEsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * Created by hristiyan on 12.12.16.
 */
@Component
public class RecipeService {

    @Autowired
    private RecipeEsRepository repository;

    public Iterable<RecipeEs> getAllRecipes() {
        return repository.findAll();
    }

    public RecipeEs findRecipeById(String id) {
        return repository.findOne(id);
    }

    public RecipeEs createRecipe(RecipeEs recipe) {
        return repository.save(recipe);
    }

    public Iterable<RecipeEs> getRecipeByDescription(String description, Pageable pageable) {
        return repository.findByDescription(description, pageable);
    }
}
