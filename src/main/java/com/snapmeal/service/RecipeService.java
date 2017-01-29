package com.snapmeal.service;

import com.snapmeal.entity.elasticsearch.RecipeEs;

import com.snapmeal.entity.jpa.Recipe;
import com.snapmeal.repository.elasticsearch.RecipeEsRepository;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by hristiyan on 12.12.16.
 */
@Component
public class RecipeService {

    @Autowired
    private RecipeEsRepository repository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    private float minScore = 0.12000f;

    public Iterable<RecipeEs> getAllRecipes() {
        return repository.findAll();
    }

    public RecipeEs findRecipeById(String id) {
        return repository.findOne(id);
    }

    public RecipeEs createRecipe(RecipeEs recipe) {
        return repository.save(recipe);
    }

    public Page<RecipeEs> getRecipeByDescription(String description) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();

        QueryBuilder matchPhraseQuery = QueryBuilders.matchQuery("description", description);

        nativeSearchQueryBuilder.withQuery(matchPhraseQuery);
        nativeSearchQueryBuilder.withMinScore(minScore);

        NativeSearchQuery nativeSearchQuery = nativeSearchQueryBuilder.build();
        
        Page<RecipeEs> results = elasticsearchTemplate.queryForPage(nativeSearchQuery, RecipeEs.class);

        return results;
    }
}
