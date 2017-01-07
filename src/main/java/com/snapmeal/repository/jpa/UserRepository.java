package com.snapmeal.repository.jpa;

import com.snapmeal.entity.jpa.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by hristiyan on 12.12.16.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //List<User> findByFirstName(String name);
    User findByUsername(String name);
}
