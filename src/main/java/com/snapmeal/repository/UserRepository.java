package com.snapmeal.repository;

import com.snapmeal.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hristiyan on 12.12.16.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByFName(String fname);
}
