package com.snapmeal.repositories;

import com.snapmeal.dao.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by hristiyan on 12.12.16.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
