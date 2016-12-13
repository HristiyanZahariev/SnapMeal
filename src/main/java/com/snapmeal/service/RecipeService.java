package com.snapmeal.service;

/**
 * Created by hristiyan on 12.12.16.
 */
public class RecipeService {
    private static RecipeService ourInstance = new RecipeService();

    public static RecipeService getInstance() {
        return ourInstance;
    }

    private RecipeService() {
    }
}
