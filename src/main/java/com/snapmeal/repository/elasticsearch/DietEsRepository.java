package com.snapmeal.repository.elasticsearch;

import com.snapmeal.entity.elasticsearch.DietEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by hristiyan on 16.02.17.
 */
@Repository
public interface DietEsRepository extends ElasticsearchRepository<DietEs, String> {
}
