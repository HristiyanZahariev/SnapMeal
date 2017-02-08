package com.snapmeal.service;


import com.snapmeal.entity.jpa.User;
import com.snapmeal.entity.enums.UserRole;
import com.snapmeal.repository.jpa.UserRepository;
import com.snapmeal.security.JwtUser;
import com.snapmeal.security.JwtUserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

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
        user.grantRole(UserRole.USER);
        System.out.println(user);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return repository.save(user);
    }

    public void setUserDiet(String diet, User user) {
        user.setDiet(diet);
        long userId = user.getId();
        repository.setFixedDietForUser(diet, userId);
    }

    public User getNonJwtUser(JwtUser jwtUser) {
        return repository.findByUsername(jwtUser.getUsername());
    }


    @Override
    public JwtUser loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        else {
            return JwtUserFactory.create(user);
        }
    }
}
