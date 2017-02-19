package com.snapmeal.repository.elasticsearch;

import com.snapmeal.entity.elasticsearch.IngredientEs;
import com.snapmeal.entity.jpa.Ingredient;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by hristiyan on 19.02.17.
 */
@Repository
public interface IngredientEsRepository extends ElasticsearchRepository<IngredientEs, String> {
}
