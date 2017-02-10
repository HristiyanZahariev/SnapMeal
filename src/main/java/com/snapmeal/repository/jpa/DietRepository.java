package com.snapmeal.repository.jpa;

/**
 * Created by hristiyan on 10.02.17.
 */

import com.snapmeal.entity.jpa.Diet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DietRepository extends JpaRepository<Diet, Long> {
        Diet findByName(String name);
}
