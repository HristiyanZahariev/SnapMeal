package com.snapmeal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snapmeal.entity.elasticsearch.RecipeEs;

import com.snapmeal.entity.jpa.Recipe;
import com.snapmeal.entity.jpa.User;
import com.snapmeal.repository.elasticsearch.RecipeEsRepository;
import com.snapmeal.repository.jpa.RecipeRepository;
import com.snapmeal.repository.jpa.UserRepository;
import com.snapmeal.security.JwtUser;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.nestedQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

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
    private ElasticsearchTemplate elasticsearchTemplate;

    private float minScore = 0.11000f;

    ObjectMapper mapper = new ObjectMapper();

    public Iterable<RecipeEs> getAllRecipes() {
        return recipeEsRepository.findAll();
    }

//    public RecipeEs findRecipeById(String id) {
//        return recipeEsRepository.findById(id);
//    }

    public CompletableFuture<Recipe> createRecipe(RecipeEs recipeEs) throws IOException {
        recipeEsRepository.save(recipeEs);
        String recipeJson = mapper.writeValueAsString(recipeEs);
        Recipe recipe = mapper.readValue(recipeJson, Recipe.class);
        return recipeRepository.save(recipe);

    }


    public Page<RecipeEs> getRecipeByDescription(String description, JwtUser currentJwtUser) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        User currentUser = userRepository.findByUsername(currentJwtUser.getUsername());
        String diet = currentUser.getDiet().getName();
        System.out.println(currentUser);
        QueryBuilder matchPhraseQuery = QueryBuilders.matchQuery("description", description);
        QueryBuilder builder = nestedQuery("diet", boolQuery().must(termQuery("diet.name", diet)));

        nativeSearchQueryBuilder.withQuery(matchPhraseQuery);
        nativeSearchQueryBuilder.withQuery(builder);
        nativeSearchQueryBuilder.withMinScore(minScore);

        NativeSearchQuery nativeSearchQuery = nativeSearchQueryBuilder.build();

        Page<RecipeEs> results = elasticsearchTemplate.queryForPage(nativeSearchQuery, RecipeEs.class);

        return results;
    }
}
