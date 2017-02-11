package com.snapmeal.repository.elasticsearch;

import com.snapmeal.entity.elasticsearch.RecipeEs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hristiyan on 11.12.16.
 */
@Repository
public interface RecipeEsRepository extends ElasticsearchRepository<RecipeEs, String> {

    Page<RecipeEs> findByName(String name, Pageable pageable);
    List<RecipeEs> findByDescriptionContaining(String description);
    RecipeEs findById(String id);

}
