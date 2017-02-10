package com.snapmeal.service;


import com.snapmeal.entity.jpa.Diet;
import com.snapmeal.entity.jpa.User;
import com.snapmeal.entity.enums.UserRole;
import com.snapmeal.repository.jpa.DietRepository;
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
    private UserRepository userRepository;

    @Autowired
    private DietRepository dietRepository;

    private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(long id) {
        return userRepository.findOne(id);
    }

    public User createUser(User user) {
        user.grantRole(UserRole.USER);
        System.out.println(user);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    public void setUserDiet(String dietName, User user) {
        Diet userDiet = dietRepository.findByName(dietName);
        user.setDiet(userDiet);
        long userId = user.getId();
        System.out.println(userDiet.getId());
        System.out.println(user.getDiet().getDescription());
        userRepository.setFixedDietForUser(user.getDiet(), userId);
    }

    public User getNonJwtUser(JwtUser jwtUser) {
        return userRepository.findByUsername(jwtUser.getUsername());
    }


    @Override
    public JwtUser loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        else {
            return JwtUserFactory.create(user);
        }
    }
}
