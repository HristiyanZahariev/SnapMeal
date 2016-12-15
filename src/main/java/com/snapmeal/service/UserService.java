package com.snapmeal.service;

import com.snapmeal.entity.User;
import com.snapmeal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by hristiyan on 12.12.16.
 */
@Component
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User findUserById(long id) {
        return repository.findOne(id);
    }

    public User createUser(User user) {
        return repository.save(user);
    }
}
