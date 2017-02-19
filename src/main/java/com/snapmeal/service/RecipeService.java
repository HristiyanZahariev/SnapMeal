package com.snapmeal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.snapmeal.entity.elasticsearch.RecipeEs;

import com.snapmeal.entity.jpa.Ingredient;
import com.snapmeal.entity.jpa.Recipe;
import com.snapmeal.entity.jpa.User;
import com.snapmeal.repository.elasticsearch.RecipeEsRepository;
import com.snapmeal.repository.jpa.IngredientRepository;
import com.snapmeal.repository.jpa.RecipeRepository;
import com.snapmeal.repository.jpa.UserRepository;
import com.snapmeal.security.JwtUser;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Created by hristiyan on 12.12.16.
 */
@Component
public class RecipeService {

    @Autowired
    private RecipeEsRepository recipeEsRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    private float minScore = 0.12000f;

    private String scoreMode = "max";

    private int limit = 2;
    ObjectMapper mapper = new ObjectMapper();

    Gson gson = new Gson();

    public Iterable<RecipeEs> getAllRecipes() {
        return recipeEsRepository.findAll();
    }

    public RecipeEs findRecipeById(String id) {
        return recipeEsRepository.findById(id);
    }

    public CompletableFuture<Recipe> createRecipe(RecipeEs recipeEs) throws IOException {
        recipeEsRepository.save(recipeEs);
        String recipeJson = mapper.writeValueAsString(recipeEs);
        Recipe recipe = mapper.readValue(recipeJson, Recipe.class);
        return recipeRepository.save(recipe);

    }

    public Page<RecipeEs> getRecipeByDescription(String description, JwtUser currentJwtUser, int page) throws JsonProcessingException {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        User currentUser = userRepository.findByUsername(currentJwtUser.getUsername());
        System.out.println(currentUser);
        if (currentUser.getDiet() != null) {
            //Ingredients allowed to be used for the diet
            Collection<Ingredient> dietIngredients = currentUser.getDiet().getIngredients();
            Collection<Ingredient> allIngredients = ingredientRepository.findAll();
            //Getting the !allowed ingredients
            allIngredients.removeAll(dietIngredients);
            List<String> ingredientNames = allIngredients.
                    stream()
                    .map((ingredient -> ingredient.getName()))
                    .distinct().collect(Collectors.toList());
            QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                    .must(matchQuery("description", description).boost(2.000f))
                    .mustNot((nestedQuery("ingredient", termsQuery("ingredient.name", ingredientNames))));

            nativeSearchQueryBuilder.withQuery(queryBuilder);
            nativeSearchQueryBuilder.withPageable(new PageRequest(limit * (page - 1), limit));
            nativeSearchQueryBuilder.withMinScore(minScore);
        }
        else {
            QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                    .must(matchQuery("description", description));

            nativeSearchQueryBuilder.withQuery(queryBuilder);
            nativeSearchQueryBuilder.withPageable(new PageRequest(limit * (page - 1), limit));
            nativeSearchQueryBuilder.withMinScore(minScore);
        }

        NativeSearchQuery nativeSearchQuery = nativeSearchQueryBuilder.build();

        System.out.println("nativeQuery" + nativeSearchQuery.getQuery());

        Page<RecipeEs> results = elasticsearchTemplate.queryForPage(nativeSearchQuery, RecipeEs.class);

        return results;
    }
}
