package com.snapmeal.service;

/**
 * Created by hristiyan on 12.12.16.
 */
public class UserService {
    private static UserService ourInstance = new UserService();

    public static UserService getInstance() {
        return ourInstance;
    }

    private UserService() {
    }
}
