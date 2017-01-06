package com.snapmeal.repository.elasticsearch;

import com.snapmeal.entity.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hristiyan on 11.12.16.
 */
@Repository
public interface RecipeRepository extends ElasticsearchRepository<Recipe, Long> {

    Page<Recipe> findByName(String name, Pageable pageable);

    List<Recipe> findByDescription(String description, Pageable pageable);

}
