package com.snapmeal.repository.jpa;

/**
 * Created by hristiyan on 10.02.17.
 */

import com.snapmeal.entity.jpa.Diet;
import com.snapmeal.entity.jpa.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Repository
public interface DietRepository extends org.springframework.data.repository.Repository<Diet, Long> {
        Diet findByName(String name);
        Diet findById(Long id);
        List<Diet> findAll();
        void delete(Diet diet);
        @Async
        <S extends Diet> CompletableFuture<S> save(S diet);
}
