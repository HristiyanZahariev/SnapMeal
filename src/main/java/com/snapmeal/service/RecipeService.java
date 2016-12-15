package com.snapmeal.service;

import com.snapmeal.entity.Recipe;
import com.snapmeal.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hristiyan on 12.12.16.
 */
@Component
public class RecipeService {

    @Autowired
    private RecipeRepository repository;

    public List<Recipe> getAllRecipes() {
        return repository.findAll();
    }

    public Recipe findRecipeById(long id) {
        return repository.findOne(id);
    }

    public Recipe createRecipe(Recipe recipe) {
        return repository.save(recipe);
    }
}
