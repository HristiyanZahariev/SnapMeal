package com.snapmeal.service;


import com.snapmeal.entity.User;
import com.snapmeal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by hristiyan on 12.12.16.
 */
@Component
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User findUserById(long id) {
        return repository.findOne(id);
    }

    public User createUser(User user) {
        return repository.save(user);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        detailsChecker.check(user);
        return user;

    }
}
