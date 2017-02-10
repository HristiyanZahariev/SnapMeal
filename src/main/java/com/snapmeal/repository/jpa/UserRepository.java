package com.snapmeal.repository.jpa;

import com.snapmeal.entity.jpa.Diet;
import com.snapmeal.entity.jpa.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by hristiyan on 12.12.16.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //List<User> findByFirstName(String name);
    User findByUsername(String name);
    @Modifying
    @Transactional
    @Query("update User u set u.diet=:diet  where u.id =:userId")
    void setFixedDietForUser(@Param("diet") Diet diet, @Param("userId") long userId);
}
