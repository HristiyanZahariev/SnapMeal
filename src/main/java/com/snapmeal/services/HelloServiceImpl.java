package com.snapmeal.services;

/**
 * Created by hristiyan on 08.12.16.
 */
public class HelloServiceImpl implements HelloService{
    @Override
    public String hello() {
        return "Something cool";
    }
}
